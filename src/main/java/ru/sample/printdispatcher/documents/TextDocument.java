package ru.sample.printdispatcher.documents;

public class TextDocument extends AbstractDocument {
    private Long printDuration;
    private PageSize pageSize;
    private String documentName;

    TextDocument(Long printDuration, PageSize pageSize, String documentName) {
        this.printDuration = printDuration;
        this.pageSize = pageSize;
        this.documentName = documentName;
    }

    public DocumentType getDocumentType() {
        return DocumentType.TEXT;
    }

    public Long getPrintDuration() {
        return printDuration;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public String getDocumentName() {
        return documentName;
    }
}
