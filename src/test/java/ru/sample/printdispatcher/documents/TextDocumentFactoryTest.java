package ru.sample.printdispatcher.documents;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextDocumentFactoryTest {

    @Test
    public void documentGenerated(){
        TextDocumentFactory textDocumentFactory = new TextDocumentFactory();
        Document document = textDocumentFactory.create();

        assertTrue(document.getPageSize().getOrdinal() >= 0 && document.getPageSize().getOrdinal() <= 2);
    }

}