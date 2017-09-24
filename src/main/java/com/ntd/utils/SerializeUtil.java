package com.ntd.utils;

import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by nongtiedan on 2016/8/30.
 */
public class SerializeUtil {

    private static final Log logger = LogFactory.getLog(SerializeUtil.class);

    /**
     * 序列化
     */
    public static byte[] serialize(Object object) {

        byte[] result = null;

        if (object == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
            try  {
                if (!(object instanceof Serializable)) {
                    throw new IllegalArgumentException(SerializeUtil.class.getSimpleName() + " requires a Serializable payload " +
                            "but received an object of type [" + object.getClass().getName() + "]");
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                result =  byteStream.toByteArray();
            }
            catch (Throwable ex) {
                throw new Exception("Failed to serialize", ex);
            }
        } catch (Exception ex) {
            logger.error("Failed to serialize",ex);
        }

        return result;
    }

    /**
     * 反序列化
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {

        if (isEmpty(bytes)) {
            return null;
        }

        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
                try {
                    return (T)objectInputStream.readObject();
                }
                catch (ClassNotFoundException ex) {
                    throw new Exception("Failed to deserialize object type", ex);
                }
            }
            catch (Throwable ex) {
                throw new Exception("Failed to deserialize", ex);
            }
        } catch (Exception e) {
            logger.error("Failed to deserialize",e);
        }
        return null;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(SerializeUtil.serialize(list));
    }

}
