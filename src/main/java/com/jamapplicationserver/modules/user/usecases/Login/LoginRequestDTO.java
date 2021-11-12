/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.Login;

import ua_parser.*;

/**
 *
 * @author amirhossein
 */
public class LoginRequestDTO {
    
    public final String mobile;
    public final String password;
    public final String FCMToken;
    public final Device device;
    public final OS os;
    public final String ip;
    
    public LoginRequestDTO(String mobile, String password, String FCMToken, Device device, String ip, OS os) {
        this.mobile = mobile;
        this.password = password;
        this.FCMToken = FCMToken;
        this.device = device;
        this.ip = ip;
        this.os = os;
    }
    
}
