package ru.sample.printdispatcher.documents;

import javax.annotation.Nonnull;

public enum DocumentType {
    TEXT("Текстовый документ"),
    TABLE("Электронная таблица"),
    PICTURE("Изображение")
    ;

    private String name;

    DocumentType(String name) {
        this.name = name;
    }

    @Nonnull
    public static DocumentType getByName(@Nonnull String name){
        for (DocumentType value : values()){
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
