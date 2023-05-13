package fr.alex.io;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseZonedDateTime;

import java.util.stream.Stream;

public enum Fields {
    ID("id", new Optional(new ParseLong())),
    FIRST_NAME("firstName", new Optional()),
    LAST_NAME("lastName", new Optional()),
    EMAIL("email", new Optional()),
    GENDER("gender", new Optional()),
    IP_ADDRESS("ipAddress", new Optional()),
    LAST_LOGIN("lastLogin", new Optional(new ParseZonedDateTime())),
    ACCOUNT_BALANCE("accountBalance", new Optional(new ParseBigDecimal())),
    COUNTRY_ISO_CODE("countryIsoCode", new Optional()),
    FAVORITE_COLOR("favoriteColor", new Optional());

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
