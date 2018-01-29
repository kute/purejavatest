package com.kute.guava;

import com.google.common.base.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class StringsTest {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(StringsTest.class);
    
    @Test
    public void test() {

        LOGGER.info(Joiner.on("-").skipNulls().join("a", "b"));

        LOGGER.info(Joiner.on("-").useForNull("null").join("a", null, "b"));

        String s = ",a,,b,";
        String t = "a=1, b=2";
        LOGGER.info(Arrays.toString(s.split(",")));
        LOGGER.info("{}", Splitter.on(",").splitToList(s));
        LOGGER.info("{}", Splitter.on(",").limit(2).splitToList(s));
        LOGGER.info("{}", Splitter.on(",").trimResults().omitEmptyStrings().splitToList(s));
        LOGGER.info("{}", Splitter.on(",").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(t));

        s = "jas2kj2123FFA23";
        // remove control characters
        LOGGER.info(CharMatcher.JAVA_ISO_CONTROL.removeFrom(s));
        // only the digits
        LOGGER.info(CharMatcher.DIGIT.retainFrom(s));
        LOGGER.info(CharMatcher.DIGIT.removeFrom(s));

        LOGGER.info(CharMatcher.WHITESPACE.trimAndCollapseFrom(s, ' '));

        // trim whitespace at ends, and replace/collapse whitespace into single spaces
        // star out all digits
        LOGGER.info(CharMatcher.JAVA_DIGIT.replaceFrom(s, "*"));
        LOGGER.info(CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(s));
        // eliminate all characters that aren't digits or lowercase

        byte[] bytes = s.getBytes(Charsets.UTF_8);

        LOGGER.info(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "KUTE_BAI"));
    }
}
