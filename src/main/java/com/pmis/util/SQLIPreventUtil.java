package com.pmis.util;

public class SQLIPreventUtil {
    public static String fSQLStandardValue(String sValue) {
        if (sValue == null || sValue.isEmpty()) {
            return sValue;
        }
        sValue = sValue.replaceAll("'", "");
        return sValue;
    }

    public static String encodeValueToPreventSQLI(String root) {
        if (root == null) {
            return null;
        }
        return root.replaceAll("[^a-zA-Z0-9_<>=:-]", "");
    }

    public static String encodeValueToPreventSQLIFilter(String root) {
        if (root == null) {
            return null;
        }
        return root.replaceAll("[^V<>=:!%-]", "");
    }
}
