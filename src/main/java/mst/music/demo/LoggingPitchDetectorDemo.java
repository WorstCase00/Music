package mst.music.demo;

import mst.music.analysis.IFrequencySpectrum;
import mst.music.analysis.IPitchDetector;
import mst.music.analysis.MaxFrequencyBandPitchDetector;
import mst.music.analysis.Pitch;
import mst.music.fft.IFft;
import mst.music.fft.MinimFft;
import mst.music.source.StreamingMicAudioSource;

public abstract class LoggingPitchDetectorDemo {
	private static final int SAMPLE_RATE = 44100;
	private static final int BUFFER_SIZE = 1024;//512;// change for bandwidth
	private static final long TIME_OUT_MS = 10;

	private LoggingPitchDetectorDemo() {}

	public static void main(String[] args) throws InterruptedException {
		StreamingMicAudioSource mic = new StreamingMicAudioSource(BUFFER_SIZE, SAMPLE_RATE);
		IFft fft = new MinimFft(BUFFER_SIZE, SAMPLE_RATE);
		IPitchDetector detector = new MaxFrequencyBandPitchDetector();
		while(true) {
			Thread.sleep(TIME_OUT_MS);
			float[] pcm = mic.getPcmSignal();
			IFrequencySpectrum spectrum = fft.transform(pcm);
			Pitch pitch = detector.getPitch(spectrum);
			if(pitch != null) {
				System.out.println(pitch.name() + " ");
			}
		}
	}
}
