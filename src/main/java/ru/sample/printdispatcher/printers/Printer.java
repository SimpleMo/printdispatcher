package ru.sample.printdispatcher.printers;

public interface Printer {
    void print(String info, Long duration) throws InterruptedException;
}
