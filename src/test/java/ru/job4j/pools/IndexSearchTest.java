package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

class IndexSearchTest {

    @Test
    void whenUsePoolThenSuccess() {
        Integer[] array = new Integer[10000];
        int value = 7777;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        IndexSearch<Integer> indexSearch = new IndexSearch<>(array, 0, array.length - 1, value);
        int result = forkJoinPool.invoke(indexSearch);
        int expected = 7777;
        assertThat(result).isEqualTo(expected);
    }

}