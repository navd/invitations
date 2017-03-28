package com.hubspot.partner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by navdeep.a.sharma on 27/03/2017.
 */
public class Invitation {
    private static final String HTTP_URL = "https://candidate.hubteam.com/candidateTest/v2/partners?userKey=5678eb3a87827d7bc021ee7392f7";
    private static final String POST_URL="https://candidate.hubteam.com/candidateTest/v2/results?userKey=5678eb3a87827d7bc021ee7392f7";
    public static void main(String args[]) throws IOException {
        URL url=new URL(HTTP_URL);
        URLConnection urlConnection=url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine=br.readLine())!=null){
            sb.append(inputLine);
        }
        List<Partner> partnerList=new ArrayList<Partner>();
        JSONObject jsonObject=new JSONObject(sb.toString());
        JSONArray jsonArray=jsonObject.getJSONArray("partners");
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObj=jsonArray.getJSONObject(i);
            partnerList.add(i,new Partner(jsonObj.getString("firstName"),
                    jsonObj.getString("lastName"),jsonObj.getString("email"),
                    jsonObj.getString("country"),jsonObj.getJSONArray("availableDates").toString()));
        }
        Map<String, List<Partner>> countryMap= partnerList.stream().collect(Collectors.groupingBy(Partner::getCountry));
            Map<String, HashMap<Date, List<Partner>>> dateCountryPersonMap = countryMap.
                    entrySet().stream().
                    collect(Collectors.toMap(e -> e.getKey(),
                            e -> getSpecialDate(e.getValue())));
            List<Countries> countryList=new ArrayList<>();
            //dateCountryPersonMap.forEach((k,v)->{System.out.println(k.toString());});

            dateCountryPersonMap.forEach((String k, HashMap<Date, List<Partner>> v) ->{
                Countries country=new Countries();
                country.setName(k);
                v.forEach((key,val)->{
                    country.setStartDate(key);
                    List<String> emails = new ArrayList<String>();
                    val.forEach(partner -> emails.add(partner.getEmail()));
                    country.setAttendees(emails);
                    country.setAttendeeCount(emails.size());
                });
                countryList.add(country);
            });
            //countryList.forEach(k -> {System.out.println(k.getName()+" "+k.getAttendeeCount());});
            JSONObject jsonOutput=getJSON(countryList);
            System.out.println(jsonOutput);
        try{
            HttpClient httpClient= HttpClientBuilder.create().build();
            HttpPost request=new HttpPost(POST_URL);
            StringEntity params=new StringEntity(String.valueOf(jsonOutput));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response=httpClient.execute(request);
            System.out.println(response.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }



        /*HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        conn.setUseCaches(false);
        try {
            DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(URLEncoder.encode(jsonOutput.toString(),"UTF-8"));
        }catch (Exception ex){
            ex.printStackTrace();
        }*/



    }
    public static JSONObject getJSON(List<Countries> countryList){
        JSONObject jObject = new JSONObject();
        try
        {
            JSONArray jArray = new JSONArray();
            for (Countries country : countryList)
            {
                JSONObject countryJSON = new JSONObject();
                countryJSON.put("attendeeCount", country.getAttendeeCount());
                countryJSON.put("attendees", country.getAttendees().toArray());
                countryJSON.put("startDate", (new SimpleDateFormat("yyyy-MM-dd").format(country.getStartDate())).toString());
                countryJSON.put("name", country.getName());
                jArray.put(countryJSON);
            }
            jObject.put("countries", jArray);
        } catch (JSONException jse) {
            jse.printStackTrace();
        }
        return jObject;

    }
    public static HashMap<Date,List<Partner>> getSpecialDate(List<Partner> partnerList){
        List<Date> dateList = new ArrayList<>();
        partnerList.forEach(k-> {
            dateList.addAll(k.getAvailableDates());
        });
        Map<Date, Integer> map = new HashMap<>();
        for (Date d : dateList) {
            Integer val = map.get(d);
            map.put(d, val == null ? 1 : val + 1);
        }
        Map.Entry<Date, Integer> max = null;

        for (Map.Entry<Date, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        Map<Date,List<Partner>> output= new HashMap();
        output.put(max.getKey(),partnerList);

        return (HashMap<Date, List<Partner>>) output;
    }

}

