package com;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Image
{
    public static void saveAsImage(Board board, long startTime, long endTime, long iterations, String fileName, boolean isSolutionFound) 
    {
        double time = (endTime - startTime) * 1E-6;

        int cellSize = 40;
        int imageWidth = board.getM() * cellSize;
        int imageHeight = board.getN() * cellSize + 60; 

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.setColor(Color.BLACK);
        for (int i = 0; i < board.getN(); i++) 
        {
            for (int j = 0; j < board.getM(); j++) 
            {
                char cell = board.getBoard()[i][j];
                Color cellColor = getColorForCell(cell);

                g.setColor(cellColor);
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize); 
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Waktu Pencarian: " + String.format("%.3f", time) + " ms", 10, imageHeight - 40);
        g.drawString("Jumlah Iterasi: " + iterations, 10, imageHeight - 20);

        if (!isSolutionFound) 
        {
            g.setColor(Color.RED);
            g.drawString("Tidak ada solusi ditemukan!s", 10, imageHeight - 60);
        }

        Path filePath = Paths.get("test", fileName + ".png");
        try 
        {
            ImageIO.write(image, "PNG", filePath.toFile());
            System.out.println("Hasil disimpan dalam gambar: " + filePath.toFile().getAbsolutePath());
        } 
        catch (IOException e) 
        {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
        g.dispose();
    }

    private static Color getColorForCell(char cell) 
    {
        if (cell == '.') return Color.WHITE; 
        if (cell >= 'A' && cell <= 'Z') 
        {
            int colorIndex = (cell - 'A') % 26; 
            switch (colorIndex) {
                case 0: return Color.RED;
                case 1: return Color.GREEN;
                case 2: return Color.YELLOW;
                case 3: return Color.BLUE;
                case 4: return Color.CYAN;
                case 5: return Color.MAGENTA;
                case 6: return Color.ORANGE;
                case 7: return new Color(255, 165, 0);  
                case 8: return new Color(75, 0, 130);  
                case 9: return new Color(255, 20, 147);  
                case 10: return new Color(60, 179, 113);
                case 11: return new Color(255, 99, 71);  
                case 12: return new Color(138, 43, 226);
                case 13: return new Color(0, 255, 255);  
                case 14: return new Color(0, 128, 0);    
                case 15: return new Color(255, 69, 0);   
                case 16: return new Color(240, 128, 128); 
                case 17: return new Color(255, 215, 0);   
                case 18: return new Color(34, 139, 34);   
                case 19: return new Color(255, 222, 173); 
                case 20: return new Color(219, 112, 147); 
                case 21: return new Color(238, 130, 238); 
                case 22: return new Color(255, 228, 181); 
                case 23: return new Color(240, 128, 128); 
                case 24: return new Color(144, 238, 144); 
                case 25: return new Color(72, 61, 139);  
                default: return Color.BLACK; 
            }
        }
        return Color.BLACK; 
    }
}
