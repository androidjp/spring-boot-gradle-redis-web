package com.jp.springbootgradleredisweb.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoBufUtils {
    public static <T> byte[] serializer(T o) {
        if(o!= null) {
            Schema schema = RuntimeSchema.getSchema(o.getClass());
            return ProtobufIOUtil.toByteArray(o, schema, LinkedBuffer.allocate(256));
        }
        return null;
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            ProtobufIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
}
