package com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int iterations = 0;
    
    private static boolean solve(Board board, List<Block> blocks, int index) {
        if (index == blocks.size()) {
            if (board.isFull()) {
                board.printBoard();
                return true; // Solusi ditemukan
            }
            return false; // Tidak ada solusi
        }

        Block block = blocks.get(index);
        List<Block> orientations = block.getOrientations();

        for (Block orient : orientations) {
            for (int i = 0; i < board.getN(); i++) {
                for (int j = 0; j < board.getM(); j++) {
                    if (board.canPlaceBlock(orient, i, j)) {
                        iterations++;
                        board.placeBlock(orient, i, j, (char) ('A' + index));
                        if (solve(board, blocks, index + 1)) {
                            return true;
                        }
                        board.removeBlock(orient, i, j); // Backtracking
                    }
                }
            }
        }

        return false; // Tidak ada solusi yang ditemukan untuk blok ini
    }
    private static void saveResult(Board board, long startTime, long endTime, String fileName) {
        Path filePath = Paths.get("test", fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            // Menulis bentuk akhir papan
            writer.write("Bentuk Akhir Papan:\n");
            board.printBoardToFile(writer);

            // Menulis waktu pencarian dan jumlah iterasi
            double elapsedTimeInMillis = (endTime - startTime) * 1E-6;
            writer.write("\nWaktu Pencarian: " + String.format("%.3f", elapsedTimeInMillis) + " ms\n");
            writer.write("Jumlah Iterasi: " + iterations + "\n");

            System.out.println("Hasil telah disimpan di " + fileName);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan hasil: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        try (Scanner consoleScanner = new Scanner(System.in)){ 
            System.out.print("Masukkan nama file test case (.txt): ");
            String fileName = consoleScanner.nextLine();
            TestCase testCase = Reader.readPuzzleFile(fileName);

            // Inisialisasi papan permainan
            Board board = new Board(testCase.N, testCase.M);

            // Membuat list blok berdasarkan input
            List<Block> blocks = new ArrayList<>();
            for (String shape : testCase.puzzleShapes) {
                blocks.add(new Block(shape));
            }

            // Memulai pencarian solusi dengan Brute Force
            long startTime = System.nanoTime();
            boolean result = solve(board, blocks, 0);
            long endTime = System.nanoTime();

            // Menampilkan hasil akhir
            if (!result) {
                System.out.println("Tidak ada solusi yang ditemukan.");
            }

            System.out.printf("Waktu pencarian: %.3f ms\n", (endTime - startTime) * 1E-6);
            System.out.println("Jumlah iterasi: " + iterations + " kali");

            String input;
            while (true) {
                System.out.print("Apakah Anda ingin menyimpan hasil (y/n)? ");
                input = consoleScanner.nextLine().trim().toLowerCase();

                if ("y".equals(input)) {
                    // Meminta nama file untuk menyimpan
                    System.out.print("Nama file simpanan: ");
                    String saveFileName = consoleScanner.nextLine();
                    saveResult(board, startTime, endTime, saveFileName + ".txt");
                    break; // Keluar dari loop
                } else if ("n".equals(input)) {
                    System.out.println("Program dihentikan.");
                    break; // Keluar dari loop
                } else {
                    System.out.println("Input tidak sesuai!");
                }
            }

        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }
}

