package mst.music.fft;

import mst.music.analysis.IFrequencySpectrum;

public interface IFft {

	IFrequencySpectrum transform(float[] values);
}
