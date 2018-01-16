package com.bilgetech.widgets.helper;

public class VersionComparator {
    // IMPORTANT: version numbers are assumed to be max. 999 (999.999.999.999)
    public static double getVersionValue(String version) {
        double number = 0;
        String[] tokens = version.split("\\.");
        double n = 1;
        for (int i = 0; i < tokens.length; i++, n /= 1000) {
            number += Integer.parseInt(tokens[i]) * n;
        }
        return number;
    }
}