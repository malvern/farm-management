package zw.co.malvern.utils.dates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FunctionalInterface
public interface FormatDateTime {
   Logger LOGGER = LoggerFactory.getLogger(FormatDateTime.class);

    LocalDateTime customFormatStringToDateTime(String dateTime);

    default LocalDateTime formatStringToLocalDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception exception) {
            LOGGER.debug("exception thrown ",exception);
            throw new DateTimeException("Failed to parse given date.Please use format(yyyy-MM-dd'T'HH:mm:ss)");
        }
    }
}
