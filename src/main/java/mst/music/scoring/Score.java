package mst.music.scoring;

public class Score {

	public static final Score EMPTY = new Score(0f, 0f);
	private final float value;
	private final float percentage;

	public Score(float value, float percentage) {
		this.value = value;
		this.percentage = percentage;
	}

	public float getValue() {
		return value;
	}

	public float getPercentage() {
		return percentage;
	}
}
