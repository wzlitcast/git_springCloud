package com.aspire.commons.utils;

import com.aspire.commons.log.LogCommons;

import java.io.*;

/**
 * @description: object<- - ->bytes
 * @author: WuZhiLong
 * @create: 2019-07-19 16:39
 **/
public class ObjectsTranscoder {

    private ObjectsTranscoder() {
    }

    private static ObjectsTranscoder objectsTranscoder;

    public static ObjectsTranscoder getInstance() {
        if (objectsTranscoder == null) {
            objectsTranscoder = new ObjectsTranscoder();
        }
        return objectsTranscoder;
    }

    public static byte[] serialize(Object value) {
        if (value == null) {
            LogCommons.error("null value error");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            LogCommons.error("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    public static Object deserialize(byte[] in) {
        Object result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            LogCommons.error("convert byte to Object error", e);
        } catch (ClassNotFoundException e) {
            LogCommons.error("convert byte to Object error", e);
        } finally {
            close(is);
            close(bis);
        }
        return result;
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogCommons.info("Unable to close " + closeable, e);
            }
        }
    }
}
