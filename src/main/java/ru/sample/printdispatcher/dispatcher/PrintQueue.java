package ru.sample.printdispatcher.dispatcher;

import ru.sample.printdispatcher.documents.Document;
import ru.sample.printdispatcher.printers.Printer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class PrintQueue {
    Queue<Document> wrapped = new LinkedList<Document>();
    private List<Document> printedDocuments = new ArrayList<Document>();
    private Boolean printing = false;
    private AtomicLong totalPrintingDuration = new AtomicLong();
    {
        totalPrintingDuration.set(0);
    }
    private ExecutorService printingExecutor = Executors.newCachedThreadPool();
    private Object printingLock = new Object();

    Document getDocument() throws InterruptedException {
        synchronized (printingLock){
            while (printing){
                printingLock.wait();
            }
        }
        return wrapped.size() > 0 ? wrapped.element() : null;
    }

    void putDocumentToQueue(Document document){
        wrapped.add(document);
    }

    void printDocument(final Printer printer) throws InterruptedException {
        final Document document = getDocument();
        if(document == null){
            printing = false;
            return;
        }

        //Принтер работает в отдельном потоке (для демонстрации птокобезопасности)
        printing = true;
        printingExecutor.execute(new Runnable() {
            public void run() {
                synchronized (printingLock) {
                    try {
                        document.print(printer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        totalPrintingDuration.addAndGet(document.getPrintDuration());
                        printedDocuments.add(wrapped.poll());
                        printing = false;
                        printingLock.notifyAll();
                    }
                }
            }
        });
    }

    synchronized void cancelPrinting(){
        if(!printing){
            return;
        }
        printing = false;
        notifyAll();
    }

    List<Document> getPrinted(){
        return printedDocuments;
    }

    public List<Document> getPrinted(Comparator<Document> comparator){
        Collections.sort(printedDocuments,comparator);
        return getPrinted();
    }

    Long getAveragePrintingDuration(){
        if(printedDocuments.isEmpty()){
            return 0L;
        }
        return totalPrintingDuration.get() / printedDocuments.size();
    }

    void reset(){
        cancelPrinting();
        printedDocuments = new ArrayList<Document>();
        wrapped = new LinkedList<Document>();
        totalPrintingDuration.set(0L);
    }

}
