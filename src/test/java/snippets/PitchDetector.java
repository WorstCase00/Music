package snippets;

import java.util.ArrayList;
import java.util.List;

import mst.music.analysis.IFrequencySpectrum;
import mst.music.analysis.Pitch;

import com.google.common.primitives.Floats;

public class PitchDetector {

	private List<Pitch> pitches;
	
	public PitchDetector() {
		pitches = new ArrayList<Pitch>();
		for(Pitch pitch : Pitch.values()) {
			pitches.add(pitch);
		}
	}
	
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
		for(int i = 1; i < pitches.size(); i++) {
			if(pitches.get(i).getFrequency() > maxFrequency) {
				return pitches.get(i);
			}
		}
		return pitches.get(0);
	}

	
}
