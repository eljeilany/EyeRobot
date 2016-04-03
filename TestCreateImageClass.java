import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

public class TestCreateImageClass {
	
	CreateImage img;

	@Before
	public void createImageTest() throws Exception {
		img = new CreateImage();
	}

	// Test si deux dataset contiennent les même données ou non 
	// Si on remplace image par image2 parmi les deux image passées en paramètre dans assertEquals 
	// on verra que le test ne va pas marcher, car image2 contient des données différents que celles de image
 
	@Test 
	public void testReadImage() throws Exception {
		BufferedImage image = ImageIO.read(new File("/home/balde/Documents/Display_Images/jordan.jpg"));
		BufferedImage image2 = ImageIO.read(new File("/home/balde/Documents/Display_Images/cristiano-ronaldo-ballon.jpg"));
		assertEquals(img.readImage(image), img.readImage(image));
	}
	
/*	@Test
	public void testMakeImage() throws Exception {
		BufferedImage image = ImageIO.read(new File("/home/balde/Documents/Display_Images/jordan.jpg"));
		int[][] tab1 = img.readImage(image);
		
		BufferedImage image2 = ImageIO.read(new File("/home/balde/Documents/Display_Images/cristiano-ronaldo-ballon.jpg"));
		int[][] tab2 = img.readImage(image2);
		assertEquals(img.makeImage(tab1), img.makeImage(tab));
	}
*/
}
