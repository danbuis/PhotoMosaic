package JUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import library.PictureLibrary;

public class Overall {

	@Test
	public void testPictureLibraryTileGeneration() {
		
		File testFolder = new File("image_repo/Test/nest 1");
		
		
		PictureLibrary lib = new PictureLibrary(testFolder, 0, 5, 3);
		
		assertEquals(20, lib.tileList.size());
	}

}
