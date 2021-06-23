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
public enum CountryCode {
    
    IRN("Iran", "98", ""),
    ABW("Aruba", "", ""),
    AFG("Afghanistan", "93", ""),
    AGO("Angola", "", ""),
    AIA("Anguilla", "", ""),
    ALA("Aland Island", "", ""),
    ALB("", "", ""),
    AND("", "", ""),
    ARE("", "", ""),
    ARG("", "", ""),
    ARM("", "", ""),
    ASM("", "", ""),
    ATA("", "", ""),
    ATF("", "", ""),
    ATG("", "", ""),
    AUS("", "", ""),
    AUT("", "", ""),
    
    ;
    
    private final String countryName;
    private final String countryNameInPersian;
    private final String code;
    
    CountryCode(String countryName, String code, String countryNameInPersian) {
        this.countryName = countryName;
        this.countryNameInPersian = countryNameInPersian;
        this.code = code;
    }
    
    public String getCountryName() {
        return this.countryName;
    }
    
    public String getCountryNameInPersian() {
        return this.countryNameInPersian;
    }
    
    public String getCountryCallingCode() {
        return this.code;
    }
    
    
}
