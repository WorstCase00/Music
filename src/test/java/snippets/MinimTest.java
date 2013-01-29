package snippets;

import java.util.Arrays;

import mst.music.analysis.IFrequencySpectrum;
import mst.music.analysis.FrequencySpectrum;

import processing.core.PApplet;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class MinimTest {
	private static final float SAMPLE_RATE = 44100;
	protected static final int BITS = 8;
	protected static final int TIME_SIZE = 512; // change for bandwidth
	protected static final int BUFFER_SIZE = 512;// change for bandwidth
	
	public static void main(String[] args) throws InterruptedException {
		PApplet parent = new PApplet();
		final Minim minim = new Minim(parent);
		final SpectrumVisualizer visualizer = new SpectrumVisualizer();
		//	final AudioInput mic = minim.getLineIn(Minim.MONO, 128);
		Runnable run = new Runnable() {

			@Override
			public void run() {
				FFT fft = new FFT(TIME_SIZE, SAMPLE_RATE);


				while(true) {
					final AudioInput mic = minim.getLineIn(Minim.MONO, BUFFER_SIZE, SAMPLE_RATE);
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					float[] pcmSignal = mic.mix.toArray().clone();
					fft.forward(pcmSignal.clone());
					System.out.println(Arrays.toString(pcmSignal));
					float maxFreq = -1f;
					float maxAmpl = -1f;
					int maxBand = -1;
					float[] spectrumValues = new float[fft.specSize()];
					for(int band = 0; band < fft.specSize(); band ++) {
						spectrumValues[band] = fft.getBand(band);
						if(maxAmpl < fft.getBand(band)) {
							maxFreq = fft.indexToFreq(band);
							maxBand = band;
							maxAmpl = fft.getBand(band);
						}
					}
					IFrequencySpectrum spectrum = new FrequencySpectrum(fft.getBandWidth(), spectrumValues);
					visualizer.update(pcmSignal, spectrum);
//					fft.inverse();
					System.out.println("f: " + maxFreq+ " amplitude: " + maxAmpl + " band: " + maxBand);

					mic.close();
				}
			}
		};
		(new Thread(run)).start();
		Thread.sleep(3000);
		minim.stop();
	}
}
