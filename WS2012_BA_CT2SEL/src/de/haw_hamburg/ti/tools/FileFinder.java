package de.haw_hamburg.ti.tools;

import java.io.*;
import java.util.*;

public class FileFinder {

    public List<File> find(String start, String... extensions) {
        final List<File> files = new ArrayList<File>(1024);
        final Stack<File> dirs = new Stack<File>();
        final File startdir = new File(start);
        if (startdir.isDirectory())
            dirs.push(startdir);
        while (dirs.size() > 0) {
            for (File file : dirs.pop().listFiles()) {
                if (file.isDirectory())
                    dirs.push(file);
                else if (match(file.getName(), extensions))
                    files.add(file);
            }
        }
        return files;
    }

    private static boolean match(String s, String... suffixes) {
        for (String suffix : suffixes)
            if (s.length() >= suffix.length()
                    && s.substring((s.length() - suffix.length()), s.length())
                            .equalsIgnoreCase(suffix))
                return true;
        return false;
    }
}
