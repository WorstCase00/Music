package mst.music.fft;

import mst.music.analysis.FrequencySpectrum;
import mst.music.analysis.IFrequencySpectrum;
import ddf.minim.analysis.FFT;

public class MinimFft implements IFft {
	private static final float SAMPLE_RATE = 44100;
	private static final int TIME_SIZE = 512; // change for bandwidth

	private final FFT fft;
	
	public MinimFft() {
		fft = new FFT(TIME_SIZE, SAMPLE_RATE);
	}

	@Override
	public IFrequencySpectrum transform(float[] values) {
		fft.forward(values.clone());
		float[] spectrumValues = new float[fft.specSize()];
		for(int band = 0; band < fft.specSize(); band ++) {
			spectrumValues[band] = fft.getBand(band);
		}
		IFrequencySpectrum spectrum = new FrequencySpectrum(fft.getBandWidth(), spectrumValues);
		return spectrum;
	}
}
