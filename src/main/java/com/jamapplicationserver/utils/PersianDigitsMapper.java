/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.utils;

/**
 *
 * @author amirhossein
 */
public class PersianDigitsMapper {
    

    private final static String[] MAP = {
        "۰",
        "۱",
        "۲",
        "۳",
        "۴",
        "۵",
        "۶",
        "۷",
        "۸",
        "۹",
    };
    
    public static String toPersian(int value) {

        final StringBuilder builder = new StringBuilder();

        while(value > 0) {
            builder.append(MAP[value % 10]);
            value = value / 10;
        }
        
        return builder.reverse().toString();
    }
    
    public static String toPersian(String value) {
        try {
            final int v = Integer.valueOf(value);
            return toPersian(v);
        } catch(NumberFormatException e) {
            return "N/A";
        }
    }
    
}
