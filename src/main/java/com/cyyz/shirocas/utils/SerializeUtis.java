package com.cyyz.shirocas.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.codec.Base64;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public class SerializeUtis extends SerializationUtils {
    public static String serializeToString(Serializable obj){
        try {
            byte[] value = serialize(obj);
            return Base64.encodeToString(value);
        }catch (Exception e){
            throw new RuntimeException("serialize session erroe",e);
        }
    }

    public static <T> T deserializeFromString(String base64){

        try{
            byte[] objectData = Base64.decode(base64);
            return deserialize(objectData);
        }catch(Exception e){
            throw new RuntimeException("deserialize session erroe",e);
        }
    }

    public static <T>Collection<T> deserializeFromStringController(Collection<String> base64s){
        List<T> list = null;
        try {
            list = Lists.newLinkedList();
            for(String basr64: base64s){
                byte[] objectData = Base64.decode(basr64);
                T t = deserialize(objectData);
                list.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException("desrialize session error",e);
        }
        return list;
    }



}
