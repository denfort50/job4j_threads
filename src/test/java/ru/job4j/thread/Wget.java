package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("downloadedFile")) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead = 0;
            long startTime, endTime, actualTime, lag = 0;
            while (bytesRead != -1) {
                startTime = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, speed);
                endTime = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                actualTime = endTime - startTime;
                if (actualTime < 1000) {
                    lag = 1000 - actualTime;
                    Thread.sleep(lag);
                }
                System.out.printf("bytesRead = %s; actualTime = %s; lag = %s%n", bytesRead, actualTime, lag);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
