package mst.music.scoring.calc;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.TestConstant;
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

public class PauseRespectingScoreCalculationTest {

	private static final int BPM = 120;
	private static final long TIMESTAMP = 123;
	@Rule
	public final MethodRule initMockito = MockitoJUnit.rule();
	@Mock
	private TrackDefinition track;
	@Mock
	private PitchDetectionResult pitch;
	@Mock
	private AudioEvent event;

	@Test
	public void testStartWithMiss() throws Exception {
		ScoreCalculation subject = createSubject();
		when(track.calculatePitch(TIMESTAMP, BPM)).thenReturn(Pitch.A4);
		when(event.getTimeStamp()).thenReturn((double) TIMESTAMP);
		when(pitch.getPitch()).thenReturn(1f);

		Score result = subject.add(pitch, event);

		assertEquals(0f, result.getValue(), TestConstant.DELTA);
		assertEquals(0f, result.getValue(), TestConstant.DELTA);
	}

	@Test
	public void testMaximumFrequencyHit() throws Exception {
		when(event.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitch.getPitch()).thenReturn(Float.MAX_VALUE);

		ScoreCalculation subject = createSubject();

		Score result = subject.add(pitch, event);

		assertEquals(0f, result.getValue(), TestConstant.DELTA);
	}

	@Test
	public void testMinimumFrequency() throws Exception {
		when(event.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitch.getPitch()).thenReturn(0f);

		ScoreCalculation subject = createSubject();

		Score result = subject.add(pitch, event);

		assertEquals(0f, result.getValue(), TestConstant.DELTA);
	}

	@Test
	public void testNoPitch() throws Exception {
		when(event.getTimeStamp()).thenReturn(0d);
		when(track.calculatePitch(0L, BPM)).thenReturn(Pitch.A4);
		when(pitch.getPitch()).thenReturn(-1f);

		ScoreCalculation subject = createSubject();

		Score result = subject.add(pitch, event);

		assertEquals(0f, result.getValue(), TestConstant.DELTA);
	}

	private PauseRespectingScoreCalculation createSubject() {
		return new PauseRespectingScoreCalculation(track, BPM);
	}

}