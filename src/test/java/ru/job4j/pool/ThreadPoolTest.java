package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThreadPoolTest {

    @Test
    public void whenStartThreadPoolWithTasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        int[] index = {0};
        for (int i = 0; i < 10; i++) {
            threadPool.work(() -> index[0]++);
        }
        threadPool.shutdown();
        assertThat(index[0], is(10));
    }
}