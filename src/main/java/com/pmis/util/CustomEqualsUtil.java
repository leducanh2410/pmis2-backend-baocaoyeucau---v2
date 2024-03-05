package com.pmis.util;

import com.pmis.constant.DataType;

import java.sql.Timestamp;
import java.util.Date;

public class CustomEqualsUtil {
    public static boolean customEquals(Object a, Object b, String dataType) {
        if (a == null) {
            return false;
        }
        return a.toString().equals(b.toString());
    }

    public static boolean customEqualsFrontEnd(Object a, Object b, String dataType) {
        if (a == null) {
            return false;
        }
        if (dataType.equals(DataType.KDL_1)) {
            return a.toString().equals(b.toString());
        } else if (dataType.equals(DataType.KDL_3) || dataType.equals(DataType.KDL_4)) {
            try {
                return ((Timestamp) a).getTime() == (Long) b;
            } catch (ClassCastException e) {
                return ((Date) a).getTime() == (Long) b;
            }
        } else {
            return a.equals(b);
        }
    }
}
