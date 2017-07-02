package mst.music.source;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.LineUnavailableException;

public class Tarsos {

	public static void main(String... args) throws LineUnavailableException, InterruptedException {
		AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(2048, 2048 / 2);

		PitchDetectionHandler handler = new PitchDetectionHandler() {
			@Override
			public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
				System.out.println(pitchDetectionResult.getPitch());
			}
		};
		AudioProcessor pitchDetector = new PitchProcessor(
				PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
				44100,
				2048,
				handler);
		audioDispatcher.addAudioProcessor(pitchDetector);

		new Thread(audioDispatcher,"Audio dispatching").start();

		while(true) {
			Thread.sleep(500);
		}
	}
}
