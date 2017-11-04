package library;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tile {
	
	BufferedImage image;
	double colorAdjustmentFreedom = 0.0;
	int rowsOfBlocks=1;
	int colsOfBlocks=1;
	public String file;
	
	public Color[][] colorArray;

	public Tile(BufferedImage baseImage, double colorAdjustmentFreedom, int rowsOfBlocks, int colsOfBlocks, String file) {
		this.image = baseImage;
		this.rowsOfBlocks = rowsOfBlocks;
		this.colsOfBlocks=colsOfBlocks;
		this.colorAdjustmentFreedom=colorAdjustmentFreedom;
		this.file=file;
		
		colorArray = generateColorArray(baseImage, rowsOfBlocks, colsOfBlocks);
	}
	
public Tile(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks, String file) {
		this(baseImage, 0.0, rowsOfBlocks, colsOfBlocks, file);

	}

public Color[][] generateColorArray(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks){
	
	Color[][] returnArray = new Color[colsOfBlocks][rowsOfBlocks];
	
	int imageHeight=baseImage.getHeight();
	int imageWidth =baseImage.getWidth();
	
	int blockHeight = imageHeight;
	//protect against accidental div by 0
	if(rowsOfBlocks>0){blockHeight/=rowsOfBlocks;}
	
	int blockWidth = imageWidth;
	if(colsOfBlocks>0){blockWidth/=colsOfBlocks;}
	
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
					Color pixelColor = new Color(clr, true);
					 
					
					redSum+=pixelColor.getRed();
					
					
					greenSum+=pixelColor.getGreen();
					
				
					blueSum+=pixelColor.getBlue();
					
					count++;
					 
				} //end blockY
			}//end blockX
			
			//after block is summed, go ahead and get the average value and stick resulting Color
			//in array
			
			returnArray[x][y]=new Color(redSum/count, greenSum/count, blueSum/count);
			//System.out.println(returnArray[x][y]);
			
			
		} //end y loop
	} //end x loop
	
	return returnArray;
}


/**
 * equation taken from wikipedia.  Light-weight equation that accounts for difference in color perception,
 * ie that a smaller change in red is percieved equivalent to a larger change in blue
 * 
 * @param firstColor
 * @param secondColor
 * @return
 */
public double colorDistance (Color firstColor, Color secondColor){
	double distance = 0.0;
	
	int firstRed = firstColor.getRed();
	int secondRed = secondColor.getRed();
	
	int firstGreen = firstColor.getGreen();
	int secondGreen = secondColor.getGreen();
	
	int firstBlue = firstColor.getBlue();
	int secondBlue = secondColor.getBlue();
	
	double averageRed = (firstRed+secondRed)/2.0;
	int deltaRed = firstRed-secondRed;
	int deltaGreen = firstGreen-secondGreen;
	int deltaBlue = firstBlue-secondBlue;
	
	double redComponent = (2+averageRed/256)*deltaRed*deltaRed;
	
	double greenComponent = 4*deltaGreen*deltaGreen;
	
	double blueComponent = (2+(255-averageRed)/256)*deltaBlue*deltaBlue;
	
	distance = Math.sqrt(redComponent+blueComponent+greenComponent);
	
	return distance;
	
}

public double totalColorDistance(Color[][] outsideColorArray){
	double returnDouble = 0.0;
	
	for (int colorDistX=0; colorDistX<colsOfBlocks; colorDistX++){
		for(int colorDistY=0; colorDistY<rowsOfBlocks; colorDistY++){
			returnDouble+= this.colorDistance(colorArray[colorDistX][colorDistY], outsideColorArray[colorDistX][colorDistY]);
		}
	}
	
	return returnDouble;
}

}
