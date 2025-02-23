package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Board {
    private char[][] board;
    private int N, M;

    private static final String[] COLORS = {
        "\u001B[31m", // Red (A)
        "\u001B[32m", // Green (B)
        "\u001B[33m", // Yellow (C)
        "\u001B[34m", // Blue (D)
        "\u001B[35m", // Magenta (E)
        "\u001B[36m", // Cyan (F)
        "\u001B[37m", // White (G)
        "\u001B[90m", // Gray (H)
        "\u001B[91m", // Light Red (I)
        "\u001B[92m", // Light Green (J)
        "\u001B[93m", // Light Yellow (K)
        "\u001B[94m", // Light Blue (L)
        "\u001B[95m", // Light Magenta (M)
        "\u001B[96m", // Light Cyan (N)
        "\u001B[97m", // Light White (O)
        "\u001B[41m", // Red background (P)
        "\u001B[42m", // Green background (Q)
        "\u001B[43m", // Yellow background (R)
        "\u001B[44m", // Blue background (S)
        "\u001B[45m", // Magenta background (T)
        "\u001B[46m", // Cyan background (U)
        "\u001B[47m", // White background (V)
        "\u001B[48m", // Light Gray background (W)
        "\u001B[49m", // Default background (X)
        "\u001B[1m",  // Bold text (Y)
        "\u001B[22m"  // Normal text (Z)
    };

    public Board(int N, int M) {
        this.N = N;
        this.M = M;
        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = '.'; 
            }
        }
    }

    public boolean canPlaceBlock(Block block, int row, int col) {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        if (row + h > N || col + w > M) return false; // Cek batas papan

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (shape[i][j] != '.' && board[row + i][col + j] != '.') {
                    return false; // Terdapat tumpang tindih
                }
            }
        }
        return true;
    }

    public void placeBlock(Block block, int row, int col, char blockType) {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (shape[i][j] != '.') {
                    board[row + i][col + j] = blockType;
                }
            }
        }
    }

    public void removeBlock(Block block, int row, int col) {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (shape[i][j] != '.') {
                    board[row + i][col + j] = '.';
                }
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == '.') return false;
            }
        }
        return true;
    }

    // Fungsi untuk mencetak papan dengan warna sesuai hurufnya
    public void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char cell = board[i][j];
                if (cell == '.') {
                    System.out.print("\u001B[0m" + cell + " "); // Default color for empty cells
                } else {
                    int index = (cell - 'A') % COLORS.length;  // Menggunakan modulus jika lebih dari 26 huruf
                    // Menampilkan warna berdasarkan karakter
                    System.out.print(COLORS[index] + cell + " \u001B[0m"); // Reset warna setelah setiap karakter
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }
    
    public void printBoardToFile(BufferedWriter writer) throws IOException {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                writer.write(board[i][j] + " ");
            }
            writer.newLine();
        }
        writer.newLine();
    }
}
