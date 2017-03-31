package com.github.greengerong;

/******************************************
 *                                        *
 * Auth: green gerong                     *
 * Date: 2014.                         *
 * blog: http://greengerong.github.io/    *
 * github: https://github.com/greengerong *
 *                                        *
 ******************************************/

public class OSUtils {
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
