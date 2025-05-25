package com.ssafy.enjoytrip.util;

public class RedisKeyUtil {

    public static String buildAttractionPolygonKey(Integer attractionId) {
        return "polygon:" + attractionId;
    }

    public static String buildSubAttractionPolygonKey(Integer subAttractionId) {
        return "sub_polygon:" + subAttractionId;
    }

    public static String buildGptGuideKey(int id) {
        return "gpt:guide:" + id;
    }
}