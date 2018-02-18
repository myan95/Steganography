/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Nancy Sherif IME
 */
public class findMessage {
    
    
    int[][] redValues = null ; 
    
    public boolean[][] getMessage (BufferedImage modImage) throws IOException
    {
       int[][] redValues = convertTo2DUsingGetRGB(modImage);
       boolean[][] TMessage = GetMessage(redValues);
       modImage(TMessage);
       boolean[][] Message = trasposeMatrix(TMessage);
       return Message; 
    }
    
    
    
      public static boolean [][] trasposeMatrix(boolean [][] matrix)
{
    int m = matrix.length;
    int n = matrix[0].length;

    boolean[][] trasposedMatrix = new boolean[n][m];

    for(int x = 0; x < n; x++)
    {
        for(int y = 0; y < m; y++)
        {
            trasposedMatrix[x][y] = matrix[y][x];
        }
    }

    return trasposedMatrix;
}
    
 
    
    
    public boolean[][] GetMessage(int[][] redValues){
    
        int row1 = redValues.length;
        int col1 = redValues[0].length;
        System.out.println("mess row "+row1);
        System.out.println("mess col "+col1);
        boolean[][] TMessage = new boolean[row1][col1]; 
        
        for (int row = 0; row < redValues.length; row++) {
            for (int col = 0; col < redValues[0].length; col++) {
        
             if ( redValues[row][col] % 2 == 0 )
            {
                
                System.out.println("even");
                TMessage[row][col] = true ; 
            }
            else
            {
                System.out.println("odd");
                TMessage[row][col]= false; 
            }
            
            }
        }
    
    return TMessage; 
    }
    
    
    public void modImage (boolean[][] Message) throws IOException{
       
     
        int width = Message.length;
        int height = Message[0].length;
        

        BufferedImage b = new BufferedImage(height,width, 3);

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                
                /*
                result[row][col] = image.getRGB(col, row);
               // int alpha = (result[row][col] & 0x0000ff00) >> 24; 
                redValues[row][col] = (result[row][col] & 0x00ff0000) >> 16;
                int green = (result[row][col] & 0x0000ff00) >> 8;
                int blue = result[row][col] & 0x000000ff;
                
                System.out.println("orig red "+redValues[row][col]);
                System.out.println("T   "+TransRedV[row][col]);
                int instColor = new Color(TransRedV[row][col],green,blue).getRGB(); 
                */
                int instColor ; 
                if(Message[row][col]==false){
                instColor = new Color(0,0,0).getRGB(); 
                }
                else {instColor = new Color(255,255,255).getRGB();}
                
                
                b.setRGB(col, row,instColor);
                
            }
        }
            
        ImageIO.write(b, "png", new File("Message.png"));
        System.out.println("end");
        
        
}
    
    
    
    private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("image row"+height);
        System.out.println("image column "+width);
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
