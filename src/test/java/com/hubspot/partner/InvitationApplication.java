package com.hubspot.partner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InvitationApplication {
    public static String httpsURL="https://candidate.hubteam.com/candidateTest/v2/partners?userKey=5678eb3a87827d7bc021ee7392f7";
    public static void main() throws IOException {
        URL url=new URL(httpsURL);
        URLConnection urlConnection=url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine=br.readLine())!=null){
            sb.append(inputLine);
        }
        JSONObject jsonObject=new JSONObject(sb.toString());
        System.out.println(jsonObject.toString());


    }

}
