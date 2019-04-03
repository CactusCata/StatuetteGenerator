package fr.statuettegenerator.apiuploader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SkinChanger {

	private final static ThreadLocal<ObjectMapper> objectMapper = new ThreadLocal<ObjectMapper>() {
		@Override
		protected ObjectMapper initialValue() {
			return new ObjectMapper();
		}
	};

	public static void changeSkin(@NonNull final SkinChangeParams params) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				CloseableHttpClient client = HttpClients.createDefault();
				HttpHost proxy = null;
				if (!params.getProxyIp().trim().isEmpty() && params.getProxyPort() > 0) {
					proxy = new HttpHost(params.getProxyIp(), params.getProxyPort());
				}
				try {
					RequestConfig.Builder config = RequestConfig.copy(RequestConfig.DEFAULT).setRedirectsEnabled(true)
							.setCircularRedirectsAllowed(false).setRelativeRedirectsAllowed(true).setMaxRedirects(10);
					config.setProxy(proxy);

					////////////////////////////////////////////////////////////////////////////////////////////////////
					// authenticate
					AuthenticatePayload payload = new AuthenticatePayload(new SkinChanger.Agent("Minecraft"),
							params.getEmail(), params.getPassword());

					HttpPost authReq = new HttpPost("https://authserver.mojang.com/authenticate");
					authReq.setConfig(config.build());

					objectMapper.get().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

					StringEntity stringEntity = new StringEntity(objectMapper.get().writeValueAsString(payload));
					authReq.setEntity(stringEntity);
					authReq.setHeader("Content-type", "application/json");
					CloseableHttpResponse authResponse = client.execute(authReq);

					if (authResponse.getStatusLine().getStatusCode() != 200) {
						throw new SkinChangeException("authentication failed:" + "\nResult:\n" + "Header:\n"
								+ Arrays.deepToString(authResponse.getAllHeaders()) + "\n\n" + "Body:\n"
								+ EntityUtils.toString(authResponse.getEntity()));
					}

					AuthenticateResponse auth = objectMapper.get()
							.readValue(EntityUtils.toString(authResponse.getEntity()), AuthenticateResponse.class);

					////////////////////////////////////////////////////////////////////////////////////////////////////
					// upload skin
					HttpPut uploadPage = new HttpPut(
							"https://api.mojang.com/user/profile/" + auth.getSelectedProfile().getUuid() + "/skin");

					uploadPage.setConfig(config.build());
					uploadPage.setHeader("Authorization", "Bearer " + auth.getAccessToken());

					stringEntity = new StringEntity(objectMapper.get().writeValueAsString(payload));
					uploadPage.setEntity(MultipartEntityBuilder.create()
							.addBinaryBody("file", params.getImage(), ContentType.create("image/png"),
									params.getImage().getName())
							.addTextBody("model", params.getSkinModel().toString()).build());
					CloseableHttpResponse skinResponse = client.execute(uploadPage);

					// check success
					if (skinResponse.getStatusLine().getStatusCode() != 204) {
						uploadPage.releaseConnection();
						String headers = Arrays.deepToString(skinResponse.getAllHeaders());
						throw new SkinChangeException(
								"upload failed:" + "\nResult:\n" + "Header:\n" + headers + "\n\n" + "Body:\n"
										+ (skinResponse.getEntity() != null
												? EntityUtils.toString(skinResponse.getEntity())
												: "null"));
					}
				} catch (SkinChangeException | IOException ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}

	@Data
	@RequiredArgsConstructor
	@SuppressWarnings("unused")
	private static class Agent {
		private final String name;
		private final int version = 1;

		private Agent(String name) {
			this.name = name;
		}
	}

	@Data
	@SuppressWarnings("unused")
	private static class AuthenticatePayload {
		private final SkinChanger.Agent agent;
		private final String username, password;

		private AuthenticatePayload(SkinChanger.Agent agent, String username, String password) {
			this.agent = agent;
			this.username = username;
			this.password = password;
		}
	}

	@Data
	@SuppressWarnings("unused")
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class AuthenticateResponse {
		private String accessToken, clientToken;
		private List<SkinChanger.Profile> availableProfiles;
		private SkinChanger.Profile selectedProfile;

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getClientToken() {
			return clientToken;
		}

		public void setClientToken(String clientToken) {
			this.clientToken = clientToken;
		}

		public List<SkinChanger.Profile> getAvailableProfiles() {
			return availableProfiles;
		}

		public void setAvailableProfiles(List<SkinChanger.Profile> availableProfiles) {
			this.availableProfiles = availableProfiles;
		}

		public SkinChanger.Profile getSelectedProfile() {
			return selectedProfile;
		}

		public void setSelectedProfile(SkinChanger.Profile selectedProfile) {
			this.selectedProfile = selectedProfile;
		}
	}

	@Data
	@SuppressWarnings("unused")
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class Profile {
		@JsonProperty("id")
		private String uuid;
		private String name;
		private boolean legacy;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUuid() {
			return name;
		}

		public void setUuid(String name) {
			this.name = name;
		}
	}
}