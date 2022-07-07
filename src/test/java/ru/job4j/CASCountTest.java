package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(casCount::increment);
        Thread second = new Thread(casCount::increment);
        Thread third = new Thread(casCount::increment);
        Thread fourth = new Thread(casCount::increment);
        Thread fifth = new Thread(casCount::increment);
        first.start();
        second.start();
        third.start();
        fourth.start();
        fifth.start();
        first.join();
        second.join();
        third.join();
        fourth.join();
        fifth.join();
        assertThat(casCount.get(), is(5));

    }
}