package ru.sample.printdispatcher.documents;

import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTypeTest {

    @Test
    public void constantReturned(){
        DocumentType documentType = DocumentType.getByName("Текстовый документ");

        assertEquals(DocumentType.TEXT, documentType);
    }

    @Test
    public void constantNotFound(){
        DocumentType documentType = DocumentType.getByName("Левый тип документа");

        assertNull(documentType);
    }

}