package de.haw_hamburg.ti.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public abstract class FileHandler {

    public static ArrayList<Object> loadObjectsFromFile(File f) {
        ObjectInputStream ois = null;
        ArrayList<Object> objects = new ArrayList<>();
        try {
            FileInputStream fin = new FileInputStream(f);
            ois = new ObjectInputStream(fin);
            objects = Cast.as(ArrayList.class, ois.readObject());
        } catch (FileNotFoundException e) {
            System.err.println("File not found...");
        } catch (ClassNotFoundException e) {
            System.err.println("Loading failed...");
        } catch (IOException e) {
            System.err.println("Read/Write error...");
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                System.err.println("Read/Write error...");
            }
        }
        return objects;
    }

}
