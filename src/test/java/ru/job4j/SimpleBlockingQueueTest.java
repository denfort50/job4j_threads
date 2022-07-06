package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleBlockingQueueTest {

    @Test
    public void simpleBlockingQueueTesting1() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
                queue.offer(5);
                queue.offer(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(500);
                queue.poll();
                queue.poll();
                queue.poll();
                Thread.sleep(500);
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();
        consumer.join();
        producer.join();
        assertThat(queue.poll(), is(6));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 10).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
}