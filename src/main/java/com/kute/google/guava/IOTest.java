package com.kute.google.guava;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * created by kute on 2018-02-08 16:12
 */
public class IOTest {

    @Test
    public void test() throws IOException {

        File file = new File("test.txt");

        // create file
        Files.touch(file);

        Files.getFileExtension(file.getName());
        Files.getNameWithoutExtension(file.getName());

        List<String > list = Files.readLines(file, Charsets.UTF_8);

    }
}
