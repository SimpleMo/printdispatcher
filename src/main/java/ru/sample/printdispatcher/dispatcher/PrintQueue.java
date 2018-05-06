package ru.sample.printdispatcher.dispatcher;

import ru.sample.printdispatcher.documents.Document;
import ru.sample.printdispatcher.printers.Printer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public class PrintQueue {
    private Queue<Document> wrapped = new LinkedList<Document>();
    private List<Document> printedDocuments = new ArrayList<Document>();
    private Boolean printing = false;
    private AtomicLong totalPrintingDuration;
    {
        totalPrintingDuration.set(0);
    }

    synchronized Document getDocument() throws InterruptedException {
        while (printing){
            wait();
        }
        return wrapped.element();
    }

    public void putDocumentToQueue(Document document){
        wrapped.add(document);
    }

    public synchronized void printDocument(Printer printer){
        try {
            Document document = getDocument();
            if(document == null){
                printing = false;
                return;
            }

            printing = true;
            document.print(printer);
            totalPrintingDuration.addAndGet(document.getPrintDuration());
            printedDocuments.add(wrapped.poll());
            printing = false;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void cancelPrinting(){
        if(!printing){
            return;
        }
        printing = false;
        notifyAll();
    }

}
