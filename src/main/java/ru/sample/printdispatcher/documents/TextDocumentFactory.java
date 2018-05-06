package ru.sample.printdispatcher.documents;

import java.util.Random;

public class TextDocumentFactory extends Factory {

    private Random random = new Random(-1);;

    public Document create() {
        int salt = random.nextInt(100);
        int duration = random.nextInt(5000);
        int pageSize = random.nextInt(3);
        return new TextDocument((long) duration, PageSize.getByOrdinal(pageSize), "Текстовый документ" + salt);
    }
}
