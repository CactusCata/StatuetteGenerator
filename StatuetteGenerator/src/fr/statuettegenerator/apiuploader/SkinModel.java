package fr.statuettegenerator.apiuploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkinModel {

	STEVE(""),
	ALEX("slim");

	private final String inForm;

	private SkinModel(String inform) {
		this.inForm = inform;
	}

	@Override
	public String toString() {
		return inForm;
	}
}