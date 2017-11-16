package JUnit;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import library.MosaicGenerator;
import library.Tile;

public class Overall {

	@Test
	public void testPictureLibraryTileGeneration() {
		
		File testFolder = new File("image_repo/Test/nest 1");
		
		
		MosaicGenerator lib = new MosaicGenerator(testFolder, 0);
		
		assertEquals(20, lib.tileList.size());
	}
	
	@Test
	public void testMosaic(){
		File testFolder = new File("image_repo");
		
		MosaicGenerator gen = new MosaicGenerator(testFolder, 0);
		
		BufferedImage masterImage = null;
		try{
			masterImage = ImageIO.read(new File("big/reallyLargeTest.png"));
		}catch (IOException e){
			System.out.println("master image not found");
			e.printStackTrace();
		}
		System.out.println("beginning test mosaic");
		BufferedImage resultMosaic = gen.mosaicify(masterImage,  0.0, 5400,2700);
		
		try {
			ImageIO.write(resultMosaic, "png", new File("big/testResult.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testArraydims(){
		Tile[][] test = new Tile[3][50];
		
		assertEquals(3, test.length);
		assertEquals(50, test[1].length);
	}

}
