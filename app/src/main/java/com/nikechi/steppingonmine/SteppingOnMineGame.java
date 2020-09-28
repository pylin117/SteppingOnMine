package com.nikechi.steppingonmine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SteppingOnMineGame {
    private static int BOMB = -1;

    int[][] board;
    boolean isEnd;
    boolean isLose;

    private boolean[][] display;
    private int rows;
    private int columns;


    SteppingOnMineGame(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        reset();
    }

    void reset() {
        board = new int[rows][columns];
        display = new boolean[rows][columns];

        List<Integer> range = IntStream.range(0, columns * rows).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        int bombCount = 2;
        for (int i = 0; i < bombCount; i++) {
            int bomb = range.get(i);
            int bombRow = bomb / rows;
            int bombColumn = bomb % columns;
            calculateNeighborBomb(bombRow, bombColumn);
        }

        for (int i = 0; i < bombCount; i++) {
            int bomb = range.get(i);
            int bombRow = bomb / rows;
            int bombColumn = bomb % columns;
            board[bombRow][bombColumn] = BOMB;
        }

        isEnd = false;
        isLose = false;
    }

    void clickCell(int row, int column) {
        display[row][column] = true;
        if (board[row][column] == BOMB) {
            isEnd = true;
            isLose = true;
            return;
        }

        if (board[row][column] == 0) {
            displayCellIfNeighborNoBomb(row, column);
        }
        checkGameState();
    }

    String getCell(int row, int column) {
        String value = board[row][column] == -1 ? "*" : board[row][column] + "";
        return display[row][column] ? value : "";
    }

    private void calculateNeighborBomb(int bombRow, int bombColumn) {

        if (bombRow > 0 && bombRow < rows - 1) {
            if (bombColumn > 0 && bombColumn < columns - 1) {
                board[bombRow - 1][bombColumn - 1] += 1;
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow - 1][bombColumn + 1] += 1;
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow + 1][bombColumn - 1] += 1;
                board[bombRow + 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn + 1] += 1;
            } else if (bombColumn < 1) {
                board[bombRow - 1][bombColumn + 1] += 1;
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow + 1][bombColumn + 1] += 1;
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn] += 1;
            } else if (bombColumn == columns - 1) {
                board[bombRow - 1][bombColumn - 1] += 1;
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow + 1][bombColumn - 1] += 1;
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn] += 1;
            }
        } else if (bombRow < 1) {
            if (bombColumn < 1) {
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow + 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn+1] += 1;
            } else if (bombColumn == columns - 1) {
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow + 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn-1] += 1;
            } else {
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow + 1][bombColumn - 1] += 1;
                board[bombRow + 1][bombColumn] += 1;
                board[bombRow + 1][bombColumn + 1] += 1;
            }
        } else if (bombRow == rows - 1) {
            if (bombColumn < 1) {
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow -1][bombColumn+1] += 1;
            } else if (bombColumn == columns - 1) {
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow - 1][bombColumn-1] += 1;
            } else {
                board[bombRow][bombColumn - 1] += 1;
                board[bombRow][bombColumn + 1] += 1;
                board[bombRow - 1][bombColumn - 1] += 1;
                board[bombRow - 1][bombColumn] += 1;
                board[bombRow - 1][bombColumn + 1] += 1;
            }
        }
    }

    private void displayCellIfNeighborNoBomb(int row, int column) {
        if (row > 0 && row < rows - 1) {
            if (column > 0 && column < columns - 1) {
                display[row - 1][column - 1] = true;
                display[row - 1][column] = true;
                display[row - 1][column + 1] = true;
                display[row][column + 1] = true;
                display[row][column - 1] = true;
                display[row + 1][column - 1] = true;
                display[row + 1][column] = true;
                display[row + 1][column + 1] = true;
            } else if (column < 1) {
                display[row - 1][column + 1] = true;
                display[row][column + 1] = true;
                display[row + 1][column + 1] = true;
                display[row - 1][column] = true;
                display[row + 1][column] = true;
            } else if (column == columns - 1) {
                display[row - 1][column - 1] = true;
                display[row][column - 1] = true;
                display[row + 1][column - 1] = true;
                display[row - 1][column] = true;
                display[row + 1][column] = true;
            }
        } else if (row < 1) {
            if (column < 1) {
                display[row][column + 1] = true;
                display[row + 1][column] = true;
                display[row + 1][column+1] = true;
            } else if (column == columns - 1) {
                display[row][column - 1] = true;
                display[row + 1][column] = true;
                display[row + 1][column-1] = true;
            } else {
                display[row][column - 1] = true;
                display[row][column + 1] = true;
                display[row + 1][column - 1] = true;
                display[row + 1][column] = true;
                display[row + 1][column + 1] = true;
            }
        } else if (row == rows - 1) {
            if (column < 1) {
                display[row - 1][column] = true;
                display[row][column + 1] = true;
                display[row - 1][column+1] = true;
            } else if (column == columns - 1) {
                display[row][column - 1] = true;
                display[row - 1][column] = true;
                display[row -1][column-1] = true;
            } else {
                display[row][column - 1] = true;
                display[row][column + 1] = true;
                display[row - 1][column - 1] = true;
                display[row - 1][column] = true;
                display[row - 1][column + 1] = true;
            }
        }
    }

    private void checkGameState() {

        int displayCount = 0;
        for (boolean[] booleans : display) {
            for (boolean aBoolean : booleans) {
                if (aBoolean)
                    displayCount++;
            }
        }

        if ((rows * columns) - displayCount == 2) {
            isEnd = true;
            isLose = false;
        }
    }

}
