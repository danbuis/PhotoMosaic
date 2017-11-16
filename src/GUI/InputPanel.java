package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import library.MosaicGenerator;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {

	public JFrame frame;
	public MosaicGenerator generator;
	
	private JButton selectInputDirButton;
	private JButton processImagesButton;
	private JTextField repoLocationField = new JTextField("");
	
	public File inputDir;
	public Boolean inputSelected=false;
	
	public InputPanel(MosaicGenerator generator) {

		this.generator = generator;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		createInputs();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel Repolabel = new JLabel ("Selected image repository :");
		this.add(Repolabel);
		
		this.add(repoLocationField);
	}

	private void createInputs() {
		selectInputDirButton = new JButton("Select Image Repository");
		selectInputDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//when clicked, do this stuff
               clickImageRepoButton();
            }
        });
		
		this.add(selectInputDirButton);
		
		processImagesButton = new JButton("Prepare image tiles");
	
			processImagesButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					//when clicked and have an input directory...
					clickProcessImages();
					System.out.println("processed images");
				}
			});
		
		
		this.add(processImagesButton);
		
	}
	
	private void clickProcessImages(){
		if(inputDir !=null){
			System.out.println("processing images");
			generator = new MosaicGenerator(inputDir, 0);
		} else System.out.println("inputDir = null");
		
	}
	
	private void clickImageRepoButton() {
		 JFileChooser chooser = new JFileChooser();
           chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
           chooser.setAcceptAllFileFilterUsed(false);
           
           if (inputDir != null){
        	   chooser.setCurrentDirectory(inputDir);
           }
           
           if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
           	inputDir = chooser.getSelectedFile();
           	System.out.println(inputDir);
           	this.repoLocationField.setText(inputDir.getAbsolutePath());
           	this.inputSelected=true;
           }
		
	}


}
