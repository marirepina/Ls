package test.java;


import main.java.LsLauncher;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class LsTests {

    private void assertFileContent(String file1, String file2) throws IOException {
        String fileContent1 = (String.valueOf(Files.readAllLines(Paths.get(String.valueOf(file1)))));
        String fileContent2 = (String.valueOf(Files.readAllLines(Paths.get(String.valueOf(file2)))));
        assertEquals(fileContent1, fileContent2);
    }

    @Test
    public void test1() {
     assertEquals(Arrays.asList("archive-tmp", "classes", "generated-sources",
              "LsLauncher-1.0-SNAPSHOT-jar-with-dependencies.jar", "LsLauncher-1.0-SNAPSHOT.jar", "maven-archiver",
              "maven-status"), new LsLauncher().launch("target"));
    }

    @Test
    public void testFlagO2() throws IOException {
        new LsLauncher().launch("-o", "mainTest/outputFile", "mainTest");
        assertFileContent("mainTest/inputFile","mainTest/outputFile");
    }

    @Test
    public void testFlagL() {
        assertEquals(Arrays.asList("TestDirFile 111 0 Wed Apr 07 23:49:57 MSK 2021",
                "TestF2 111 6118 Thu Apr 08 00:00:17 MSK 2021"
                ), new LsLauncher().launch("-l" ,"TestDir"));
        assertEquals(Collections.singletonList("TestF2 111 6118 Thu Apr 08 00:00:17 MSK 2021"
        ), new LsLauncher().launch("-l" ,"TestDir/TestF2"));
    }

    @Test
    public void testFlagH() {
        assertEquals(Arrays.asList("TestDirFile rwx 0 byte",
                "TestF2 rwx 5 Kb"
        ), new LsLauncher().launch("-h" ,"TestDir"));

        assertEquals(Collections.singletonList("TestF2 rwx 5 Kb"),
                new LsLauncher().launch("-h" ,"TestDir/TestF2"));
    }

    @Test
    public void testFlagR() {
        assertEquals(Arrays.asList("TestF2", "TestDirFile"
        ), new LsLauncher().launch("-r" ,"TestDir"));
    }

    @Test
    public void testError1() {
        assertEquals(Collections.singletonList("Incorrect data entry"),
                new LsLauncher().launch("-loong" ,"TestDir"));
    }

    @Test
    public void testError2() {
        assertEquals(Collections.singletonList("The directory doesn't exist"),
                new LsLauncher().launch("TestDirr"));
    }

    @Test
    public void testError3() {
        assertEquals(Collections.singletonList("The directory doesn't exist"),
                new LsLauncher().launch("-o", "132123","TestDir"));
    }

}
