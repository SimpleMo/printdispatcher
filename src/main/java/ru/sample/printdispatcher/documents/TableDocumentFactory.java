package ru.sample.printdispatcher.documents;

import java.util.Random;

public class TableDocumentFactory extends Factory {
    private Random random = new Random(-1);;

    public Document create() {
        int salt = random.nextInt(100);
        int duration = random.nextInt(5000);
        int pageSize = random.nextInt(3);
        return new TableDocument((long) duration, PageSize.getByOrdinal(pageSize), "Электронная таблица" + salt);
    }

}
