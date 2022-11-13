package utils;

import java.io.*;

/**
 * Class that provides ease for the serializing and deserializing of objects to be stored in our database.
 */
public class DataSerializer {
    /**
     * Method that serialises the object given into the specified file path.
     * @param filename File path to be stored in
     * @param obj Object to be serialised
     */
    public static void ObjectSerializer(String filename, Object obj) {
        try {
            //System.out.println("show file path" + filename);
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.close();
            file.close();
//            System.out.println("Object has been successfully serialized and stored as " + filename);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that deserialises an object and returns it from the given file path.
     * @param filename File path to obtain the object and deserialise it
     * @return Object that is deserialised
     */
    public static Object ObjectDeserializer(String filename) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            bufferedInputStream.close();
            fileInputStream.close();
            //System.out.println(filename + " has been deserialized to an object successfully!");
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
