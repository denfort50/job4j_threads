package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String name;

    public Wget(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            long bytesWritten = 0, startTime = 0, deltaTime;
            while (bytesRead != -1) {
                if (bytesWritten == 0) {
                    startTime = System.currentTimeMillis();
                }
                bytesRead = in.read(dataBuffer, 0, 1024);
                fileOutputStream.write(dataBuffer);
                bytesWritten += bytesRead;
                if (bytesWritten >= speed) {
                    deltaTime = System.currentTimeMillis() - startTime;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    bytesWritten = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void validate(int argsNum) {
        if (argsNum != 3) {
            throw new IllegalArgumentException("The number of arguments is incorrect." + System.lineSeparator()
                    + "First argument – the URL (http://...);" + System.lineSeparator()
                    + "second – the expected speed (in bytes);" + System.lineSeparator()
                    + "third – the file name.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String name = args[2];
        Thread wget = new Thread(new Wget(url, speed, name));
        long start = System.currentTimeMillis();
        wget.start();
        wget.join();
        System.out.printf("Total time of the execution = %s ms", System.currentTimeMillis() - start);
    }
}
