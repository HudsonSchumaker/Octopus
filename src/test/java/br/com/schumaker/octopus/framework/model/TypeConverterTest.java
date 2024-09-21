package br.com.schumaker.octopus.framework.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The TypeConverterTest class.
 * This class is responsible for testing the TypeConverter class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class TypeConverterTest {

    @Test
    public void testStringConversion() {
        // Arrange
        String value = "test";

        // Act & Assert
        assertEquals(value, TypeConverter.typeParsers.get(String.class).apply(value));
    }

    @Test
    public void testBigIntegerConversion() {
        // Arrange
        String value = "12345678901234567890";
        BigInteger expected = new BigInteger(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(BigInteger.class).apply(value));
    }

    @Test
    public void testBigDecimalConversion() {
        // Arrange
        String value = "12345.6789";
        BigDecimal expected = new BigDecimal(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(BigDecimal.class).apply(value));
    }

    @Test
    public void testIntegerConversion() {
        // Arrange
        String value = "123";
        Integer expected = Integer.parseInt(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Integer.class).apply(value));
    }

    @Test
    public void testFloatConversion() {
        // Arrange
        String value = "123.45";
        Float expected = Float.parseFloat(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Float.class).apply(value));
    }

    @Test
    public void testDoubleConversion() {
        // Arrange
        String value = "123.456";
        Double expected = Double.parseDouble(value);
        assertEquals(expected, TypeConverter.typeParsers.get(Double.class).apply(value));
    }

    @Test
    public void testLongConversion() {
        // Arrange
        String value = "1234567890";
        Long expected = Long.parseLong(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Long.class).apply(value));
    }

    @Test
    public void testBooleanConversion() {
        // Arrange
        String value = "true";
        Boolean expected = true;

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Boolean.class).apply(value));
    }

    @Test
    public void testShortConversion() {
        // Arrange
        String value = "12345";
        Short expected = Short.parseShort(value);
        assertEquals(expected, TypeConverter.typeParsers.get(Short.class).apply(value));
    }

    @Test
    public void testByteConversion() {
        // Arrange
        String value = "123";
        Byte expected = Byte.parseByte(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Byte.class).apply(value));
    }

    @Test
    public void testCharacterConversion() {
        // Arrange
        String value = "a";
        Character expected = value.charAt(0);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Character.class).apply(value));
    }

    @Test
    public void testInstantConversion() {
        // Arrange
        String value = "2021-02-14T03:29:28.259Z";
        Instant expected = Instant.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(Instant.class).apply(value));
    }

    @Test
    public void testLocalDateConversion() {
        // Arrange
        String value = "2021-02-14";
        LocalDate expected = LocalDate.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(LocalDate.class).apply(value));
    }

    @Test
    public void testLocalDateTimeConversion() {
        // Arrange
        String value = "2021-02-14T03:29:28";
        LocalDateTime expected = LocalDateTime.parse(value);

        // Act & Assert
        assertEquals(expected, TypeConverter.typeParsers.get(LocalDateTime.class).apply(value));
    }
}