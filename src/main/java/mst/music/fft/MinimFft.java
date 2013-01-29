package mst.music.fft;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mst.music.analysis.FrequencySpectrum;
import mst.music.analysis.IFrequencySpectrum;
import ddf.minim.analysis.FFT;

public class MinimFft implements IFft {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MinimFft.class);

	private final FFT fft;
	
	public MinimFft(int timeSize, int sampleRate) {
		LOGGER.info(
				"init fft with time frame size {} and sample rate {}", 
				timeSize, 
				sampleRate);
		fft = new FFT(timeSize, sampleRate);
	}

	@Override
	public IFrequencySpectrum transform(float[] values) {
		LOGGER.debug("transfor values: {}", Arrays.toString(values));
		fft.forward(values.clone());
		float[] transformedValues = new float[fft.specSize()];
		LOGGER.debug("transformed values: {}", Arrays.toString(transformedValues));
		for(int band = 0; band < fft.specSize(); band ++) {
			transformedValues[band] = fft.getBand(band);
		}
		IFrequencySpectrum spectrum = new FrequencySpectrum(
				fft.getBandWidth(), 
				transformedValues);
		return spectrum;
	}
}
