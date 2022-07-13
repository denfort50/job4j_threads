package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThreadPoolTest {

    @Test
    public void whenStartThreadPoolWith30Tasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10);
        int[] index = {0};
        for (int i = 0; i < 20; i++) {
            threadPool.work(() -> index[0]++);
        }
        threadPool.shutdown();
        assertThat(index[0], is(20));
    }

    @Test
    public void whenStartThreadPoolWith100Tasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(20);
        int[] index = {0};
        for (int i = 0; i < 100; i++) {
            threadPool.work(() -> index[0]++);
        }
        threadPool.shutdown();
        assertThat(index[0], is(100));
    }
}