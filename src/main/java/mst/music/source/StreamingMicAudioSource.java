package mst.music.source;

import processing.core.PApplet;
import ddf.minim.AudioInput;
import ddf.minim.Minim;

public class StreamingMicAudioSource implements IStreamingAudioSource {
	private static final float SAMPLE_RATE = 44100;
	protected static final int BITS = 8;
	protected static final int TIME_SIZE = 512; // change for bandwidth
	protected static final int BUFFER_SIZE = 512;// change for bandwidth
	
	private final AudioInput mic;
	
	public StreamingMicAudioSource() {
		PApplet parent = new PApplet();
		Minim  minim = new Minim(parent);
		mic = minim.getLineIn(Minim.MONO, BUFFER_SIZE, SAMPLE_RATE);
	}
	
	@Override
	public float[] getPcmSignal() {
		float[] pcmSignal = mic.mix.toArray().clone();
		return pcmSignal;
	}
}
