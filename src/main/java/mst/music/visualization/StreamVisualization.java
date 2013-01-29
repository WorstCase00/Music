package mst.music.visualization;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import mst.music.analysis.IFrequencySpectrum;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

// TODO disgusting hack
public class StreamVisualization {
	private final ChartPanel pcmPanel = createPcmPanel();
    private final ChartPanel spectrumPanel = createSpectrumPanel();
    
    private DefaultCategoryDataset spectrumDataSet = new DefaultCategoryDataset();
    private DefaultCategoryDataset pcmDataSet = new DefaultCategoryDataset();
    private JFreeChart spectrumChart;
    private JFreeChart pcmChart;
    
	public StreamVisualization() {
		 JFrame f = new JFrame("");
	        f.setTitle("");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        f.setLayout(new BorderLayout(0, 5));
	        f.setLayout(new GridLayout(2, 1));
	        f.add(spectrumPanel);//, BorderLayout.CENTER);
	        f.add(pcmPanel);//, BorderLayout.SOUTH);
	        spectrumPanel.setMouseWheelEnabled(true);
	        spectrumPanel.setHorizontalAxisTrace(true);
	        spectrumPanel.setVerticalAxisTrace(true);
	        
//	        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//	        f.add(panel, BorderLayout.SOUTH);
	        f.pack();
	        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	        f.setLocationRelativeTo(null);
	        f.setVisible(true);
	}
	
	private ChartPanel createPcmPanel() {
		createPcmChart();
        return new ChartPanel(pcmChart);
	}

	private void createPcmChart() {
		pcmChart = ChartFactory.createBarChart(
				"", //java.lang.String title, 
				"", //java.lang.String categoryAxisLabel, 
				"", //java.lang.String valueAxisLabel, 
				pcmDataSet, //CategoryDataset dataset, 
				PlotOrientation.VERTICAL, //PlotOrientation orientation, 
				false, false, false);//boolean legend, boolean tooltips, boolean urls)
		CategoryPlot plot = pcmChart.getCategoryPlot();
        plot.getRangeAxis().setRange(-1, 1);
	}

	public void update(float[] pcm, IFrequencySpectrum spectrum) {
		spectrumDataSet = new DefaultCategoryDataset();
		float[] spectrumValues = spectrum.getSpectrumValues();
		for(int i = 0; i < spectrumValues.length / 2; i++) {
			spectrumDataSet.setValue(spectrumValues[i], "row", Integer.valueOf(i));
		}
		createSpectrumChart();
		spectrumPanel.setChart(spectrumChart);
		

		pcmDataSet = new DefaultCategoryDataset();
		for(int i = 0; i < pcm.length / 2; i++) {
			pcmDataSet.setValue(pcm[i], "row", Integer.valueOf(i));
		}
		createPcmChart();
		pcmPanel.setChart(pcmChart);
	}
	
	 private ChartPanel createSpectrumPanel() {
			createSpectrumChart();
	        return new ChartPanel(spectrumChart);
	    }

	private void createSpectrumChart() {
		spectrumChart = ChartFactory.createBarChart(
				"", //java.lang.String title, 
				"", //java.lang.String categoryAxisLabel, 
				"", //java.lang.String valueAxisLabel, 
				spectrumDataSet, //CategoryDataset dataset, 
				PlotOrientation.VERTICAL, //PlotOrientation orientation, 
				false, false, false);//boolean legend, boolean tooltips, boolean urls)
		CategoryPlot plot = spectrumChart.getCategoryPlot();
        plot.getRangeAxis().setVisible(false);
        plot.getRangeAxis().setRange(0, 20);
	}
}
