package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import library.MosaicGenerator;

public class MainGUI {

	static MosaicGenerator generator;
	static JFrame frame;
	static public InputPanel inputPanel;
	static public SourcePanel sourcePanel;
	static public JPanel outputPanel;
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }

			
				
			
        });
	}

private static void createAndShowGUI() {
	//set up the encompassing JFrame
	frame=new JFrame("Photomosaic Generator");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setPreferredSize(new Dimension(400,600));
	
	//set up main container
	JPanel containerPanel = new JPanel();
	containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    frame.add(containerPanel);
    
    //add inputs
    inputPanel = new InputPanel(generator);
    containerPanel.add(inputPanel);
    
    sourcePanel = new SourcePanel(inputPanel);
    containerPanel.add(sourcePanel);
    
    outputPanel = new OutputPanel(sourcePanel, inputPanel);
    containerPanel.add(outputPanel);
    
    //display window
    frame.pack();
    frame.setVisible(true);
}
	
}
