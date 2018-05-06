package ru.sample.printdispatcher.dispatcher;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sample.printdispatcher.documents.Factory;
import ru.sample.printdispatcher.documents.TextDocumentFactory;
import ru.sample.printdispatcher.printers.ConsolePrinter;
import ru.sample.printdispatcher.printers.Printer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.*;

public class PrintQueueTest {
    PrintQueue queue;
    Factory documentFactory = new TextDocumentFactory();

    @Before
    public void setUp(){
        queue = new PrintQueue();
        queue.putDocumentToQueue(documentFactory.create());
    }

    @Test
    public void getDocumentWhenExists() throws InterruptedException {
        assertNotNull(queue.getDocument());
    }

    @Test
    public void putDocumentToQueue() {
        int size = queue.wrapped.size();
        queue.putDocumentToQueue(documentFactory.create());
        assertEquals(size + 1, queue.wrapped.size());
    }

    @Test
    public void printDocument() throws InterruptedException {
        int size = queue.wrapped.size();
        waitWhilePrinted();

        assertEquals(size - 1, queue.wrapped.size());
    }

    private void waitWhilePrinted() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Printer printer = new ConsolePrinter();
        queue.printDocument(printer);
        queue.getDocument();
    }

    @Test
    public void getPrinted() throws InterruptedException {
        waitWhilePrinted();

        assertEquals(1, queue.getPrinted().size());
        assertEquals(queue.getAveragePrintingDuration(), queue.getPrinted().get(0).getPrintDuration());

    }
}