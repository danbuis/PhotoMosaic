package library;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tile {
	
	BufferedImage image;
	double colorAdjustmentFreedom = 0.0;
	int rowsOfBlocks=1;
	int colsOfBlocks=1;
	
	public Color[][] colorArray;

	public Tile(BufferedImage baseImage, double colorAdjustmentFreedom, int rowsOfBlocks, int colsOfBlocks) {
		this.image = baseImage;
		this.rowsOfBlocks = rowsOfBlocks;
		this.colsOfBlocks=colsOfBlocks;
		this.colorAdjustmentFreedom=colorAdjustmentFreedom;
		
		colorArray = generateColorArray(baseImage, rowsOfBlocks, colsOfBlocks);
	}
	
public Tile(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks) {
		this(baseImage, 0.0, rowsOfBlocks, colsOfBlocks);

	}

public Color[][] generateColorArray(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks){
	
	Color[][] returnArray = new Color[colsOfBlocks][rowsOfBlocks];
	
	int imageHeight=baseImage.getHeight();
	int imageWidth =baseImage.getWidth();
	
	int blockHeight = imageHeight/rowsOfBlocks;
	int blockWidth = imageWidth/colsOfBlocks;
	
	//iterate over blocks
	for (int x=0; x< colsOfBlocks; x++){
		for (int y=0; y<rowsOfBlocks; y++){
			
			//iterate across pixels within that block
			int left = blockWidth*x;
			int right = blockWidth*(x+1)-1;
			int top = blockHeight*y;
			int bottom = blockHeight*(y+1)-1;
			
			int count=0;
			int redSum=0;
			int greenSum=0;
			int blueSum=0;
			
			for(int blockX = left; blockX<right; blockX++){
				for (int blockY = top; blockY<bottom; blockY++){
					
					//process each pixel
					 int clr = image.getRGB(blockX, blockY);
					 redSum += (clr & 0x00ff0000) >> 16;
					 greenSum += (clr & 0x0000ff00) >> 8;
					 blueSum += clr & 0x000000ff;
					 count++;
					 
				} //end blockY
			}//end blockX
			
			//after block is summed, go ahead and get the average value and stick resulting Color
			//in array
			
			returnArray[x][y]=new Color(redSum/count, greenSum/count, blueSum/count);
			
			
		} //end y loop
	} //end x loop
	
	return returnArray;
}

}
