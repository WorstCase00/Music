package mst.music.analysis;

import java.util.Arrays;

public class FrequencySpectrum implements IFrequencySpectrum {

	private final float bandWidth;
	private final float[] spectrumValues;

	public FrequencySpectrum(float bandWidth, float[] spectrumValues) {
		this.bandWidth = bandWidth;
		this.spectrumValues = spectrumValues;
	}

	/* (non-Javadoc)
	 * @see mst.music.analysis.ISpectrum#getBandWidth()
	 */
	@Override
	public float getBandWidth() {
		return bandWidth;
	}

	/* (non-Javadoc)
	 * @see mst.music.analysis.ISpectrum#getSpectrumValues()
	 */
	@Override
	public float[] getSpectrumValues() {
		return spectrumValues;
	}

	@Override
	public String toString() {
		return "Spectrum [bandWidth=" + bandWidth + ", spectrumValues="
				+ Arrays.toString(spectrumValues) + "]";
	}
}
