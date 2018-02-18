package Steganography;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Steganography {

    public static void main(String[] args) throws IOException {

        // reading the initial image 
        File file = new File("o.png"); // I have bear.jpg in my working directory  
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis); //reading the image file

        // reading the message image 
        File file1 = new File("m.png"); // I have bear.jpg in my working directory  
        FileInputStream fis1 = new FileInputStream(file1);
        BufferedImage image1 = ImageIO.read(fis1); //reading the image file
        
        
        // converting message image to boolean 
        
        boolean[][] secMessage = convertTo2Dbinary(image1);
        
        hideMessage HideMessage = new hideMessage();
       
        BufferedImage imageModified = HideMessage.Hide(secMessage, image);
        
        findMessage FindMessage = new findMessage();
        
        boolean[][] Message = FindMessage.getMessage(imageModified);
        
        compareMessage(secMessage, Message);
 
         
    }
    
    
    public static void compareMessage (boolean[][] secMessage, boolean [][] Message)
    {
       int counter =0; 
       int width = Message.length;
       int height = Message[0].length;
       int checker = width * height ; 
       for (int row =0; row <Message.length;row++){
         for (int col=0;col<Message[0].length ; col++){
              
             if(secMessage[row][col]== Message[row][col]){counter++;}
             
         }
       
       }
    
       if(counter==checker){System.out.println(" Message delivered");}
       else {System.out.println(" Message Error ");}
    }

       private static boolean[][] convertTo2Dbinary(BufferedImage image) {
       //white =true , black =false  
        Raster raster = image.getData();
        int w = raster.getWidth(), h = raster.getHeight();
        boolean pixels[][] = new boolean[w][h];
           System.out.println(" original message row "+w);
           System.out.println("original message width "+h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int intermediatePixel ; 
                intermediatePixel = raster.getSample(x, y, 0);
                
                if(intermediatePixel == 255 )
                { pixels[x][y] = true ;}
                else {pixels[x][y] = false ;}
              //   System.out.println("[ " + x + " ] [" + y + " ] : " + pixels[x][y]);
            }
        }

        return pixels;

    }

  

}
