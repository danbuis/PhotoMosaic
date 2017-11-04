package JUnit;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import library.Tile;

public class ColorArrayTests {
	
	//custom sort of red color.  can't get photoshop to generate "pure" red
	Color sortOfRed = new Color(254, 0, 0);

	/**
	 * This test processes a very large picture so that there is a baseline for how efficient the 
	 * algorithm is for processing.
	 * 
	 * Currently runs in 1.9 seconds
	 */
	@Test
	public void testTimeForLargePicture() {
		BufferedImage testImage = null;
		String fileName = "image_repo/Test/reallyLargeTest.png";
		try{
			testImage = ImageIO.read(new File(fileName));
		}catch (IOException e){
			System.out.println("Very large image not found");
			e.printStackTrace();
		}
		
		Tile resultTile = new Tile(testImage, 10, 10, fileName);
		
		//trivial assertion just to make sure that the process runs at all.
		//process
		assertTrue(resultTile.colorArray[0][0]!=Color.CYAN);
	}
	
	/**
	 * basic test for color averages.  Uses a solid color
	 */
	@Test
	public void testSolids(){
		BufferedImage testRed = null;
		try{
			testRed = ImageIO.read(new File("image_repo/Test/testBlocksRed.jpg"));
		}catch (IOException e){
			System.out.println("Solid red image not found");
			e.printStackTrace();
		}
		
		BufferedImage testBlack = null;
		try{
			testBlack = ImageIO.read(new File("image_repo/Test/testBlocksBlack.jpg"));
		}catch (IOException e){
			System.out.println("Solid black image not found");
			e.printStackTrace();
		}
		
		//test two solid colors
		Tile redTile = new Tile(testRed,1,1, "image_repo/Test/testBlocksRed.jpg");
		
		assertEquals(sortOfRed, redTile.colorArray[0][0]);
		
		Tile blackTile = new Tile(testBlack,1,1,"image_repo/Test/testBlocksBlack.jpg");
		assertEquals(Color.BLACK, blackTile.colorArray[0][0]);
		
		//test a few different block sizes
		Tile redTile2 = new Tile(testRed,2,2,"image_repo/Test/testBlocksRed.jpg");
		assertEquals(sortOfRed, redTile2.colorArray[0][0]);
		assertEquals(sortOfRed, redTile2.colorArray[0][1]);
		assertEquals(sortOfRed, redTile2.colorArray[1][1]);
		
		Tile redTile3 = new Tile(testRed,3,3,"image_repo/Test/testBlocksRed.jpg");
		assertEquals(sortOfRed, redTile3.colorArray[0][0]);
		assertEquals(sortOfRed, redTile3.colorArray[2][1]);
		assertEquals(sortOfRed, redTile3.colorArray[2][2]);
		
		
	}
	
	/**
	 * test to make sure that bounds between blocks are drawn as expected.  Uses checkerboard images
	 */
	@Test
	public void testCheckers(){
		BufferedImage test5x5 = null;
		try{
			test5x5 = ImageIO.read(new File("image_repo/Test/testBlocks 5x5.gif"));
		}catch (IOException e){
			System.out.println("5x5 checks image not found");
			e.printStackTrace();
		}
		
		
		//delta of 2 allows for a difference of 1-2 in any of the RGB values.  needed due to artifacts from image saving.
		//its been hard to get pure colors to translate from image editor to the Color info in BufferedImage.  a difference of 
		//2 is not visible to the human eye.
		Tile tile5x5 = new Tile(test5x5,2,2,"image_repo/Test/testBlocks 5x5.gif");
		assertEquals(tile5x5.colorDistance(Color.BLACK, tile5x5.colorArray[0][0]),0,2);
		assertEquals(tile5x5.colorDistance(sortOfRed, tile5x5.colorArray[0][1]),0,2);
		assertEquals(tile5x5.colorDistance(sortOfRed, tile5x5.colorArray[1][0]),0,2);
		assertEquals(tile5x5.colorDistance(Color.BLACK, tile5x5.colorArray[1][1]),0,2);
		
		BufferedImage test6x6 = null;
		try{
			test6x6 = ImageIO.read(new File("image_repo/Test/testBlocks 6x6.jpg"));
		}catch (IOException e){
			System.out.println("6x6 checks image not found");
			e.printStackTrace();
		}
		
		Tile tile6x6 = new Tile(test6x6,2,2,"image_repo/Test/testBlocks 6x6.jpg");
		
		assertEquals(0,tile6x6.colorDistance(sortOfRed, tile6x6.colorArray[0][0]),2);
		assertEquals(0,tile6x6.colorDistance(Color.BLACK, tile6x6.colorArray[0][1]),2);
		assertEquals(0,tile6x6.colorDistance(Color.BLACK, tile6x6.colorArray[1][0]),2);
		assertEquals(0,tile6x6.colorDistance(sortOfRed, tile6x6.colorArray[1][1]),2);
		

		BufferedImage testUneven = null;
		try{
			testUneven = ImageIO.read(new File("image_repo/Test/testBlocks 3x6.gif"));
		}catch (IOException e){
			System.out.println("6x6 checks image not found");
			e.printStackTrace();
		}
		
		Tile tileUneven = new Tile(testUneven,4,2,"image_repo/Test/testBlocks 3x6.gif");
		
		assertEquals(0,tileUneven.colorDistance(sortOfRed, tileUneven.colorArray[0][0]),2);
		assertEquals(0,tileUneven.colorDistance(Color.BLACK, tileUneven.colorArray[1][0]),2);
		assertEquals(0,tileUneven.colorDistance(Color.BLACK, tileUneven.colorArray[0][1]),2);
		assertEquals(0,tileUneven.colorDistance(sortOfRed, tileUneven.colorArray[1][1]),2);
		assertEquals(0,tileUneven.colorDistance(sortOfRed, tileUneven.colorArray[0][2]),2);
		assertEquals(0,tileUneven.colorDistance(Color.BLACK, tileUneven.colorArray[1][2]),2);
		assertEquals(0,tileUneven.colorDistance(Color.BLACK, tileUneven.colorArray[0][3]),2);
		assertEquals(0,tileUneven.colorDistance(sortOfRed, tileUneven.colorArray[1][3]),2);
		
	}

}
