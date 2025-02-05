package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Utils {
    public static String mapToString(Map<String, String[]> parameters){
        // Custom formatting using StringBuilder
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(Arrays.toString(entry.getValue())).append(", ");
        }

        // Remove trailing comma and space
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public static String listToString(List parameters){
        // Custom formatting using StringBuilder
        StringBuilder sb = new StringBuilder();
        for (Object entry : parameters) {
            sb.append(entry.toString()).append(", ");
        }

        // Remove trailing comma and space
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

}
