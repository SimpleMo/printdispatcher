package ru.sample.printdispatcher.documents;

import javax.annotation.Nullable;

public enum PageSize {
    A3(0), A4(1), A5(2)
    ;

    private Integer ordinal;

    PageSize(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    @Nullable
    public static PageSize getByOrdinal(Integer ordinal){
        for(PageSize value : values()){
            if(value.getOrdinal().equals(ordinal)){
                return value;
            }
        }
        return null;
    }
}
