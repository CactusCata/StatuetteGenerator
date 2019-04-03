package fr.statuettegenerator.apiuploader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;

public class SkinUploader {

	private void login(String email, String password) throws Exception {
		String payload = "username=" + URLEncoder.encode(email, "UTF-8");
		payload += "&password=" + URLEncoder.encode(password, "UTF-8");
		byte[] payload_data = payload.getBytes("UTF-8");

		HttpURLConnection http = (HttpURLConnection) (new URL("https://minecraft.net/login").openConnection());
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		http.setFixedLengthStreamingMode(payload_data.length);

		http.connect();
		http.getOutputStream().write(payload_data);

		System.out.println("login: " + http.getResponseCode() + " " + http.getResponseMessage());

		http.disconnect();
	}

	private void profile() throws Exception {
		HttpURLConnection http = (HttpURLConnection) (new URL("https://minecraft.net/profile").openConnection());
		http.setRequestMethod("GET");

		http.connect();

		System.out.println("profile: " + http.getResponseCode() + " " + http.getResponseMessage());

		http.disconnect();
	}

	private void upload(String authenticityToken, BufferedImage image, boolean male) throws java.lang.Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);

		String boundary = Long.toHexString(System.currentTimeMillis());

		HttpURLConnection http = (HttpURLConnection) (new URL("https://minecraft.net/profile/skin").openConnection());
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		http.connect();

		http.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
		http.getOutputStream().write(("Content-Disposition: form-data; name=\"authenticityToken\"\r\n\r\n").getBytes());
		http.getOutputStream().write((authenticityToken + "\r\n").getBytes());

		http.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
		http.getOutputStream().write(("Content-Disposition: form-data; name=\"model\"\r\n\r\n").getBytes());
		http.getOutputStream().write(((male ? "steve" : "3pxarm") + "\r\n").getBytes());

		http.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
		http.getOutputStream()
				.write(("Content-Disposition: form-data; name=\"skin\"; filename=\"skin.png\"\r\n").getBytes());
		http.getOutputStream().write(("Content-Type: image/png\r\n\r\n").getBytes());
		http.getOutputStream().write(baos.toByteArray());
		http.getOutputStream().write(("\r\n").getBytes());

		http.getOutputStream().write(("--" + boundary + "--\r\n").getBytes());

		http.getOutputStream().flush();

		System.out.println("upload: " + http.getResponseCode() + " " + http.getResponseMessage());

		http.disconnect();
	}

	private String get_authenticityToken(List<HttpCookie> cookies) throws Exception {
		for (HttpCookie cookie : cookies) {
			if (cookie.getName().equals("PLAY_SESSION")) {
				for (String param : cookie.getValue().split("&")) {
					int i = param.indexOf("=");
					String name = URLDecoder.decode(param.substring(0, i), "UTF-8");
					String value = URLDecoder.decode(param.substring(i + 1), "UTF-8");
					if (name.equals("___AT"))
						return value;
				}
			}
		}
		return null;
	}

	public void start(String email, String password, BufferedImage skin) throws Exception {

		// this should handle cookies for all HTTP requests
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		login(email, password);
		profile();

		// get authenticityToken from picked up cookies
		String authenticityToken = get_authenticityToken(cookieManager.getCookieStore().getCookies());
		if (authenticityToken == null) {
			System.out.println("Failed to get authenticityToken");
		} else {
			System.out.println("authenticityToken = " + authenticityToken);

			upload(authenticityToken, skin, true);
		}
	}

}
