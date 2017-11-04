package library;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MosaicGenerator {
	
	public ArrayList<Tile> tileList = new ArrayList<Tile>();
	public int columnsInTile=5;
	public int rowsInTile=3;
	public int tileWidth=125;
	public int tileHeight=75;
	public int distanceBetweenSameTile=4;
	
	public MosaicGenerator(File rootDirectory, double colorAdjustmentFreedom){
		tileList = buildTileList(tileList, rootDirectory, colorAdjustmentFreedom);
	}

	
	/**
	 * Build a set of tiles with from a given root directory
	 * 
	 * @param tileList
	 * @param rootDirectory
	 * @param colorAdjustmentFreedom
	 * @param rowsOfBlocks - in Tiles
	 * @param colsOfBlocks - in Tiles
	 * 
	 * @return
	 */
	private ArrayList<Tile> buildTileList(ArrayList<Tile> tileList, File rootDirectory, double colorAdjustmentFreedom) {
		
		File[] fileList = rootDirectory.listFiles();
		BufferedImage temp=null;
		
		for(File file:fileList){
			if(file.isDirectory()){
				//recurse if its another directory
				buildTileList(tileList, file, colorAdjustmentFreedom);
			}else{
				
				//read the image file
				try {
					temp = ImageIO.read(file);
				} catch (IOException e) {
					System.out.println("could not find file: "+file);
					e.printStackTrace();
				}
				
				//TODO filter for image files and ignore null
				
				//prep the image file
				try{
					temp=PictureHandler.prepareTile(temp, this.tileWidth, this.tileHeight);
				}catch(NullPointerException e){
					e.printStackTrace();
					System.out.println(file.getPath()+" is null");
				}
				
				System.out.println("Completed file: "+file.getPath());
				
				if(temp!=null){
					tileList.add(new Tile(temp, colorAdjustmentFreedom, this.rowsInTile, this.columnsInTile, file.getPath()));
				}
			}
		}
		
		return tileList;
	}
	
	public BufferedImage mosaicify(BufferedImage masterImage, File rootDirectory, double colorAdjustmentFreedom, int targetWidth, int targetHeight){
		BufferedImage mosaic = null;
		
		//determine dimensions of final image
		int masterWidth = masterImage.getWidth();
		int masterHeight = masterImage.getHeight();
		System.out.println("masterWidth "+masterWidth);
		System.out.println("masterHeight "+masterHeight);
		
		int mosaicWidth = this.tileWidth*(targetWidth/this.tileWidth);
		int mosaicHeight = this.tileHeight*(targetHeight/this.tileHeight);
		System.out.println("base stats set "+mosaicWidth+ "x"+mosaicHeight);
		
		int tilesWide = mosaicWidth/this.tileWidth;
		int tilesHigh = mosaicHeight/this.tileHeight;
		
		Tile[][] tileGrid= new Tile[tilesWide][tilesHigh];
		
		System.out.println("tilesWide "+tilesWide);
		System.out.println("tilesHigh "+tilesHigh);
		
		BufferedImage master = PictureHandler.prepareTile(masterImage, mosaicWidth, mosaicHeight);
		System.out.println("created master image");
		
		//set up blank image
		mosaic = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D mosaicGraphics = mosaic.createGraphics();
		System.out.println("set up blank image");
	
		
		Tile masterTile = new Tile(master, colorAdjustmentFreedom, mosaicHeight/25, mosaicWidth/25, "");
		Color[][] masterColors = masterTile.colorArray;
		
		//repeat for each tile in mosaic
		for (int x=0; x<tilesWide; x++){
			for (int y=0; y<tilesHigh; y++){
				//grab segment of array corresponding to next tile
				Color[][] tileColors = new Color[this.columnsInTile][this.rowsInTile];
				int initialX = x*this.columnsInTile;
				int initialY = y*this.rowsInTile;
				
				int count=0;
				
				for(int tileX=initialX; tileX<initialX+this.columnsInTile; tileX++){
					for(int tileY=initialY; tileY<initialY+this.rowsInTile; tileY++){
						count++;
						try{
						tileColors[tileX-initialX][tileY-initialY] 
								= masterColors[tileX][tileY];
						}catch(ArrayIndexOutOfBoundsException e){
							e.printStackTrace();
							System.out.println("count "+count);
							
							System.out.println("tileX="+tileX);
							System.out.println("tileY="+tileY);
							
							System.out.println("initialX="+initialX);
							System.out.println("initialY="+initialY);
							
							System.out.println("x="+x);
							System.out.println("y="+y);
							
							System.out.println("tilesWide = "+tilesWide);
							System.out.println("tilesHigh = "+tilesHigh);
							
							return mosaic;
						}
						
					}
				}
				
				
				//find closest tile
				Tile closestTile = tileList.get(0);
				double closestDistance = 100000000;
				
		
				for (Tile tile:tileList){
					
					// calculate total color distance
					double colorDist=tile.totalColorDistance(tileColors);
					
					if(colorDist<closestDistance && noOtherSameTilesNearby(tileGrid, tile, x, y)){
						closestTile = tile;
						closestDistance = colorDist;
					}
					
				}
				
				//stick in mosaic
				
				mosaicGraphics.drawImage(closestTile.image, null, initialX*25, initialY*25);
				
				//add to tileGrid
				tileGrid[x][y]=closestTile;
				
				/*try{
					ImageIO.write(mosaic, "jpg", new File("big/test"+x+"-"+y+".jpg"));
				}catch (IOException e){
					e.printStackTrace();
				}*/
				System.out.println ("completed tile ("+x+","+y+")");
				
			}//end y loop
		}//end x loop
		
		
		
		
		
		return mosaic;
		
	}


	private boolean noOtherSameTilesNearby(Tile[][] tileGrid, Tile tile, int x, int y) {
		
		int range=this.distanceBetweenSameTile;
		String baseTileFile = tile.file;
		System.out.println("checking this tile: "+baseTileFile);
		
		//first check same column, working upwards
		for(int i = 1; i<range;i++){
			//if this is in the array and it is the same tile
			System.out.println("i:"+i);
			System.out.println("x:"+x);
			System.out.println("y:"+y);
			if (y-i>=0){
				String fileTemp = tileGrid[x][y-i].file;
				System.out.println("against: "+fileTemp+ " i-"+i);
				
				if(baseTileFile.equals(fileTemp)){
					
					
					//repeat tile found
					System.out.println("repeat tile found");
					return false;
				}
				
			}
		}
		
		//then check each column left for repeats
		for(int i=1; i<range;i++){
			//if the column exists
			if(x-i>=0){
				
				//traverse row
				for (int k=(0-range); k<range; k++){
					if(y+k>=0 && y+k<=tileGrid[0].length){
						String fileTemp = null;

						System.out.println("looking at ("+(x-i)+","+(y+k)+")");
						System.out.println("x length:"+tileGrid.length);
						System.out.println("y length:"+tileGrid[0].length);
							fileTemp = tileGrid[x-i][y+k].file;
						
						
						if(fileTemp!=null && baseTileFile.equals(fileTemp)){
							
							
							//repeat tile found
							System.out.println("repeat tile found");
							return false;
						}
					}
				}
			}
		}

		//no repeat found
		return true;
	}

}
