package mst.music.analysis;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum Pitch {


	
	C0(16.35f),
	C0Db0(17.32f),
	D0(18.35f),
	D0Eb0(19.45f),
	E0(20.60f),
	F0(21.83f),
	F0Gb0(23.12f),
	G0(24.50f),
	G0Ab0(25.96f),
	A0(27.50f),
	A0Bb0(29.14f),
	B0(30.87f),
	C1(32.70f),
	C1Db1(34.65f),
	D1(36.71f),
	D1Eb1(38.89f),
	E1(41.20f),
	F1(43.65f),
	F1Gb1(46.25f),
	G1(49.00f),
	G1Ab1(51.91f),
	A1(55.00f),
	A1Bb1(58.27f),
	B1(61.74f),
	C2(65.41f),
	C2Db2(69.30f),
	D2(73.42f),
	D2Eb2(77.78f),
	E2(82.41f),
	F2(87.31f),
	F2Gb2(92.50f),
	G2(98.00f),
	G2Ab2(103.83f),
	A2(110.00f),
	A2Bb2(116.54f),
	B2(123.47f),
	C3(130.81f),
	C3Db3(138.59f),
	D3(146.83f),
	D3Eb3(155.56f),
	E3(164.81f),
	F3(174.61f),
	F3Gb3(185.00f),
	G3(196.00f),
	G3Ab3(207.65f),
	A3(220.00f),
	A3Bb3(233.08f),
	B3(246.94f),
	C4(261.63f),
	C4Db4(277.18f),
	D4(293.66f),
	D4Eb4(311.13f),
	E4(329.63f),
	F4(349.23f),
	F4Gb4(369.99f),
	G4(392.00f),
	G4Ab4(415.30f),
	A4(440.00f),
	A4Bb4(466.16f),
	B4(493.88f),
	C5(523.25f),
	C5Db5(554.37f),
	D5(587.33f),
	D5Eb5(622.25f),
	E5(659.26f),
	F5(698.46f),
	F5Gb5(739.99f),
	G5(783.99f),
	G5Ab5(830.61f),
	A5(880.00f),
	A5Bb5(932.33f),
	B5(987.77f),
	C6(1046.50f),
	C6Db6(1108.73f),
	D6(1174.66f),
	D6Eb6(1244.51f),
	E6(1318.51f),
	F6(1396.91f),
	F6Gb6(1479.98f),
	G6(1567.98f),
	G6Ab6(1661.22f),
	A6(1760.00f),
	A6Bb6(1864.66f),
	B6(1975.53f),
	C7(2093.00f),
	C7Db7(2217.46f),
	D7(2349.32f),
	D7Eb7(2489.02f),
	E7(2637.02f),
	F7(2793.83f),
	F7Gb7(2959.96f),
	G7(3135.96f),
	G7Ab7(3322.44f),
	A7(3520.00f),
	A7Bb7(3729.31f),
	B7(3951.07f),
	C8(4186.01f),
	C8Db8(4434.92f),
	D8(4698.64f),
	D8Eb8(4978.03f);

	public static final List<Pitch> LADDER = Lists.newArrayList(
			C0,
			C0Db0,
			D0,
			D0Eb0,
			E0,
			F0,
			F0Gb0,
			G0,
			G0Ab0,
			A0,
			A0Bb0,
			B0,
			C1,
			C1Db1,
			D1,
			D1Eb1,
			E1,
			F1,
			F1Gb1,
			G1,
			G1Ab1,
			A1,
			A1Bb1,
			B1,
			C2,
			C2Db2,
			D2,
			D2Eb2,
			E2,
			F2,
			F2Gb2,
			G2,
			G2Ab2,
			A2,
			A2Bb2,
			B2,
			C3,
			C3Db3,
			D3,
			D3Eb3,
			E3,
			F3,
			F3Gb3,
			G3,
			G3Ab3,
			A3,
			A3Bb3,
			B3,
			C4,
			C4Db4,
			D4,
			D4Eb4,
			E4,
			F4,
			F4Gb4,
			G4,
			G4Ab4,
			A4,
			A4Bb4,
			B4,
			C5,
			C5Db5,
			D5,
			D5Eb5,
			E5,
			F5,
			F5Gb5,
			G5,
			G5Ab5,
			A5,
			A5Bb5,
			B5,
			C6,
			C6Db6,
			D6,
			D6Eb6,
			E6,
			F6,
			F6Gb6,
			G6,
			G6Ab6,
			A6,
			A6Bb6,
			B6,
			C7,
			C7Db7,
			D7,
			D7Eb7,
			E7,
			F7,
			F7Gb7,
			G7,
			G7Ab7,
			A7,
			A7Bb7,
			B7,
			C8,
			C8Db8,
			D8,
			D8Eb8
	);

	public static Pitch getNearestPitch(float frequency) {
		int index = Collections.binarySearch(Pitch.PITCH_FREQUENCIES, frequency);
		if (index >= 0) {
			return Pitch.LADDER.get(index);
		}
		int insertionPoint = (-index) - 1;
		if (insertionPoint == 0) {
			return Pitch.LADDER.get(0);
		} else if (insertionPoint == Pitch.LADDER.size()) {
			return Pitch.LADDER.get(insertionPoint - 1);
		}

		if (Math.abs(frequency - Pitch.LADDER.get(insertionPoint-1).getFrequency()) <=
				Math.abs(frequency - Pitch.LADDER.get(insertionPoint).getFrequency())) {
			return Pitch.LADDER.get(insertionPoint-1);
		}
		return Pitch.LADDER.get(insertionPoint);
	}

	public static final List<Float> PITCH_FREQUENCIES =
			LADDER.stream().map(pitch -> pitch.getFrequency()).collect(Collectors.toList());

	private final float frequency;

	Pitch(float frequency) {
		this.frequency = frequency;
	}

	public float getFrequency() {
		return frequency;
	}


	@Override
	public String toString() {
		return this.name();
	}

}
