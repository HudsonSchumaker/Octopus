package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.exception.ForceException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The QueryBuilder class.
 * It is responsible for building SQL queries based on method names.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class QueryBuilder {
    private static final Map<String, String> keywordMap = new HashMap<>();

    static {
        keywordMap.put("Distinct", "DISTINCT");
        keywordMap.put("And", "AND");
        keywordMap.put("Or", "OR");
        keywordMap.put("Is", "=");
        keywordMap.put("Equals", "=");
        keywordMap.put("Between", "BETWEEN");
        keywordMap.put("LessThan", "<");
        keywordMap.put("LessThanEqual", "<=");
        keywordMap.put("GreaterThan", ">");
        keywordMap.put("GreaterThanEqual", ">=");
        keywordMap.put("After", ">");
        keywordMap.put("Before", "<");
        keywordMap.put("IsNull", "IS NULL");
        keywordMap.put("NotNull", "IS NOT NULL");
        keywordMap.put("Like", "LIKE");
        keywordMap.put("NotLike", "NOT LIKE");
        keywordMap.put("StartingWith", "LIKE");
        keywordMap.put("EndingWith", "LIKE");
        keywordMap.put("Containing", "LIKE");
        keywordMap.put("OrderBy", "ORDER BY");
        keywordMap.put("Not", "<>");
        keywordMap.put("In", "IN");
        keywordMap.put("NotIn", "NOT IN");
        keywordMap.put("True", "= TRUE");
        keywordMap.put("False", "= FALSE");
        keywordMap.put("IgnoreCase", "UPPER");
    }

    /**
     * Builds a SQL query based on the method name.
     *
     * @param tableName the table name.
     * @param method    the method.
     * @param args      the method arguments.
     * @return the SQL query.
     */
    public static String buildQuery(String tableName, Method method, Object[] args) {
        var methodName = method.getName();
        if (methodName.startsWith("findBy")) {
            return buildSelectQuery(tableName, method, args);
        }
        return "";
    }

    /**
     * Builds a SELECT query based on the method name.
     *
     * @param tableName the table name.
     * @param method    the method.
     * @param args      the method arguments.
     * @return the SELECT query.
     */
    private static String buildSelectQuery(String tableName, Method method, Object[] args) {
        var query = new StringBuilder("SELECT * FROM ");
        query.append(tableName);
        query.append(" WHERE ");

        var methodName = method.getName().substring(6); // Remove "findBy"
        var conditions = methodName.split("And|Or");
        var argsIndex = 1;

        for (int i = 0; i < conditions.length; i++) {
            var condition = conditions[i];
            var keyword = getKeyword(condition);
            if (keyword.equals("UPPER")) {
                query.append(keyword).append("(")
                        .append(toSnakeCase(getFieldName(condition)))
                        .append(") = UPPER(")
                        .append(argsIndex)
                        .append("?)");
            } else {
                query.append(toSnakeCase(getFieldName(condition)))
                        .append(" ")
                        .append(keyword)
                        .append(" ")
                        .append(argsIndex)
                        .append("?");
            }

            if (i < conditions.length - 1) {
                if (methodName.contains("And")) {
                    query.append(" AND ");
                } else if (methodName.contains("Or")) {
                    query.append(" OR ");
                }
            }
            argsIndex++;
        }
        System.out.println("SQL: " + query);

        return replacePlaceholders(query.toString(), args);
    }

    /**
     * Gets the keyword based on the condition.
     *
     * @param condition the condition.
     * @return the keyword.
     */
    private static String getKeyword(String condition) {
        for (String key : keywordMap.keySet()) {
            if (condition.endsWith(key)) {
                return keywordMap.get(key);
            }
        }
        return "="; // Default to equals if no keyword is found
    }

    /**
     * Gets the field name based on the condition.
     *
     * @param condition the condition.
     * @return the field name.
     */
    private static String getFieldName(String condition) {
        for (String key : keywordMap.keySet()) {
            if (condition.endsWith(key)) {
                return condition.substring(0, condition.length() - key.length());
            }
        }
        return condition;
    }

    /**
     * Replaces the placeholders in the query with the parameters.
     *
     * @param query  the query.
     * @param params the parameters.
     * @return the query with the placeholders replaced.
     */
    public static String replacePlaceholders(String query, Object[] params) {
        Pattern pattern = Pattern.compile("(\\d+)\\?");
        Matcher matcher = pattern.matcher(query);
        StringBuilder result = new StringBuilder();
        int paramIndex = 0;

        while (matcher.find()) {
            if (paramIndex < params.length) {
                Object param = params[paramIndex];
                String replacement = param instanceof String ? "'" + param + "'" : param.toString();
                matcher.appendReplacement(result, replacement);
                paramIndex++;
            } else {
                throw new ForceException("Not enough parameters provided for the query.");
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * Converts a camelCase string to snake_case.
     *
     * @param camelCase the camelCase string.
     * @return the snake_case string.
     */
    private static String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
