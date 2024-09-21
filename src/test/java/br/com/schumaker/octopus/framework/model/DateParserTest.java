package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The DateParserTest class.
 * This class is responsible for testing the DateParser class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class DateParserTest {

    @Test
    public void testParseToInstant() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28.259Z";
        Instant expected = Instant.parse(dateStr);

        // Act
        Instant actual = DateParser.parseToInstant(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDate_ISO() {
        // Arrange
        String dateStr = "2021-02-14";
        LocalDate expected = LocalDate.parse(dateStr);

        // Act
        LocalDate actual = DateParser.parseToLocalDate(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDate_Custom() {
        // Arrange
        String dateStr = "14/02/2021";
        LocalDate expected = LocalDate.of(2021, 2, 14);

        // Act
        LocalDate actual = DateParser.parseToLocalDate(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDateTime_ISO() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28";
        LocalDateTime expected = LocalDateTime.parse(dateStr);

        // Act
        LocalDateTime actual = DateParser.parseToLocalDateTime(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testParseToLocalDateTime_Custom() {
        // Arrange
        String dateStr = "2021-02-14T03:29:28.259Z";
        LocalDateTime expected = LocalDateTime.ofInstant(Instant.parse(dateStr), ZoneId.systemDefault());

        // Act
        LocalDateTime actual = DateParser.parseToLocalDateTime(dateStr);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidDateFormat() {
        // Arrange
        String invalidDateStr = "invalid-date";

        // Act & Assert
        assertThrows(OctopusException.class, () -> DateParser.parseToLocalDate(invalidDateStr));
    }
}
