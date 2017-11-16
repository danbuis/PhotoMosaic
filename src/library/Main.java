package library;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	/*public static void main(String[] args) {
		
		testMosaic();

	}*/
	
	public static void testMosaic(){
		File testFolder = new File("image_repo");
		
		MosaicGenerator gen = new MosaicGenerator(testFolder, 0);
		
		BufferedImage masterImage = null;
		try{
			masterImage = ImageIO.read(new File("big/dragon.jpg"));
		}catch (IOException e){
			System.out.println("master image not found");
			e.printStackTrace();
		}
		System.out.println("beginning test mosaic");
		BufferedImage resultMosaic = gen.mosaicify(masterImage, 0.0, 6000, 4245);
		
		try {
			ImageIO.write(resultMosaic, "jpg", new File("big/dragonResult.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
