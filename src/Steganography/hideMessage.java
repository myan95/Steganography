package Steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nancy Sherif IME
 */
public class hideMessage {
    
  int[][] redValues = null ; 
    
    
    // Main Method 
    public BufferedImage Hide(boolean[][] message,BufferedImage image) throws IOException{
     
    int[][] redValues = convertTo2DUsingGetRGB(image);
    int[][] invRedValues = trasposeMatrix(redValues); 
    int[][] modifiedRedV = Operate(message,invRedValues);
  
    int[][] modifiedImage = modImage(modifiedRedV, image); 
    
    File file = new File("modifiedImage.png"); // I have bear.jpg in my working directory  
    FileInputStream fis = new FileInputStream(file);
    BufferedImage imageModified = ImageIO.read(fis); //reading the image file
    
    return imageModified;
    
    }
    
    
    
    
    
    public int[][] modImage (int[][] modifiedRedV, BufferedImage image ) throws IOException{
       
        // value of reg to replace the below red value 
        int[][] TransRedV = trasposeMatrix(modifiedRedV);

        
        int widthT = TransRedV.length; 
        int heightT = TransRedV[0].length; 
        int width = image.getWidth();
        int height = image.getHeight();

        int[][] result = new int[height][width];
        int[][] redValues = new int[height][width];
        int[][] modImage = new int [height][width]; 
        //byte[][] byteImg = new byte [height][width];
        int[][] myColor = new int [height][width];
        BufferedImage b = new BufferedImage(heightT,widthT, 3);
        for (int row = 0; row < widthT; row++) {
            for (int col = 0; col < heightT; col++) {
                
                
                result[row][col] = image.getRGB(col, row);
               // int alpha = (result[row][col] & 0x0000ff00) >> 24; 
                redValues[row][col] = (result[row][col] & 0x00ff0000) >> 16;
                int green = (result[row][col] & 0x0000ff00) >> 8;
                int blue = result[row][col] & 0x000000ff;
                
                System.out.println("orig red "+redValues[row][col]);
                System.out.println("T   "+TransRedV[row][col]);
                int instColor = new Color(TransRedV[row][col],green,blue).getRGB(); 
                 b.setRGB(col, row,instColor);
                

                System.out.println(" original result [ "+row+" ] [ "+col+" ] equals "+result[row][col]);
                System.out.println(" my color [ "+row+" ] [ "+col+" ] equals "+instColor);
            }
        }
            
        ImageIO.write(b, "png", new File("modifiedImage.png"));
        System.out.println("end");
        
        
        return result;
}
    
     
   public static int[][] trasposeMatrix(int[][] matrix)
{
    int m = matrix.length;
    int n = matrix[0].length;

    int[][] trasposedMatrix = new int[n][m];

    for(int x = 0; x < n; x++)
    {
        for(int y = 0; y < m; y++)
        {
            trasposedMatrix[x][y] = matrix[y][x];
        }
    }

    return trasposedMatrix;
}
    
   
   
    
    public  int[][] Operate (boolean[][] message , int[][] redValues ){
    
    //check even or odd 
    int i,j;
    boolean Even = false ; 
    int[][] modifiedRedV = new int[225][196] ; 
   int width = redValues.length-1;
   int height = redValues[0].length-1; 
      //  System.out.println("width "+width);
      //  System.out.println("height "+height);
    
    for (i=0;i< width;i++){
       for(j=0;j< height;j++)
       {   
   
            //System.out.println(redValues[i][j]);
            //System.out.println(message[i][j]);
            if ( redValues[i][j] % 2 == 0 )
            {
                
                Even = true ; 
            }
            else
            {
                
                Even = false ; 
            }
            //white and even , white and odd (check 255 ) 
            //System.out.println("before if ");
            if((message[i][j]== true) && (Even == true)){
              modifiedRedV[i][j] = (redValues[i][j]);
            }
            // white and odd
            else if ((message[i][j]== true)&& (Even == false))
            {
                // equal max , then minus 1 
              if (redValues[i][j] == 255 )
              {    
                   modifiedRedV[i][j] = ((redValues[i][j])-1);
                 
              }
              else {modifiedRedV[i][j] = ((redValues[i][j])+1);}
            }
            // black and odd , black even check for (0) 
            else if ((message[i][j]== false)&&(Even == false))
            { modifiedRedV[i][j] = (redValues[i][j]);}
            // black and even 
            else if ((message[i][j]== false)&&(Even == true))
            {
              if(redValues[i][j] == 0)
               {modifiedRedV[i][j] =( (redValues[i][j])+1);}
              else 
               {modifiedRedV[i][j] = ((redValues[i][j])-1);}
            }
            else {System.out.println("Error");}
       
       }
    }
    return modifiedRedV; 
 }
   
    
    
    
    
    
    
    
     private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];
        int[][] redValues = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
                redValues[row][col] = (result[row][col] & 0x00ff0000) >> 16;
                int green = (result[row][col] & 0x0000ff00) >> 8;
                int blue = result[row][col] & 0x000000ff;
                
                
                //System.out.println("result equals  : [ " + row + " ] [ " + col + " ]  : "+result[row][col]);
                //System.out.println("Red Color value = " + redValues[row][col]);
                //System.out.println("Green Color value = " + green);
                //System.out.println("Blue Color value = " + blue);
            }
        }

        return redValues;
    }
     
     

    
    
}
