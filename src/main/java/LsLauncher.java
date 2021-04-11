package main.java;
/*Вариант 1 -- ls

        Вывод содержимого указанной в качестве аргумента директории в виде
        отсортированного списка имен файлов.
        ● Флаг -l (long) переключает вывод в длинный формат, в котором, кроме имени
        файла, указываются права на выполнение/чтение/запись в виде битовой маски
        XXX, время последней модификации и размер в байтах.
        ● Флаг -h (human-readable) переключает вывод в человеко-читаемый формат
        (размер в кило-, мега- или гигабайтах, права на выполнение в виде rwx).
        ● Флаг -r (reverse) меняет порядок вывода на противоположный.
        ● Флаг -o (output) указывает имя файла, в который следует вывести результат;
        если этот флаг отсутствует, результат выводится в консоль.
        В случае, если в качестве аргумента указан файл, а не директория, следует вывести
        информацию об этом файле.
        Command Line: ls [-l] [-h] [-r] [-o output.file] directory_or_file
        Кроме самой программы, следует написать автоматические тесты к ней.

 */

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class LsLauncher {

    @Option(name = "-l", aliases = "--long")
    private Boolean loong = false;

    @Option(name = "-h", aliases = "--forHuman")
    private Boolean humanReadable = false;

    @Option(name = "-r", aliases = "--reverse")
    private Boolean reverse = false;

    @Option(name = "-o", aliases = "--output", metaVar = "outputName")
    private File output;

    @Argument(required = true, metaVar = "directory/file")
    private String directoryName;



    public static void main(String[] args) {
        List<String> res = new LsLauncher().launch(args);
    }

    public List<String> launch(String... args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar ls.jar [-l] [-h] [-r] [-o output.file] directory_or_file");
            parser.printUsage(System.err);
            return Collections.singletonList("Incorrect data entry"); // TestError1
        }

        Ls ls = new Ls(loong, humanReadable, reverse, output, directoryName);
        try {
            return ls.main();
        } catch (IOException | IllegalArgumentException e) {
            return Collections.singletonList("The directory doesn't exist"); //TestError2
        }
    }
}


