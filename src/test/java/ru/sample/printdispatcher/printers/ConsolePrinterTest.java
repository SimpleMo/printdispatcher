package ru.sample.printdispatcher.printers;

import org.junit.Test;
import ru.sample.printdispatcher.documents.Document;
import ru.sample.printdispatcher.documents.TextDocumentFactory;
import ru.sample.printdispatcher.documents.TextDocumentFactoryTest;

import static org.junit.Assert.*;

public class ConsolePrinterTest {
    private Long printDuration;

    @Test
    public void print() throws InterruptedException {
        TextDocumentFactory textDocumentFactory = new TextDocumentFactory();
        Document document = textDocumentFactory.create();
        document.print(new PrintTestable());

        assertEquals(document.getPrintDuration(), printDuration);
    }

    private class PrintTestable extends ConsolePrinter{
        @Override
        public void print(String info, Long duration) throws InterruptedException {
            printDuration = duration;
            super.print(info, duration);
        }
    }
}