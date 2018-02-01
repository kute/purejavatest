package com.kute.service;

import com.google.common.collect.Lists;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;

public class Test {

    public static void main(String[] args) {

        String path = "";

        Long lr = ServiceBusinessUtil.getByCatchException(new AbstractServiceExecutor<Long>() {
            @Override
            public Long execute() throws Exception {
                return Long.parseLong(path);
            }
        }, -1L);

        Assert.assertEquals(lr.longValue(), -1L);


        List<String > lineList = ServiceBusinessUtil.getByCatchException(new AbstractServiceExecutor<List<String>>() {
            @Override
            public List<String> execute() throws IOException {
                return readLines(new File(path));
            }
        }, Lists.newArrayList("no-content"));

        Assert.assertTrue(lineList.size() == 1);
        Assert.assertTrue("no-content".equals(lineList.get(0)));
    }
}
