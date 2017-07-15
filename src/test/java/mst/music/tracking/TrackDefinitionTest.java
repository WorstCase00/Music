package mst.music.tracking;

import com.google.common.collect.Lists;
import mst.music.analysis.Pitch;
import mst.music.util.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by msturm on 7/07/2017.
 */
public class TrackDefinitionTest {

	@Test
	public void testNoteTracking() throws Exception {
		TrackDefinition subject = new TrackDefinition(
				Lists.newArrayList(Pitch.C4, Pitch.C4, Pitch.C4, Pitch.C4),
				Lists.newArrayList(0.25F, 0.25F, 0.25F, 0.25F),
				0.25f
		);

		assertEquals(0, subject.calculateCurrentNoteIndex(0, 1));
		assertEquals( 0, subject.calculateCurrentNoteIndex(Constants.MS_PER_MINUTE - 1, 1));
		assertEquals( 1, subject.calculateCurrentNoteIndex(Constants.MS_PER_MINUTE, 1));
		assertEquals( 1, subject.calculateCurrentNoteIndex(2 * Constants.MS_PER_MINUTE - 1, 1));
		assertEquals( 2, subject.calculateCurrentNoteIndex(2 * Constants.MS_PER_MINUTE, 1));
	}

	@Test
	public void testNoteTrackingWithHigherBpm() throws Exception {
		TrackDefinition subject = new TrackDefinition(
				Lists.newArrayList(Pitch.C4, Pitch.C4, Pitch.C4, Pitch.C4),
				Lists.newArrayList(0.5F, 0.25F, 0.25F),
				0.25f
		);

		assertEquals(0, subject.calculateCurrentNoteIndex(0, 1));
		assertEquals( 0, subject.calculateCurrentNoteIndex(Constants.MS_PER_MINUTE - 1, 1));
		assertEquals( 0, subject.calculateCurrentNoteIndex(Constants.MS_PER_MINUTE, 1));
		assertEquals( 0, subject.calculateCurrentNoteIndex(2 * Constants.MS_PER_MINUTE - 1, 1));
		assertEquals( 1, subject.calculateCurrentNoteIndex(2 * Constants.MS_PER_MINUTE, 1));
	}
}