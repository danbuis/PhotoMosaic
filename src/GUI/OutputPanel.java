package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
	
	private JButton selectSaveLocationButton;
	private JLabel saveLocationLabel = new JLabel("");
	public String saveLocationString;
	
	private JButton processMosaicButton;
	
	public SourcePanel sourcePanel;
	public InputPanel inputPanel;

	public OutputPanel(SourcePanel sourcePanel2, InputPanel inputPanel2) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.sourcePanel = sourcePanel2;
		this.inputPanel = inputPanel2;

		arrangeComponents();
	}

	private void arrangeComponents() {
			selectSaveLocationButton = new JButton("Select Save Location");
		
				selectSaveLocationButton.addActionListener(new ActionListener(){
						@Override
							public void actionPerformed(ActionEvent e){
								//what to do when clicked
								clickGetSaveButton();
				
			}

		});
		
		this.add(selectSaveLocationButton);
		this.add(saveLocationLabel);
		
		processMosaicButton = new JButton("Create Mosaic");
		
		processMosaicButton.addActionListener(new ActionListener(){
			@Override
				public void actionPerformed(ActionEvent e){
				//what to do when saved
				//check if things are
				System.out.println("source file");
				System.out.println(sourcePanel.sourceFile.toString());
				
				if(saveLocationString!=null &&
				   sourcePanel.sourceFile != null &&
				   inputPanel.inputSelected){
				 
				  mosaicify();
				}
			}
		});
		
		this.add(processMosaicButton);
	}
	
	public void mosaicify(){
		
		BufferedImage sourceImage=null;
		try{
			sourceImage = ImageIO.read(sourcePanel.sourceFile);
		}catch(IOException e){
			System.out.println("Source not found");
			e.printStackTrace();
		}
		
		int targetWidth = sourceImage.getWidth()*4;
		int targetHeight = sourceImage.getHeight()*4;
		
		BufferedImage result = this.inputPanel.generator.mosaicify(sourceImage, 0.0, targetWidth, targetHeight);
		
		try{
			ImageIO.write(result, "png", new File(this.saveLocationString+".png"));
		}catch (IOException e){
			System.out.println("save location not valid");
			e.printStackTrace();
		}
	}
	
	public void clickGetSaveButton(){
		JFileChooser chooser = new JFileChooser();
		
		if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			this.saveLocationString=chooser.getSelectedFile().toString();
			this.saveLocationLabel.setText(saveLocationString);
			
		}
	}

}
