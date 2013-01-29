package snippets;

import java.util.Arrays;

public class Spectrum {

	private final float bandWidth;
	private final float[] spectrumValues;

	public Spectrum(float bandWidth, float[] spectrumValues) {
		this.bandWidth = bandWidth;
		this.spectrumValues = spectrumValues;
	}

	public float getBandWidth() {
		return bandWidth;
	}

	public float[] getSpectrumValues() {
		return spectrumValues;
	}

	@Override
	public String toString() {
		return "Spectrum [bandWidth=" + bandWidth + ", spectrumValues="
				+ Arrays.toString(spectrumValues) + "]";
	}
}
