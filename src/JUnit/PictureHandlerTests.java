package JUnit;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import library.PictureHandler;

public class PictureHandlerTests {
	
	PictureHandler pictureHandler = new PictureHandler();

	@Test
	public void testCropImageTooTall(){
		BufferedImage testImage = null;
		try{
			testImage = ImageIO.read(new File("image_repo/Test/TooTall.jpg"));
		}catch (IOException e){
			System.out.println("Too Tall image not found");
			e.printStackTrace();
		}
		
		BufferedImage result = pictureHandler.cropImage(testImage, 250, 150);
		
		assertEquals(testImage.getWidth(),result.getWidth());
		assertFalse(testImage.getHeight() == result.getHeight());
		
		//Save image for visual confirmation
		try {
			ImageIO.write(result, "png", new File("image_repo/Test/TooTallResult.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCropImageTooWide(){
		BufferedImage testImage = null;
		try{
			testImage = ImageIO.read(new File("image_repo/Test/TooWide.jpg"));
		}catch (IOException e){
			System.out.println("Too Wide image not found");
			e.printStackTrace();
		}
		
		BufferedImage result = pictureHandler.cropImage(testImage, 250, 150);
		
		assertEquals(testImage.getHeight(),result.getHeight());
		assertFalse(testImage.getWidth() == result.getWidth());
		
		//Save image for visual confirmation
				try {
					ImageIO.write(result, "png", new File("image_repo/Test/TooWideResult.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	@Test
	public void testCropAndScale(){
		BufferedImage testImage = null;
		try{
			testImage = ImageIO.read(new File("image_repo/Test/PrepareTile.jpg"));
		}catch (IOException e){
			System.out.println("test crop plus scale image not found");
			e.printStackTrace();
		}
		
		BufferedImage result = pictureHandler.prepareTile(testImage, 250, 150);
		
		assertEquals(result.getWidth(), 250);
		assertEquals(result.getHeight(), 150);
		
		//Save image for visual confirmation
		try {
			ImageIO.write(result, "png", new File("image_repo/Test/prepareTileResult.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
