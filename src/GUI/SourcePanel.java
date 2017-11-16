package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SourcePanel extends JPanel {
	
	public InputPanel inputPanel;
    private JButton selectSourceImageButton;
    private JLabel selectSourceLocation = new JLabel("");
    
    public File sourceFile;
	
	public SourcePanel(InputPanel inputPanel) {
		this.inputPanel=inputPanel;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		arrangeComponents();
	}

	private void arrangeComponents() {
		selectSourceImageButton = new JButton("Select Source Image");
		
		selectSourceImageButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//what to do when clicked
				clickGetSourceButton();
				
			}

		});
		
		this.add(selectSourceImageButton);
		this.add(selectSourceLocation);
	}
	
	private void clickGetSourceButton() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if(inputPanel.inputDir != null){
			chooser.setCurrentDirectory(inputPanel.inputDir);
		}
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			this.sourceFile=chooser.getSelectedFile();
			
			this.selectSourceLocation.setText(sourceFile.getAbsolutePath());
		}
		
	}

}
