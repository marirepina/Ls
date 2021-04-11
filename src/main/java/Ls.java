package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.jetbrains.annotations.NotNull;
import static java.lang.Math.pow;

public class Ls {
    private final boolean loong, forHuman, reverse;
    private final File inputFile;
    private final File outputFile;

    public Ls(boolean loong, boolean forHuman, boolean reverse, File output, String directoryName) {
        this.loong = loong;
        this.forHuman = forHuman;
        this.reverse = reverse;
        this.outputFile = output;
        this.inputFile = new File(directoryName);
    }

    public List<String> main() throws IOException {
        @NotNull
        List<String> outputLs = new ArrayList<>();
        File[] directory =  inputFile.isDirectory() ? inputFile.listFiles() : new File[]{inputFile};
        if (reverse) {
            Arrays.sort(directory, Collections.reverseOrder());
        } else {
            Arrays.sort(directory);
        }
        for (File file : directory) {
            outputLs.add(outputFile(file));
        }
        if (outputFile != null){
            if (outputFile.exists()) {
                FileWriter writer = new FileWriter(outputFile);
                for (String str : outputLs) {
                    writer.write(str + "\n");
                }
                writer.close();
                return Collections.emptyList();
            } else return Collections.singletonList("The directory doesn't exist");
        } else {
            return outputLs;
        }
    }

    @NotNull
    private String getRoots(File file) {
        boolean r = file.canRead();
        boolean w = file.canWrite();
        boolean x = file.canExecute();
        if (loong) {
            return " " + (r ? "1" : "0") + (w ? "1" : "0") + (x ? "1" : "0");
        } else if (forHuman) {
            return " " + (r ? "r" : "") + (w ? "w" : "") + (x ? "x" : "");
        }
        return "";
    }

    @NotNull
    private String lastChange(File file) {
        if (loong) {
            return " " + new Date(file.lastModified());
        }
        return "";
    }

    enum Units {
        BYTE("byte",1),
        KILOBYTE("Kb",  1024),
        MEGABYTE("Mb", (long)pow(1024.0, 2.0)),
        GIGABYTE("Gb", (long)pow(1024.0, 3.0));

        private final String unit;
        private final long ratio;

        Units(String unit, long ratio) {
            this.unit = unit;
            this.ratio = ratio;
        }
    }

    @NotNull
    private String conventSize(@NotNull File file) {
        long size = file.isDirectory() ? 0 : file.length();
        if (loong) return " " + size;
        if (forHuman) {
            if (size >= Units.GIGABYTE.ratio) {
                return " " + size / Units.GIGABYTE.ratio + " " + Units.GIGABYTE.unit;  //gb
            }
            if (size >= Units.MEGABYTE.ratio) {
                return " " + size / Units.MEGABYTE.ratio + " " + Units.MEGABYTE.unit;  //mb
            }
            if (size >= Units.KILOBYTE.ratio) {
                return " " + size / Units.KILOBYTE.ratio + " " + Units.KILOBYTE.unit;  //kb
            }
            return " " + size + " " + Units.BYTE.unit;
        }
        return "";
    }

    @NotNull
    private String outputFile(@NotNull File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException();
        }
        else return (file.getName() + this.getRoots(file)
                + this.conventSize(file) + this.lastChange(file));
    }
}
