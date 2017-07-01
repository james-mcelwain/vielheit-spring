package com.vielheit.core.utility;

import org.springframework.data.convert.Jsr310Converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by admin on 7/1/17.
 */
@Converter(
        autoApply = true
)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
    public LocalDateTimeConverter() {
    }

    public Date convertToDatabaseColumn(LocalDateTime date) {
        return Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(date);
    }

    public LocalDateTime convertToEntityAttribute(Date date) {
        return Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE.convert(date);
    }
}

