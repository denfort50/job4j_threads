package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class IndexSearchTest {

    @Test
    void whenSearchMiddleElementThenSuccess() {
        Integer[] array = new Integer[100000];
        int value = 55555;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int result = IndexSearch.search(array, value);
        int expected = 55555;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenSearchLastElementThenSuccess() {
        Integer[] array = new Integer[100000];
        int value = 99999;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int result = IndexSearch.search(array, value);
        int expected = 99999;
        assertThat(result).isEqualTo(expected);
    }

}