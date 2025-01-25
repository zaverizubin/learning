package com.playground.others;

import java.io.*;
import java.util.Base64;

public class SerializeDeserialize {

    public static String serializeObjectToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static Object deSerializeObjectFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static class T implements Serializable{
        private int i = 5;
    }

    public static void main(String[] args) throws IOException {
        SerializeDeserialize.serializeObjectToString(new T());

    }
}
