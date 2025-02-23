package com;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Reader 
{

    public static TestCase readPuzzleFile(String fileName) throws IOException 
    {
        Path filePath = Paths.get("input", fileName);
        BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
       
        String[] firstLine = reader.readLine().split(" ");
        int N = Integer.parseInt(firstLine[0]);
        int M = Integer.parseInt(firstLine[1]);
        int P = Integer.parseInt(firstLine[2]);
       
        String S = reader.readLine().trim();
        List<String> puzzlePieces = new ArrayList<>();
        Character lastType = null;
        String pieceShape = "";
        int pieceCount = 0;
        while (pieceCount < P) 
        {
            String line = reader.readLine();
            if (line == null) 
            {
                puzzlePieces.add(pieceShape.substring(0,pieceShape.length()-1));
                break;
            }
            Character currType = null;
            for (char cc : line.toCharArray()) 
            {
                if (cc >= 65 && cc <= 90) 
                {
                    currType = cc;
                    break;
                }
            }
            if (lastType == null || lastType.equals(currType)) 
            {
                pieceShape += line + System.lineSeparator();
            } 
            else 
            {
                puzzlePieces.add(pieceShape.substring(0,pieceShape.length()-1));
                pieceShape = line + System.lineSeparator();
                pieceCount++;
            }
            lastType = currType;
        }
        reader.close();
        return new TestCase(N, M, P, S, puzzlePieces);
    }
}
