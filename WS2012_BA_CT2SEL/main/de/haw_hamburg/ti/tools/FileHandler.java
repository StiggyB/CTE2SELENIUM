package de.haw_hamburg.ti.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class FileHandler {

    private static ObjectInputStream ois = null;

    private FileHandler() {
    }

    public static void setFile(File f) {
        FileInputStream fin;
        try {
            fin = new FileInputStream(f);
            ois = new ObjectInputStream(fin);
        } catch (FileNotFoundException e) {
            System.err.println("File not found...");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Read/Write error...");
            e.printStackTrace();
        }

    }

    public static Object loadObjectsFromFile() {
        Object objects = null;;
        try {
            if (!(ois == null))
                objects = ois.readObject();
            else
                return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Loading failed...");
        } catch (IOException e) {
            System.err.println("Read/Write error...");
        }
        return objects;
    }

    public static void closeFile() {
        try {
            ois.close();
        } catch (IOException e) {
            System.err.println("Read/Write error...");
        }
    }

}
