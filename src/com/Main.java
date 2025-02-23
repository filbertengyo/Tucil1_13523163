package com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main 
{
    private static int iterations = 0;
    
    private static boolean solve(Board board, List<Block> blocks, int index) 
    {
        if (index == blocks.size()) 
        {
            if (board.isFull()) 
            {
                board.printBoard();
                return true; 
            }
            return false; 
        }

        Block block = blocks.get(index);
        List<Block> orientations = block.getOrientations();

        for (Block orient : orientations) 
        {
            for (int i = 0; i < board.getN(); i++) 
            {
                for (int j = 0; j < board.getM(); j++) 
                {
                    if (board.canPlaceBlock(orient, i, j)) 
                    {
                        iterations++;
                        board.placeBlock(orient, i, j, (char) ('A' + index));
                        if (solve(board, blocks, index + 1)) return true;
                        board.removeBlock(orient, i, j); 
                    }
                }
            }
        }

        return false; 
    }

    private static void saveResult(Board board, long startTime, long endTime, String fileName) 
    {
        Path filePath = Paths.get("test", fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) 
        {
            writer.write("Bentuk Akhir Papan:\n");
            board.printBoardToFile(writer);

            double time = (endTime - startTime) * 1E-6;
            writer.write("\nWaktu Pencarian: " + String.format("%.3f", time) + " ms\n");
            writer.write("Jumlah Iterasi: " + iterations + "\n");

            System.out.println("Hasil " + fileName + " berhasil disimpan dalam folder test!");
        } 
        catch (IOException e) 
        {
            System.out.println("Terjadi kesalahan saat menyimpan hasil: " + e.getMessage());
        }
    }

    public static void main(String[] args) 
    {
        try (Scanner consoleScanner = new Scanner(System.in))
        { 
            System.out.print("Masukkan nama file test case (.txt): ");
            String fileName = consoleScanner.nextLine();
            TestCase testCase = Reader.readPuzzleFile(fileName);

            Board board = new Board(testCase.N, testCase.M);

            List<Block> blocks = new ArrayList<>();
            for (String shape : testCase.puzzleShapes) 
            {
                blocks.add(new Block(shape));
            }

            long startTime = System.nanoTime();
            boolean result = solve(board, blocks, 0);
            long endTime = System.nanoTime();

            if (!result) System.out.println("Tidak ada solusi yang ditemukan.");
            System.out.printf("Waktu pencarian: %.3f ms\n", (endTime - startTime) * 1E-6);
            System.out.println("Jumlah iterasi: " + iterations + " kali");

            String input;
            while (true) 
            {
                System.out.print("Apakah Anda ingin menyimpan hasil (y/n)? ");
                input = consoleScanner.nextLine().trim().toLowerCase();

                if ("y".equals(input)) 
                {
                    System.out.print("Nama file simpanan: ");
                    String saveFileName = consoleScanner.nextLine();
                    saveResult(board, startTime, endTime, saveFileName + ".txt");
                    Image.saveAsImage(board, startTime, endTime, iterations, saveFileName, result);
                    break; 
                } 
                else if ("n".equals(input)) 
                {
                    System.out.println("Program dihentikan.");
                    break; 
                } 
                else System.out.println("Input tidak sesuai!");
            }

        } 
        catch (IOException e) 
        {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }
}

