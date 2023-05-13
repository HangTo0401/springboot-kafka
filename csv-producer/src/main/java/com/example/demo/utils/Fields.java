package com.example.demo.utils;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseZonedDateTime;

import java.util.stream.Stream;

public enum Fields {
    ID("id", new Optional(new ParseLong())),
    PRODUCT("product", new Optional()),
    ORDER_DATE("orderDate", new Optional(new ParseZonedDateTime())),
    SALE("sale", new Optional(new ParseInt())),
    PRICE("price", new Optional(new ParseBigDecimal())),
    UNIT("unit", new Optional()),
    STORE("store", new Optional()),
    ADDRESS("address", new Optional());

    String name;
    CellProcessor cellProcessor;

    Fields(String name, CellProcessor cellProcessor) {
        this.name = name;
        this.cellProcessor = cellProcessor;
    }

    public String getName() {
        return name;
    }

    public CellProcessor getCellProcessor() {
        return cellProcessor;
    }

    public static String[] getFieldMapping() {
        return Stream.of(Fields.values())
                .map(Fields::getName)
                .toArray(String[]::new);
    }

    public static CellProcessor[] getProcessors() {
        return Stream.of(Fields.values())
                .map(Fields::getCellProcessor)
                .toArray(CellProcessor[]::new);
    }
}
