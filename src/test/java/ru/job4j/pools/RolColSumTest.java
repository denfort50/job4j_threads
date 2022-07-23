package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenSumMatrixThenSuccess() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] expected = new RolColSum.Sums[matrix.length];
        expected[0] = new RolColSum.Sums();
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1] = new RolColSum.Sums();
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2] = new RolColSum.Sums();
        expected[2].setRowSum(24);
        expected[2].setColSum(18);
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenAsyncSumMatrixThenSuccess() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] expected = new RolColSum.Sums[matrix.length];
        expected[0] = new RolColSum.Sums();
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1] = new RolColSum.Sums();
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2] = new RolColSum.Sums();
        expected[2].setRowSum(24);
        expected[2].setColSum(18);
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(result).isEqualTo(expected);
    }
}