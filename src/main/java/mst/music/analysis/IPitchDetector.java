package mst.music.analysis;


public interface IPitchDetector {
	
	Pitch getPitch(IFrequencySpectrum spectrum);
}
