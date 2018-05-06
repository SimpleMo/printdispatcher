package ru.sample.printdispatcher.documents;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageSizeTest {

    @Test
    public void getByOrdinal() {
        PageSize pageSize = PageSize.getByOrdinal(0);
        assertEquals(PageSize.A3, pageSize);
        pageSize = PageSize.getByOrdinal(1);
        assertEquals(PageSize.A4, pageSize);
        pageSize = PageSize.getByOrdinal(2);
        assertEquals(PageSize.A5, pageSize);
    }

    @Test
    public void wrongOrdinal(){
        PageSize pageSize = PageSize.getByOrdinal(-1);
        assertNull(pageSize);

    }
}