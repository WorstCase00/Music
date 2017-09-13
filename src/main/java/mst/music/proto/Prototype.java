/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*/


package mst.music.proto;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import mst.music.scoring.ScoringController;
import mst.music.scoring.ScoringModel;
import mst.music.scoring.ScoringView;
import mst.music.session.SessionController;
import mst.music.session.SessionModel;
import mst.music.session.SessionView;
import mst.music.track.TrackDefinition;
import mst.music.tracking.TrackingController;
import mst.music.tracking.TrackingModel;
import mst.music.tracking.TrackingView;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class Prototype extends JFrame {

	private static final long serialVersionUID = 3501426880288136245L;

	float sampleRate = 44100;
	int bufferSize = 1024;
	int overlap = 0;
	private TrackingController trackingController;
	private ScoringController scoringController;
	private SessionController sessionController;

	private JTextArea textArea;

	private AudioDispatcher dispatcher;
	private Mixer currentMixer;

	private PitchEstimationAlgorithm algo;

	private ActionListener algoChangeListener = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent e) {
			String name = e.getActionCommand();
			PitchEstimationAlgorithm newAlgo = PitchEstimationAlgorithm.valueOf(name);
			algo = newAlgo;
			try {
				setNewMixer(currentMixer);
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
	}};

	public Prototype() {
		this.setLayout(new GridLayout(3, 2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Pitch Detector");
		this.algo = PitchEstimationAlgorithm.YIN;

		initInputPanel();
		initTrackingPanel();
		initAlgoPanel();
		initScoringPanel();
		initLoggingPanel();
		initSessionPanel();

	}

	private void initSessionPanel() {
		SessionView sessionView = new SessionView();
		add(sessionView);
		SessionModel sessionModel = new SessionModel(sessionView);
		this.sessionController = new SessionController(sessionModel, scoringController, trackingController);
		sessionView.addRefreshButtonListener(e -> this.sessionController.onRefresh());
	}

	private void initScoringPanel() {
		ScoringView scoringView = new ScoringView();
		add(scoringView);
		ScoringModel scoringModel = new ScoringModel(scoringView);
		this.scoringController = new ScoringController(scoringModel);
		this.scoringController.setExercise(TrackDefinition.HANSL, 120);

	}

	private void initTrackingPanel() {
		TrackingView trackingPanel = new TrackingView();
		add(trackingPanel);
		TrackingModel trackingModel = new TrackingModel(trackingPanel);
		this.trackingController = new TrackingController(trackingModel);
		this.trackingController.setExcercise(TrackDefinition.HANSL, 120);
	}

	private void initLoggingPanel() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
	}

	private void initAlgoPanel() {
		JPanel pitchDetectionPanel = new PitchDetectionPanel(algoChangeListener);

		add(pitchDetectionPanel);
	}

	private void initInputPanel() {
		JPanel inputPanel = new InputPanel();
		add(inputPanel);
		inputPanel.addPropertyChangeListener("mixer",
				event -> {
					try {
						setNewMixer((Mixer) event.getNewValue());
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				});
	}


	private void setNewMixer(Mixer mixer) throws LineUnavailableException, UnsupportedAudioFileException {
		
		if(dispatcher!= null){
			dispatcher.stop();
		}
		currentMixer = mixer;
		

		
		textArea.append("Started listening with " + Shared.toLocalString(mixer.getMixerInfo().getName()) + "\n");

		final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
				true);
		final DataLine.Info dataLineInfo = new DataLine.Info(
				TargetDataLine.class, format);
		TargetDataLine line;
		line = (TargetDataLine) mixer.getLine(dataLineInfo);
		final int numberOfSamples = bufferSize;
		line.open(format, numberOfSamples);
		line.start();
		final AudioInputStream stream = new AudioInputStream(line);

		JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
		// create a new dispatcher
		dispatcher = new AudioDispatcher(audioStream, bufferSize, overlap);

		// add a processor
		PitchDetectionHandlerImpl detectionHandler = new PitchDetectionHandlerImpl(this);
		dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, detectionHandler));
		dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, trackingController));
		dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, scoringController));
		
		new Thread(dispatcher,"Audio dispatching").start();
	}

	public static void main(String... strings) throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				//ignore failure to set default look en feel;
			}
			JFrame frame = new Prototype();
			frame.pack();
			frame.setVisible(true);
		});
	}


	public void appendMessage(String message) {
		textArea.append(message);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
