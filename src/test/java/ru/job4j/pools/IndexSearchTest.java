package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class IndexSearchTest {

    @Test
    void whenUsePoolThenSuccess() {
        Integer[] array = new Integer[10000];
        int value = 7777;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        IndexSearch<Integer> indexSearch = new IndexSearch<>(array, 0, array.length - 1, value);
        int result = indexSearch.search(array, value);
        int expected = 7777;
        assertThat(result).isEqualTo(expected);
    }

}