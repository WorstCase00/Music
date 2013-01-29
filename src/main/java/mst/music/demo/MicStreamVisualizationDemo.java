package mst.music.demo;

import mst.music.analysis.IFrequencySpectrum;
import mst.music.fft.IFft;
import mst.music.fft.MinimFft;
import mst.music.source.StreamingMicAudioSource;
import mst.music.visualization.StreamVisualization;

public abstract class MicStreamVisualizationDemo {
	private static final int SAMPLE_RATE = 44100;
	private static final int BUFFER_SIZE = 1024;//512;// change for bandwidth
	private static final long TIME_OUT_MS = 400;

	private MicStreamVisualizationDemo() {}

	public static void main(String[] args) throws InterruptedException {
		StreamingMicAudioSource mic = new StreamingMicAudioSource(BUFFER_SIZE, SAMPLE_RATE);
		StreamVisualization visualization = new StreamVisualization();
		IFft fft = new MinimFft(BUFFER_SIZE, SAMPLE_RATE);
		while(true) {
			Thread.sleep(TIME_OUT_MS);
			float[] pcm = mic.getPcmSignal();
			IFrequencySpectrum spectrum = fft.transform(pcm);
			visualization.update(pcm, spectrum);
		}
	}
}
