package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThreadPoolTest {

    @Test
    public void whenStartThreadPoolWith30Tasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10);
        int[] index = {0};
        for (int i = 0; i < 30; i++) {
            threadPool.work(() -> index[0]++);
            Thread.sleep(1);
        }
        threadPool.shutdown();
        assertThat(index[0], is(30));
    }

    @Test
    public void whenStartThreadPoolWith1000Tasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(20);
        int[] index = {0};
        for (int i = 0; i < 1000; i++) {
            threadPool.work(() -> index[0]++);
            Thread.sleep(1);
        }
        threadPool.shutdown();
        assertThat(index[0], is(1000));
    }
}