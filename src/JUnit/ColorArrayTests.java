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

	/**
	 * This test processes a very large picture so that there is a baseline for how efficient the 
	 * algorithm is for processing.
	 * 
	 * Currently runs in 1.9 seconds
	 */
	@Test
	public void testTimeForLargePicture() {
		BufferedImage testImage = null;
		try{
			testImage = ImageIO.read(new File("image_repo/Test/reallyLargeTest.png"));
		}catch (IOException e){
			System.out.println("Too Tall image not found");
			e.printStackTrace();
		}
		
		Tile resultTile = new Tile(testImage, 10, 10);
		
		//trivial assertion just to make sure that the process runs at all.
		//process
		assertTrue(resultTile.colorArray[0][0]!=Color.CYAN);
	}

}
