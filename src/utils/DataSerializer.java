package utils;

import java.io.*;

public class DataSerializer {
    public static void ObjectSerializer(String filename, Object obj) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.close();
            file.close();
            System.out.println("Object has been succesfully serialized and stored as " + filename);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object ObjectDeserializer(String filename) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            bufferedInputStream.close();
            fileInputStream.close();
            System.out.println(filename + " has been deserialized to an object succesfully!");
            return obj;
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Error: IO error");
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error: Class not found");
            e.printStackTrace();
            return null;
        }
    }
}