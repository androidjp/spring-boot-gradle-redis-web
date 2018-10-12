package com.jp.springbootgradleredisweb.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

public class FastjsonUtils {

    public static <T> String objectToJson(T obj) {
        return JSONObject.toJSONString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (StringUtils.hasText(json)) {
            JSONObject jsonObject = JSONObject.parseObject(json);
            return JSONObject.parseObject(jsonObject.toJSONString(), clazz);
        }
        return null;
    }
}
