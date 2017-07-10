package library;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorArrayGenerator {

	public static Color[][] generateArray(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks) {
		Color[][] returnArray = new Color[rowsOfBlocks][colsOfBlocks];
		
		int imageHeight = baseImage.getHeight();
		int imageWidth = baseImage.getWidth();
		
		int blockHeight = imageHeight/rowsOfBlocks;
		int blockWidth = imageWidth/colsOfBlocks;
		
		for(int x=0; x<colsOfBlocks;x++){
			for (int y=0;y<rowsOfBlocks;y++){
				returnArray[x][y]=averageColor(baseImage.getSubimage(x*blockWidth, y*blockHeight, blockWidth, blockHeight));
				
			}//end y loop
		}//end x loop
		
		return returnArray;
	}



	public static Color averageColor(BufferedImage subimage){
		return null;
	
	}

}