package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = new String[] {"-", "\\", "|", "/"};
        int counter = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading: " + process[counter]);
                counter++;
                if (counter == process.length) {
                    counter = 0;
                }
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }


}
