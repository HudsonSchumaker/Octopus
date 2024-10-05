package br.com.schumaker.force.framework.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * The TypeConverter class.
 * This class provides a map of type parsers for converting string values to their corresponding types.
 * It supports various types including primitive types, wrapper types, and date types.
 * The type parsers are stored in a static map and can be accessed using the type's class.
 *
 * @author Hudson Schumaker
 * @version 1.1.1
 */
public final class TypeConverter {
    public static final Map<Class<?>, Function<String, Object>> typeParsers = new HashMap<>();

    static {
        typeParsers.put(UUID.class, UUID::fromString);
        typeParsers.put(String.class, value -> value);

        // Integer types
        typeParsers.put(int.class, Integer::parseInt);
        typeParsers.put(short.class, Short::parseShort);
        typeParsers.put(long.class, Long::parseLong);
        typeParsers.put(Short.class, Short::parseShort);
        typeParsers.put(Integer.class, Integer::parseInt);
        typeParsers.put(Long.class, Long::parseLong);
        typeParsers.put(BigInteger.class, BigInteger::new);

        // Floating point types
        typeParsers.put(float.class, Float::parseFloat);
        typeParsers.put(double.class, Double::parseDouble);
        typeParsers.put(Float.class, Float::parseFloat);
        typeParsers.put(Double.class, Double::parseDouble);
        typeParsers.put(BigDecimal.class, BigDecimal::new);

        // Date types
        typeParsers.put(Date.class, DateParser::parseToDate);
        typeParsers.put(Instant.class, DateParser::parseToInstant);
        typeParsers.put(LocalDate.class, DateParser::parseToLocalDate);
        typeParsers.put(LocalDateTime.class, DateParser::parseToLocalDateTime);

        // Special types
        typeParsers.put(byte.class, Byte::parseByte);
        typeParsers.put(boolean.class, Boolean::parseBoolean);
        typeParsers.put(char.class, value -> value.charAt(0));
        typeParsers.put(Byte.class, Byte::parseByte);
        typeParsers.put(Boolean.class, Boolean::parseBoolean);
        typeParsers.put(Character.class, value -> value.charAt(0));
    }

    /**
     * The TypeConverter constructor.
     */
    private TypeConverter() {}
}
