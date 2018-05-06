package ru.sample.printdispatcher.dispatcher;

import ru.sample.printdispatcher.documents.Document;
import ru.sample.printdispatcher.printers.ConsolePrinter;
import ru.sample.printdispatcher.printers.Printer;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class PrintDispatcher {
    private PrintQueue printQueue = new PrintQueue();
    private Printer printer = new ConsolePrinter();
    private Boolean canceled = false;

    public Boolean isCanceled() {
        return canceled;
    }

    public void putDocument(Document document){
        if(!canceled){
            printQueue.putDocumentToQueue(document);
        }
    }

    public void printDocument() throws InterruptedException {
        if(!canceled){
            printQueue.printDocument(printer);
        }
    }

    public void cancelDocument(){
        printQueue.cancelPrinting();
    }

    public List<Document> cancelPrinting(){
        printQueue.cancelPrinting();
        List<Document> printed = printQueue.getPrinted();
        printQueue.reset();
        canceled = true;

        return printed;
    }

    public List<Document> getPrinted(@Nullable Comparator<Document> comparator){
        if(comparator != null){
            return printQueue.getPrinted(comparator);
        }
        return printQueue.getPrinted();
    }

    public Long getAveragePrintingDuration(){
        return printQueue.getAveragePrintingDuration();
    }

}
