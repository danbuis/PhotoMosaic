package library;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tile {
	
	BufferedImage image;
	double colorAdjustmentFreedom = 0.0;
	int rowsOfBlocks=1;
	int colsOfBlocks=1;
	
	Color[][] colorArray;

	public Tile(BufferedImage baseImage, double colorAdjustmentFreedom, int rowsOfBlocks, int colsOfBlocks) {
		this.image = baseImage;
		this.rowsOfBlocks = rowsOfBlocks;
		this.colsOfBlocks=colsOfBlocks;
		this.colorAdjustmentFreedom=colorAdjustmentFreedom;
		
		colorArray = ColorArrayGenerator.generateArray(baseImage, rowsOfBlocks,colsOfBlocks);
	}
	
public Tile(BufferedImage baseImage, int rowsOfBlocks, int colsOfBlocks) {
		this(baseImage, 0.0, rowsOfBlocks, colsOfBlocks);

	}

}
