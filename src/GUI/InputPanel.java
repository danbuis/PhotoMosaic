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

import library.MosaicGenerator;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {

	public JFrame frame;
	public MosaicGenerator generator;
	
	private JButton selectInputDirButton;
	private JButton processImagesButton;
	private JLabel repoLocationLabel = new JLabel("");
	
	private String inputDir;
	
	public InputPanel(MosaicGenerator generator, JFrame frame) {
		this.frame = frame;
		this.generator = generator;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		createInputs();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel Repolabel = new JLabel ("Selected image repository :");
		this.add(Repolabel);
		this.add(repoLocationLabel);
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
		if(inputDir!=null){
			processImagesButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					//when clicked and have an input directory...
					clickProcessImages();
					System.out.println("processed images");
				}
			});
		}
		this.add(processImagesButton);
		
	}
	
	private void clickProcessImages(){
		generator = new MosaicGenerator(new File(inputDir), 0);
	}
	
	private void clickImageRepoButton() {
		 JFileChooser chooser = new JFileChooser();
           chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
           chooser.setAcceptAllFileFilterUsed(false);
           
           if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
           	inputDir = chooser.getCurrentDirectory().toString();
           	System.out.println(inputDir);
           	this.repoLocationLabel.setText(inputDir);
           }
		
	}


}
