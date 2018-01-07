package mst.music.proto;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;

public class PitchDetectionHandlerImpl implements PitchDetectionHandler {


	private static final String MSG_TEMPLATE = "Pitch detected at %.2fs: %.2fHz - %s ( %.2f probability, RMS: %.5f )\n";

	private final Prototype prototype;

	public PitchDetectionHandlerImpl(Prototype prototype) {

		this.prototype = prototype;
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1){
			double timeStamp = audioEvent.getTimeStamp();
			float pitch = pitchDetectionResult.getPitch();
			float probability = pitchDetectionResult.getProbability();
			double rms = audioEvent.getRMS() * 100;
			String message = String.format(MSG_TEMPLATE,
					timeStamp,
					pitch,
					Pitch.getNearestPitch(pitch).toString(),
					probability,
					rms);
			prototype.appendMessage(message);

		}
	}
}
