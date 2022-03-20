/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.io.*;
import java.net.*;
import com.google.gson.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class TransactionalSMSService {
    
    private TransactionalSMSService() {
    }
    
    public void send(int bodyId, String to, String[] args) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://console.melipayamak.com/api/send/shared/84fe9126365244368709a1422f281926");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); conn.setDoInput(true);
            
            final JsonObject json = new JsonObject();
            final JsonArray argsJsonArr = new JsonArray();
            for (String arg : args) {
                argsJsonArr.add(arg);
            }
            json.addProperty("bodyId", bodyId);
            json.addProperty("to", to);
            json.add("args", argsJsonArr);
            
            final String stringified =
                    json
                    .toString()
                    .replaceAll("\"\\[", "[").replaceAll("]\"","]")
                    .replaceAll("\\\\", "");
            
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            byte[] paramsAsByte = stringified.getBytes("utf-8");
            dos.write(paramsAsByte, 0, paramsAsByte.length);
            dos.flush(); dos.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
        }
        catch (Exception e) {
            LogService.getInstance().error(e);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String output = null;
            while(true) {
                try {
                    if ((output = br.readLine()) == null) break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sb.append(output);
            }
        }
    }
    
    public static TransactionalSMSService getInstance() {
        return TransactionalSMSServiceHolder.INSTANCE;
    }
    
    private static class TransactionalSMSServiceHolder {

        private static final TransactionalSMSService INSTANCE = new TransactionalSMSService();
    }
}
