package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;
import mst.music.track.TrackDefinition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoringModelTest {

	private static final float EPS = 0.001f;
	@Rule
	public final MethodRule initMockito = MockitoJUnit.rule();
	@Mock
	private ScoringView view;

	@Test
	public void testSingleCorrectNodeScoring() throws Exception {
		ScoringModel subject = createSubject();

		subject.addPitchDetectionResult(mockPitch(Pitch.G4), mockAudioEvent(0d));

		assertEquals(1f, subject.getCurrentScore(), EPS);
	}

	@Test
	public void testFirstPitchCorrectNodeScoringSummation() throws Exception {
		ScoringModel subject = createSubject();

		subject.addPitchDetectionResult(mockPitch(Pitch.G4), mockAudioEvent(0d));
		subject.addPitchDetectionResult(mockPitch(Pitch.G4), mockAudioEvent(1d));
		subject.addPitchDetectionResult(mockPitch(Pitch.G4), mockAudioEvent(2d));

		assertEquals(3f, subject.getCurrentScore(), EPS);
	}

	@Test
	public void testFirstTwoPitchCorrectNodeScoringSummation() throws Exception {
		ScoringModel subject = createSubject();

		subject.addPitchDetectionResult(mockPitch(Pitch.G4), mockAudioEvent(0d));
		subject.addPitchDetectionResult(mockPitch(Pitch.E4), mockAudioEvent(1000d));

		assertEquals(2f, subject.getCurrentScore(), EPS);
	}


	private AudioEvent mockAudioEvent(double timestamp) {
		AudioEvent mock = mock(AudioEvent.class);
		when(mock.getTimeStamp()).thenReturn(timestamp / 1000);
		return mock;
	}

	private PitchDetectionResult mockPitch(Pitch pitch) {
		PitchDetectionResult mock = mock(PitchDetectionResult.class);
		when(mock.getPitch()).thenReturn(pitch.getFrequency());
		return mock;
	}

	private ScoringModel createSubject() {
		ScoringModel subject = new ScoringModel(view, new DefaultScoreCalculation());
		subject.setTrackDefinitions(TrackDefinition.HANSL);
		subject.setBeatsPerMinute(60);
		return subject;
	}
}

