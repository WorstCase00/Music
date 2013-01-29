package mst.music.analysis;


public interface IPitchAnalyzer {
	
	Pitch getPitch(IFrequencySpectrum spectrum);
}
