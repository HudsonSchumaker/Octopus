package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * The DateParser class.
 * This class provides methods to parse date strings into different date types using multiple date formats.
 * It supports parsing to Instant, LocalDate, and LocalDateTime.
 * If the input string does not match any of the supported formats, an IllegalArgumentException is thrown.
 *
 * @author Hudson Schumaker
 * @version 1.0.1
 */
public final class DateParser {
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_INSTANT,
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    );

    /**
     * Parses a date string to an Instant.
     *
     * @param dateStr the date string to be parsed
     * @return the parsed Instant
     * @throws OctopusException if the date string does not match any supported format
     */
    public static Instant parseToInstant(String dateStr) {
        try {
            return Instant.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new OctopusException("Invalid date format: " + dateStr);
        }
    }

    /**
     * Parses a date string to a LocalDate.
     *
     * @param dateStr the date string to be parsed
     * @return the parsed LocalDate
     * @throws OctopusException if the date string does not match any supported format
     */
    public static LocalDate parseToLocalDate(String dateStr) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                if (formatter == DateTimeFormatter.ISO_INSTANT) {
                    return Instant.parse(dateStr).atZone(ZoneId.systemDefault()).toLocalDate();
                } else {
                    return LocalDate.parse(dateStr, formatter);
                }
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }
        throw new OctopusException("Invalid date format: " + dateStr);
    }

    /**
     * Parses a date string to a LocalDateTime.
     *
     * @param dateStr the date string to be parsed
     * @return the parsed LocalDateTime
     * @throws OctopusException if the date string does not match any supported format
     */
    public static LocalDateTime parseToLocalDateTime(String dateStr) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                if (formatter == DateTimeFormatter.ISO_INSTANT) {
                    return Instant.parse(dateStr).atZone(ZoneId.systemDefault()).toLocalDateTime();
                } else {
                    return LocalDateTime.parse(dateStr, formatter);
                }
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }
        throw new OctopusException("Invalid date format: " + dateStr);
    }
}