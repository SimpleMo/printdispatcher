package ru.sample.printdispatcher.documents;

import ru.sample.printdispatcher.printers.Printer;

public interface Document {
    DocumentType getDocumentType();
    Long getPrintDuration();
    PageSize getPageSize();
    String getDocumentName();

    void print(Printer printer) throws InterruptedException;

}
