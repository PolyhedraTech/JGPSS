/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ezequiel Andujar Montes
 * @param <T> Matrix Type
 */
public class Matrix<T> {

    private final T[][] m;
    private final int rows;
    private final int cols;

    @SuppressWarnings("unchecked")
    public Matrix(int rows, int cols) {
        m = (T[][]) new Object[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Returns the value at position [row][col]. If the given coordinates are
     * beyond the bounds of the matrix, it returns -1
     *
     * @param row
     * @param col
     * @return
     */
    public T get(int row, int col) {

        if (row < 0 || col < 0 || row > rows || col > cols) {
            return null;
        }
        return m[row][col];
    }

    /**
     * Sets the given value at position [row][col] and returns true.
     * If the given coordinates are beyond the bounds of the matrix, it returns false.
     * @param row
     * @param col
     * @param value
     * @return 
     */
    public boolean set(int row, int col, T value) {
        if (row < 0 || col < 0 || row > rows || col > cols) {
            return false;
        }
        m[row][col] = value;
        return true;
    }
}
