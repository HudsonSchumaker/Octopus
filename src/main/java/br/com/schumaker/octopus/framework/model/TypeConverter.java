package br.com.schumaker.octopus.framework.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The TypeConverter class.
 * This class provides a map of type parsers for converting string values to their corresponding types.
 * It supports various types including primitive types, wrapper types, and date types.
 * The type parsers are stored in a static map and can be accessed using the type's class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class TypeConverter {
    public static final Map<Class<?>, Function<String, Object>> typeParsers = new HashMap<>();

    /*
     * Private constructor to prevent instantiation.
     */
    static {
        typeParsers.put(String.class, value -> value);
        typeParsers.put(BigInteger.class, BigInteger::new);
        typeParsers.put(BigDecimal.class, BigDecimal::new);
        typeParsers.put(Integer.class, Integer::parseInt);
        typeParsers.put(int.class, Integer::parseInt);
        typeParsers.put(float.class, Float::parseFloat);
        typeParsers.put(Float.class, Float::parseFloat);
        typeParsers.put(double.class, Double::parseDouble);
        typeParsers.put(Double.class, Double::parseDouble);
        typeParsers.put(long.class, Long::parseLong);
        typeParsers.put(Long.class, Long::parseLong);
        typeParsers.put(boolean.class, Boolean::parseBoolean);
        typeParsers.put(Boolean.class, Boolean::parseBoolean);
        typeParsers.put(short.class, Short::parseShort);
        typeParsers.put(Short.class, Short::parseShort);
        typeParsers.put(byte.class, Byte::parseByte);
        typeParsers.put(Byte.class, Byte::parseByte);
        typeParsers.put(char.class, value -> value.charAt(0));
        typeParsers.put(Character.class, value -> value.charAt(0));
        typeParsers.put(Instant.class, DateParser::parseToInstant);
        typeParsers.put(LocalDate.class, DateParser::parseToLocalDate);
        typeParsers.put(LocalDateTime.class, DateParser::parseToLocalDateTime);
    }
}
