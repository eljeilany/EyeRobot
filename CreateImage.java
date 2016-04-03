import java.io.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.awt.Graphics;


public class CreateImage{
	
	
	public CreateImage() {};
	
	/**
	 * @param color : l'entier spécifié pour crée une couleur 
	 * @return arr : tableau contenant la valeur de chaque couleur
	 */
	// Cette methode crée une couleur correspondant à l'entier spécifié (color) puis
    // place dans arr la valeur de chaque couleur
	
	public static int[] changeStat(int color) {
		int[] arr = new int[3];
		Color c =  new Color(color);
		
		arr[0] = c.getRed();
		arr[1] = c.getGreen();
		arr[2] = c.getBlue();
		return arr;
	}
	
	/* readImage a été aussi modifiée, au lieu de lire une image à partir du chemin du dossier dans lequel
	 * se trouve cette dernière, elle prend directement l'image comme paramètre grâce à BufferedImage,
	 * recupère sa hauteur et sa largeur puis à partir des deux boucles, rempli le tableau pixel avec les 
	 * données de l'image.
	 */
	
	/** @param image : l'image à lire et à transformer en dataset
	 *  @return pixel : tableau qui contiendra les datasets
	 **/
	public int[][] readImage(BufferedImage image) {
      
	        int height = image.getHeight();
	        int width = image.getWidth();
	              
	        int[][] pixel =new int[width][height];
	        
	        for(int i=0; i<width; i++){
	        	for(int j=0; j<height; j++){
	        		pixel[i][j] = image.getRGB(i, j);
	        		
	        		int[] tab = changeStat(pixel[i][j]);
	            	System.out.print(Arrays.toString(tab) + "\n");
	        	
	        		//System.out.println("No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());		
	        	}
	        }
	        return pixel;
	}
	
	/* Méthode qui remplace createImage. Elle fait donc la même que createImage, la seule différence est qu'elle prend
	 * un tableau en paramètre et utilise cette dernière pour initialiser width (qui prend la taille de la largeur 
	 * du tableau passé en paramètre et height (celle de sa hauteur).
	 * En gros tab est le tableau qui contiendra les données qui seront transformées en image. 
	 */
	/**
	 * @param tab : tableau de données à convertir en image
	 */
	
	public void makeImage(int[][] tab) {
        ///////create Image from PixelArray
		
		try {
			int width = tab.length;
			int height = tab[0].length;
			
			BufferedImage Image2=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for(int y=0; y<height; y++){
				for(int x=0; x<width; x++){
					Image2.setRGB(x, y, tab[x][y]);
				}
			}

         File outputfile = new File("/home/balde/Alhassana/newImage.jpg");
            ImageIO.write(Image2, "jpg", outputfile);
		}
        catch(Exception ee){
            ee.printStackTrace();
        }
    }

    public static void main(String args[]){
    	try {
    	BufferedImage image = ImageIO.read(new File("/home/balde/Documents/Display_Images/jordan.jpg"));
    	
        CreateImage c = new CreateImage();
        
        int[][] pixel = c.readImage(image);
        c.makeImage(pixel);
        
        /*for(int i=0; i<pixel.length; i++) {
        	for(int j=0; j<pixel[0].length; j++) {
        		int[] tab = changeStat(pixel[i][j]);
        	System.out.print(Arrays.toString(tab) + "\n");
        	}	
        }*/
        
    	} catch(Exception ee) {
    		ee.printStackTrace();
    	}
    }
}
