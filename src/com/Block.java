package com;

import java.util.*;

public class Block 
{
    private char[][] shape;
    private int height;
    private int width;

    public Block(String shapeStr) 
    {
        String[] lines = shapeStr.split("\n");
        height = lines.length;
        width = 0;
        for (String line : lines) 
        {
            if (line.length() > width) 
            {
                width = line.length();
            }
        }
        width -= 1;
        shape = new char[height][width];

        for (int i = 0; i < height; i++) 
        {
            String line = lines[i];
            for (int j = 0; j < width; j++) 
            {
                try 
                {
                    char cc = line.charAt(j);
                    if (cc >= 65 && cc <= 90) shape[i][j] = cc;
                    else shape[i][j] = '.';
                } 
                catch (Exception e) 
                {
                    shape[i][j] = '.';
                }
            }
        }
    }

    public Block(char[][] shape) 
    {
        this.height = shape.length;
        this.width = shape[0].length;
        this.shape = new char[height][width];
        for (int i = 0; i < height; i++) 
        {
            System.arraycopy(shape[i], 0, this.shape[i], 0, width);
        }
    }

    public int getHeight() 
    {
        return height;
    }

    public int getWidth() 
    {
        return width;
    }

    public char[][] getShape() 
    {
        return shape;
    }

    public Block getRotated() 
    {
        int newH = width;
        int newW = height;
        char[][] newShape = new char[newH][newW];
        for (int i = 0; i < newH; i++) 
        {
            for (int j = 0; j < newW; j++) 
            {
                newShape[i][j] = shape[height - 1 - j][i];
            }
        }
        return new Block(newShape);
    }

    public Block getMirrored() 
    {
        char[][] newShape = new char[height][width];
        for (int i = 0; i < height; i++) 
        {
            for (int j = 0; j < width; j++) 
            {
                newShape[i][j] = shape[i][width - 1 - j];
            }
        }
        return new Block(newShape);
    }

    public List<Block> getOrientations() 
    {
        List<Block> orientations = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        Block current = this;
        for (int i = 0; i < 4; i++) 
        {
            String rep = current.toString();
            if (!seen.contains(rep)) 
            {
                orientations.add(current);
                seen.add(rep);
            }
            Block mirrored = current.getMirrored();
            rep = mirrored.toString();
            if (!seen.contains(rep)) 
            {
                orientations.add(mirrored);
                seen.add(rep);
            }
            current = current.getRotated();
        }
        return orientations;
    }

    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) 
        {
            sb.append(new String(shape[i]));
            if (i < height - 1) sb.append("\n");
        }
        return sb.toString();
    }
}
