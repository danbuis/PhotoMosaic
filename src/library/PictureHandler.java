package library;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

public class PictureHandler {

	public PictureHandler() {
		
	}
	
	
	/**
	 * uses Scalr library to scale the image
	 * 
	 * @param image - image to scale
	 * @param endTileWidth - target width
	 * @param endTileHeight - target height
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage image, int endTileWidth, int endTileHeight){
		BufferedImage returnImage = Scalr.resize(image, endTileWidth, endTileHeight);
		return returnImage;
	}
	
	public static BufferedImage cropImage (BufferedImage image, int endTileWidth, int endTileHeight){
		BufferedImage croppedImage;
		
		double currentAspectRatio = ((double)(image.getWidth()))/image.getHeight();
		double targetAspectRatio = ((double)(endTileWidth))/endTileHeight;
		
		//input is wider, so we need to lop some off the left and right
		if (targetAspectRatio < currentAspectRatio){
			//this value is how much larger current image is than the target.
			double verticalRatio = (double)image.getHeight()/endTileHeight;
			int targetWidth = (int)(endTileWidth*verticalRatio);
			
			int totalCroppedWidth = image.getWidth()-targetWidth;
			
			croppedImage = image.getSubimage(totalCroppedWidth/2, 0, targetWidth, image.getHeight());
			
		}else{
			//input is too tall, chop some off the top and bottom
			double horizontalRatio = image.getWidth()/endTileWidth;
			int targetHeight = (int)(endTileHeight*horizontalRatio);
			
			int totalCroppedHeight = image.getHeight()-targetHeight;
			
			croppedImage = image.getSubimage(0, totalCroppedHeight/2, image.getWidth(), totalCroppedHeight);
		}
		
		return croppedImage;
	}
	
	public static BufferedImage prepareTile(BufferedImage inputImage, int targetWidth, int targetHeight){
		BufferedImage croppedImage = cropImage(inputImage, targetWidth, targetHeight);
		
		return scaleImage(croppedImage, targetWidth, targetHeight);
		
	}

}
