package library;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PictureLibrary {
	
	public ArrayList<Tile> tileList = new ArrayList<Tile>();
	
	public PictureLibrary(File rootDirectory, double colorAdjustmentFreedom, int rowsOfBlocks, int colsOfBlocks){
		tileList = buildTileList(tileList, rootDirectory, colorAdjustmentFreedom, rowsOfBlocks, colsOfBlocks);
	}

	
	/**
	 * Build a set of tiles with from a given root directory
	 * 
	 * @param tileList
	 * @param rootDirectory
	 * @param colorAdjustmentFreedom
	 * @param rowsOfBlocks
	 * @param colsOfBlocks
	 * @return
	 */
	private ArrayList<Tile> buildTileList(ArrayList<Tile> tileList, File rootDirectory, double colorAdjustmentFreedom, int rowsOfBlocks, int colsOfBlocks) {
		
		File[] fileList = rootDirectory.listFiles();
		BufferedImage temp=null;
		
		for(File file:fileList){
			if(file.isDirectory()){
				//recurse if its another directory
				buildTileList(tileList, file, colorAdjustmentFreedom, rowsOfBlocks, colsOfBlocks);
			}else{
				
				//read the image file
				try {
					temp = ImageIO.read(file);
				} catch (IOException e) {
					System.out.println("could not find file: "+file);
					e.printStackTrace();
				}
				
				//prep the image file
				PictureHandler.prepareTile(temp, 250, 150);
				
				tileList.add(new Tile(temp, colorAdjustmentFreedom, rowsOfBlocks, colsOfBlocks));
			}
		}
		
		return tileList;
	}

}
