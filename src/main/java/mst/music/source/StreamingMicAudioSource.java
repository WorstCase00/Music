package mst.music.source;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;
import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class StreamingMicAudioSource implements IStreamingAudioSource {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StreamingMicAudioSource.class);
	
	private final AudioInput mic;
	
	public StreamingMicAudioSource(int bufferSize, int sampleRate) {
		PApplet parent = new PApplet();
		Minim  minim = new Minim(parent);
		LOGGER.info(
				"open mic line with buffer size {} and sample rate {}",
				bufferSize, 
				sampleRate);
		mic = minim.getLineIn(Minim.MONO, bufferSize, sampleRate);
	}
	
	@Override
	public float[] getPcmSignal() {
		float[] pcmSignal = mic.mix.toArray().clone();
		LOGGER.debug("read pcm signal from mic: {}", Arrays.toString(pcmSignal));
		return pcmSignal;
	}
}
