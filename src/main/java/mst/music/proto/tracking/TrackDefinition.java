package mst.music.proto.tracking;

import com.google.common.collect.Lists;
import mst.music.analysis.Pitch;

import java.util.List;

public class TrackDefinition {

	public static TrackDefinition HANSL = new TrackDefinition(
			Lists.newArrayList(
					Pitch.G4, Pitch.E4, Pitch.E4,
					Pitch.F4, Pitch.D4, Pitch.D4,
					Pitch.C4, Pitch.D4, Pitch.E4, Pitch.F4,
					Pitch.G4, Pitch.G4, Pitch.G4,
					Pitch.G4, Pitch.E4, Pitch.E4,
					Pitch.F4, Pitch.D4, Pitch.D4,
					Pitch.C4, Pitch.E4, Pitch.G4, Pitch.G4,
					Pitch.C4),

			Lists.newArrayList(
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.25F, 0.25F,
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.25F, 0.25F,
					1F),
			0.25F
			);

	public List<Pitch> getPitches() {
		return pitches;
	}

	public List<Float> getLengths() {
		return lengths;
	}

	private final List<Pitch> pitches;
	private final List<Float> lengths;
	private final float beat;


	public TrackDefinition(List<Pitch> pitches, List<Float> lengths, float beat) {
		this.pitches = pitches;
		this.lengths = lengths;
		this.beat = beat;
	}


	public float getBeatCount() {
		return lengths.stream().reduce((a, b) -> a + b).get() / beat;
	}
}
