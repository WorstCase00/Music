package mst.music.scoring.calc;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;
import mst.music.scoring.Score;
import mst.music.track.TrackDefinition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HitNotesScoreCalculationTest {

	private static final int BPM = 1;
	@Rule
	public final MethodRule initMockito = MockitoJUnit.rule();

	@Mock
	private AudioEvent audioEvent;
	@Mock
	private PitchDetectionResult pitchDetectionResult;
	@Mock
	private TrackDefinition track;

	@Test
	public void testPerfectPitchHit() throws Exception {
		when(audioEvent.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitchDetectionResult.getPitch()).thenReturn(Pitch.A4.getFrequency());

		HitNotesScoreCalculation subject = createSubject();

		Score result = subject.add(pitchDetectionResult, audioEvent);

		assertEquals(1f, result.getValue(), 0.001);
	}

	@Test
	public void testMaximumFrequencyHit() throws Exception {
		when(audioEvent.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitchDetectionResult.getPitch()).thenReturn(Float.MAX_VALUE);

		HitNotesScoreCalculation subject = createSubject();

		Score result = subject.add(pitchDetectionResult, audioEvent);

		assertEquals(0f, result.getValue(), 0.001);
	}

	@Test
	public void testMinimumFrequency() throws Exception {
		when(audioEvent.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitchDetectionResult.getPitch()).thenReturn(0f);

		HitNotesScoreCalculation subject = createSubject();

		Score result = subject.add(pitchDetectionResult, audioEvent);

		assertEquals(0f, result.getValue(), 0.001);
	}

	@Test
	public void testNoPitch() throws Exception {
		when(audioEvent.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitchDetectionResult.getPitch()).thenReturn(-1f);

		HitNotesScoreCalculation subject = createSubject();

		Score result = subject.add(pitchDetectionResult, audioEvent);

		assertEquals(0f, result.getValue(), 0.001);
	}

	private HitNotesScoreCalculation createSubject() {
		return new HitNotesScoreCalculation(
				track,
				BPM
		);
	}

}