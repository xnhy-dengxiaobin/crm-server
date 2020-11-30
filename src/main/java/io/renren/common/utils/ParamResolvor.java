package io.renren.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工具：解析map中的参数
 */
@Slf4j
public class ParamResolvor {

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> getList(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        Object o = rs.get(field);
        if (null == o)
            return null;
        if (o instanceof List)
            return null == o ? null : (List<Map<String, Object>>) o;
        else
            throw new RuntimeException("不是一个List, 无法获取List值");
    }

    public static List<Map<String, Object>> getListAsDefault(Map<String, Object> rs, String field, List<Map<String, Object>> d) throws RuntimeException {
        List<Map<String, Object>> r = getList(rs, field);
        return r == null ? d : r;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getTList(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        Object o = rs.get(field);
        if (null == o)
            return null;
        if (o instanceof List)
            return null == o ? null : (List<T>) o;
        else
            throw new RuntimeException("不是一个List, 无法获取List值");
    }

    public static <T> List<T> getTListAsDefault(Map<String, Object> rs, String field, List<T> d) throws RuntimeException {
        List<T> r = getTList(rs, field);
        return r == null ? d : r;
    }

    /**
     * 获取一个普通的List
     *
     * @param rs
     * @param field
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getCommonList(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        Object o = rs.get(field);
        if (null == o)
            return null;
        if (o instanceof List)
            return null == o ? null : (List<T>) o;
        else
            throw new RuntimeException("不是一个List, 无法获取List值");
    }
    public static <T> List<T> getCommonListAsDefault(Map<String, Object> rs, String field, List<T> d) throws RuntimeException {
        List<T> r = getCommonList(rs, field);
        return r == null ? d : r;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        Object o = rs.get(field);
        if (null == o)
            return null;
        if (o instanceof Map)
            return null == o ? null : (Map<String, Object>) o;
        else
            throw new RuntimeException("不是一个Map, 无法获取Map值");
    }

    public static Map<String, Object> getMapAsDefault(Map<String, Object> rs, String field, Map<String, Object> d) throws RuntimeException {
        Map<String, Object> r = getMap(rs, field);
        return r == null ? d : r;
    }

    public static String getString(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return "";
        Object o = rs.get(field);
        if (null == o)
            return "";
        return o.toString();
    }

    public static String getStringAsDefault(Map<String, Object> rs, String field, String d) throws RuntimeException {
        String r = getString(rs, field);
        return r == null ? d : r;
    }

    public static Integer getInt(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        try {
            return rs.get(field) == null ? null : Integer.parseInt(rs.get(field).toString());
        } catch (NumberFormatException e) {
            log.error("", e);
            return null;
        }
    }

    public static int getIntAsDefault(Map<String, Object> rs, String field, int d) throws RuntimeException {
        Integer r = getInt(rs, field);
        return r == null ? d : r;
    }

    public static Long getLong(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        try {
            return rs.get(field) == null ? null : Long.parseLong(rs.get(field).toString());
        } catch (NumberFormatException e) {
            log.error("", e);
            return null;
        }
    }

    public static long getLongAsDefault(Map<String, Object> rs, String field, long d) throws RuntimeException {
        Long r = getLong(rs, field);
        return r == null ? d : r;
    }

    public static Float getFloat(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        try {
            return rs.get(field) == null ? null : Float.parseFloat(rs.get(field).toString());
        } catch (NumberFormatException e) {
            log.error("", e);
            return null;
        }
    }

    public static float getFloatAsDefault(Map<String, Object> rs, String field, float d) throws RuntimeException {
        Float r = getFloat(rs, field);
        return r == null ? d : r;
    }

    public static Double getDouble(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return null;
        try {
            return rs.get(field) == null ? null : Double.parseDouble(rs.get(field).toString());
        } catch (NumberFormatException e) {
            log.error("", e);
            return null;
        }
    }

    public static Double getFloatAsDefault(Map<String, Object> rs, String field, double d) throws RuntimeException {
        Double r = getDouble(rs, field);
        return r == null ? d : r;
    }

    public static boolean getBool(Map<String, Object> rs, String field) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return false;
        boolean result = false;
        try {
            result = rs.get(field) == null ? false : (Boolean) rs.get(field);
        } catch (NumberFormatException e) {
            log.error("", e);
            result = false;
        }
        return result;
    }

    public static String getDate(Map<String, Object> rs, String field) throws RuntimeException {
        String formatter = "yyyy-MM-dd HH:mm:ss";
        return getDate(rs, field, formatter);
    }

    public static String getDate(Map<String, Object> rs, String field, String formatter) throws RuntimeException {
        if (null == rs || rs.isEmpty())
            return "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = rs.get(field) == null ? "" : rs.get(field).toString();
            if ("".equals(dateStr))
                return "";
            dateStr = dateStr.length() > 19 ? dateStr.substring(0, 19) : dateStr;
            Date d = sdf.parse(dateStr);
            sdf = new SimpleDateFormat(formatter);
            return sdf.format(d);
        } catch (Exception e) {
            log.error("", e);
        }
        return "";
    }
}