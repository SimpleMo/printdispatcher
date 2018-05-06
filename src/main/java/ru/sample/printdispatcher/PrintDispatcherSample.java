package ru.sample.printdispatcher;

import ru.sample.printdispatcher.dispatcher.PrintDispatcher;
import ru.sample.printdispatcher.documents.*;
import ru.sample.printdispatcher.printers.ConsolePrinter;
import ru.sample.printdispatcher.printers.Printer;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintDispatcherSample {

    public static void main(String[] args) throws InterruptedException {
        final PrintDispatcher printDispatcher = new PrintDispatcher();
        final Factory textDocumentFactory = new TextDocumentFactory();
        final Factory tableDocumentFactory = new TableDocumentFactory();
        final Factory pictureDocumentFactory = new PictureDocumentFactory();
        final Printer printer = new ConsolePrinter();

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
            public void run() {
                while (!printDispatcher.isCanceled()){
                    printDispatcher.putDocument(textDocumentFactory.create());
                    Thread.yield();
                    printDispatcher.putDocument(tableDocumentFactory.create());
                    Thread.yield();
                    printDispatcher.putDocument(pictureDocumentFactory.create());
                    Thread.yield();
                }
                System.out.println("Добавление докуметов в очередь прервано");
            }
        }); // в отдельном потоке формируем документы для очереди

        Thread.sleep(500); // чтобы чего-то успело записаться

        executor.execute(new Runnable() {
            public void run() {
                while (!printDispatcher.isCanceled()){
                    try {
                        printDispatcher.printDocument();
                        Thread.yield();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println("Печать прервана");
            }
        }); // в отдельном потоке печатаем

        Thread.sleep(10000);
        List<Document> printedByName = printDispatcher.getPrinted(new Comparator<Document>() {
            public int compare(Document o1, Document o2) {
                return o1.getDocumentName().compareTo(o2.getDocumentName());
            }
        }); // для примера. Список документов, отсортированный по имени
        Long averagePrintingDuration = printDispatcher.getAveragePrintingDuration();

        printer.print("Напечатанные документы:", 0L);
        for(Document document : printDispatcher.cancelPrinting()){
            document.print(printer);
        }

        printer.print("Документы, отсортированные по имени", 0L);
        for(Document document : printedByName){
            document.print(printer);
        }

        printer.print("Среднее время обработки " + averagePrintingDuration, 0L);
        printer.print("Завершение работы",0L);
    }
}
