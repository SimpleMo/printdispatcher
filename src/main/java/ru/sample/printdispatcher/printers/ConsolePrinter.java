package ru.sample.printdispatcher.printers;

public class ConsolePrinter implements Printer {

    public void print(String info, Long duration) throws InterruptedException {
        System.out.println(info);
        Thread.sleep(duration);
    }
}
