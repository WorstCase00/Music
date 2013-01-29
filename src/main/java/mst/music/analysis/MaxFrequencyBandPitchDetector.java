package mst.music.analysis;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;

public class MaxFrequencyBandPitchDetector implements IPitchDetector {

	private static final float AMPLITUDE_THRESHOLD = 5;
	private final List<Float> frequencies;
	private final List<Pitch> pitches;

	public MaxFrequencyBandPitchDetector() {
		this.frequencies = Lists.newArrayList();
		this.pitches = Lists.newArrayList();
		for(Pitch pitch : Pitch.values()) {
			Float frequency = pitch.getFrequency();
			assertPitchesOrder(frequencies, frequency);
			frequencies.add(frequency);
			pitches.add(pitch);
		}
	}

	private void assertPitchesOrder(List<Float> frequencies, Float frequency) {
		if(frequencies.size() > 1 && frequency < frequencies.get(frequencies.size() - 1)) {
			throw new RuntimeException("pitches not ordered - reimplement");
		}
	}

	@Override
	public Pitch getPitch(IFrequencySpectrum spectrum) {
		float[] spectrumValues = spectrum.getSpectrumValues();
		float maxAmpl = Floats.max(spectrumValues);
		float maxFrequency = -1;
		float bandWidth = spectrum.getBandWidth();
		for(int i = 1; i < spectrumValues.length; i++) {
			if(spectrumValues[i] == maxAmpl) {
				maxFrequency = i * bandWidth;
				break;
			}
		}
		if(maxAmpl < AMPLITUDE_THRESHOLD) {
			return null;
		}
		for(int i = 1; i < pitches.size(); i++) {
			float frequency = pitches.get(i).getFrequency();
			if(frequency > maxFrequency) {
				float upperDifference = maxFrequency - frequency;
				float lowerDifference = frequency - pitches.get(i-1).getFrequency();
				if(upperDifference < lowerDifference) {
					return pitches.get(i);
				} else {
					return pitches.get(i-1);
				}
			}
		}
		return pitches.get(0);
	}

}
