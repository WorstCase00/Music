package mst.music.tracking;

import com.google.common.collect.ImmutableMap;
import mst.music.track.TrackDefinition;

import java.util.Map;

class TrackParser {

	private static final String HANSL_STRING = "X:0\nT:A simple scale exercise\nM:4/4\nK:D\nL:1/4\n" +
			"G E E2|F D D2|C D E F|G G G2|G E E2|F D D2|C E G G|C4\n";

	private static final String SUM_STRING = "X:0\nT:Sum sum sum\nM:4/4\nK:D\nL:1/4\n" +
			"G F E2|" +
			"D E F D|C4|" +
			"E F G E|" +
			"D E F D|" +
			"E F G E|" +
			"D E F D|" +
			"G F E2|" +
			"D E F D|C4\n";

	private static final Map<String, String> TITLE_TO_PARSED =
			ImmutableMap.of(
					TrackDefinition.HANSL.getTitle(), HANSL_STRING,
					TrackDefinition.SUM.getTitle(), SUM_STRING);

	public String get(TrackDefinition trackDefinition) {
		return TITLE_TO_PARSED.get(trackDefinition.getTitle());
	}
}
