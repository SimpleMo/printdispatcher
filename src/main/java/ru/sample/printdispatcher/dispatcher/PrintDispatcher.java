package ru.sample.printdispatcher.dispatcher;

import ru.sample.printdispatcher.documents.Document;
import ru.sample.printdispatcher.printers.ConsolePrinter;
import ru.sample.printdispatcher.printers.Printer;

import java.util.List;

public class PrintDispatcher {
    private PrintQueue printQueue = new PrintQueue();
    private Printer printer = new ConsolePrinter();

    public void putDocument(Document document){
        printQueue.putDocumentToQueue(document);
    }

    public void printDocument() throws InterruptedException {
        printQueue.printDocument(printer);
    }

    public void cancelDocument(){
        printQueue.cancelPrinting();
    }

    public List<Document> cancelPrinting(){
        printQueue.cancelPrinting();
        List<Document> printed = printQueue.getPrinted();
        printQueue.reset();

        return printed;
    }
}
