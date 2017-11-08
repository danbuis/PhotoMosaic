package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import library.MosaicGenerator;

public class MainGUI {

	static MosaicGenerator generator;
	static JFrame frame;
	
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
	containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
    frame.add(containerPanel);
    
    JPanel inputPanel = new InputPanel(generator, frame);
    containerPanel.add(inputPanel);
    
    //display window
    frame.pack();
    frame.setVisible(true);
}
	
}
