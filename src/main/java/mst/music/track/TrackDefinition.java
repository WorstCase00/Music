package mst.music.track;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mst.music.analysis.Pitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class TrackDefinition {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackDefinition.class);

	private static final long MILLIS_PER_DAY = 1000 * 60;
	public static TrackDefinition HANSL = new TrackDefinition(
			"Hansl small", Lists.newArrayList(
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

	public static TrackDefinition SUM = new TrackDefinition(
			"Sum sum sum", Lists.newArrayList(
					Pitch.G4, Pitch.F4, Pitch.E4,
					Pitch.D4, Pitch.E4, Pitch.F4, Pitch.D4, Pitch.C4,
					Pitch.E4, Pitch.F4, Pitch.G4, Pitch.E4,
					Pitch.D4, Pitch.E4, Pitch.F4, Pitch.D4,
					Pitch.E4, Pitch.F4, Pitch.G4, Pitch.E4,
					Pitch.D4, Pitch.E4, Pitch.F4, Pitch.D4,
					Pitch.G4, Pitch.F4, Pitch.E4,
					Pitch.D4, Pitch.E4, Pitch.F4, Pitch.D4, Pitch.C4),

			Lists.newArrayList(
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.25F, 0.25F,
					0.25F, 0.25F, 0.25F, 0.25F,
					0.25F, 0.25F, 0.25F, 0.25F,
					0.25F, 0.25F, 0.25F, 0.25F,
					0.25F, 0.25F, 0.5F,
					0.25F, 0.25F, 0.25F, 0.25F, 0.5F),
			0.25F
	);

	private static final Map<String, TrackDefinition> TITLE_TO_DEFINITIONS = ImmutableMap.of(
			HANSL.getTitle(), HANSL,
			SUM.getTitle(), SUM
	);

	private final String title;

	public List<Pitch> getPitches() {
		return pitches;
	}

	public List<Float> getLengths() {
		return lengths;
	}

	private final List<Pitch> pitches;
	private final List<Float> lengths;
	private final float beat;


	public TrackDefinition(String title, List<Pitch> pitches, List<Float> lengths, float beat) {
		this.title = title;
		this.pitches = pitches;
		this.lengths = lengths;
		this.beat = beat;
	}


	public float getBeatCount() {
		return lengths.stream().reduce((a, b) -> a + b).get() / beat;
	}

	public int calculateCurrentNoteIndex(long timeStampInMs, int bpm) {
		int beatIndex = (int) Math.floor(((double)bpm) * ((double)timeStampInMs) / (double) MILLIS_PER_DAY);
		LOGGER.debug("beatIndex: {}", beatIndex);
		return calculateScoreIndexForBeatIndex(beatIndex);
	}

	private int calculateScoreIndexForBeatIndex(int beatIndex) {
		LOGGER.debug("beat index: {}", beatIndex);
		float length = beat * beatIndex;
		float cumulatedSum = 0f;
		for (int i = 0; i < lengths.size(); i++) {
			cumulatedSum += lengths.get(i);
			if (length < cumulatedSum) {
				return i;
			}
		}
		return lengths.size() - 1;
	}

	public Pitch calculateNote(long timestamp, int beatsPerMinute) {
		return pitches.get(calculateCurrentNoteIndex(timestamp, beatsPerMinute));
	}

	public String getTitle() {
		return this.title;
	}

	public static TrackDefinition forTitle(String title) {
		return TITLE_TO_DEFINITIONS.get(title);
	}
}
