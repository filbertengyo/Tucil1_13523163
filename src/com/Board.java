package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Board 
{
    private char[][] board;
    private int N, M;

    private static final String[] COLORS = 
    {
        "\u001B[31m",
        "\u001B[32m",
        "\u001B[33m", 
        "\u001B[34m", 
        "\u001B[35m",
        "\u001B[36m", 
        "\u001B[37m", 
        "\u001B[90m", 
        "\u001B[91m", 
        "\u001B[92m", 
        "\u001B[93m", 
        "\u001B[94m", 
        "\u001B[95m", 
        "\u001B[96m", 
        "\u001B[97m", 
        "\u001B[41m", 
        "\u001B[42m", 
        "\u001B[43m", 
        "\u001B[44m", 
        "\u001B[45m", 
        "\u001B[46m", 
        "\u001B[47m", 
        "\u001B[48m", 
        "\u001B[49m", 
        "\u001B[1m",  
        "\u001B[22m" 
    };

    public Board(int N, int M) 
    {
        this.N = N;
        this.M = M;
        board = new char[N][M];
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < M; j++) 
            {
                board[i][j] = '.'; 
            }
        }
    }

    public boolean canPlaceBlock(Block block, int row, int col) 
    {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        if (row + h > N || col + w > M) return false; 

        for (int i = 0; i < h; i++) 
        {
            for (int j = 0; j < w; j++) 
            {
                if (shape[i][j] != '.' && board[row + i][col + j] != '.') 
                {
                    return false; 
                }
            }
        }
        return true;
    }

    public void placeBlock(Block block, int row, int col, char blockType) 
    {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int i = 0; i < h; i++) 
        {
            for (int j = 0; j < w; j++) 
            {
                if (shape[i][j] != '.') 
                {
                    board[row + i][col + j] = shape[i][j];
                }
            }
        }
    }

    public void removeBlock(Block block, int row, int col) 
    {
        char[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) 
            {
                if (shape[i][j] != '.') 
                {
                    board[row + i][col + j] = '.';
                }
            }
        }
    }

    public boolean isFull() 
    {
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < M; j++) 
            {
                if (board[i][j] == '.') return false;
            }
        }
        return true;
    }

    public void printBoard() 
    {
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < M; j++) 
            {
                char cell = board[i][j];
                if (cell == '.') System.out.print("\u001B[0m" + cell + " "); 
                else 
                {
                    int index = (cell - 'A') % COLORS.length; 
                    System.out.print(COLORS[index] + cell + " \u001B[0m");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public char[][] getBoard() 
    {
        return board;
    }

    public int getN() 
    {
        return N;
    }

    public int getM() 
    {
        return M;
    }

    public void printBoardToFile(BufferedWriter writer) throws IOException 
    {
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < M; j++) 
            {
                writer.write(board[i][j] + " ");
            }
            writer.newLine();
        }
        writer.newLine();
    }
}
