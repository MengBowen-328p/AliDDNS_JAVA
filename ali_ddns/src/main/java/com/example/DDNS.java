package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.aliyuncs.IAcsClient;
/**
 * Hello world!
 *
 */
public class DDNS 
{
    private DescribeDomainRecordsResponse describeDomainRecords(DescribeDomainRecordsRequest request, IAcsClient client)
    {
        try
        {
            return client.getAcsResponse(request);
        }
        catch (ClientException e)
        {
            throw new RuntimeException();
        }
    }

    private String getCurrentHostIP()
    {
        String jsonip = "https://jsonip.com";
        String result = "";
        BufferedReader in = null;
        try
        {
            URL url = new URL(jsonip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = in.readLine())!=null);
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(in!=null)
                in.close();
            }
            catch (Exception e2)
            {
                e2.printStackTrace();;
            }
        }
        String rexp = "(\\d{1,3}\\.){3}\\d{1,3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(result);
        String res = "";
        while(mat.find())
        {
            res = mat.group();
            break;
        }
        return res;
    }

    private UpdateDomainRecordResponse updateDomainRecord(UpdateDomainRecordRequest request,IAcsClient client)
    {
        try
        {
            return client.getAcsResponse(request);
        }
        catch (ClientException e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    private static void printLog(String functionName, Object result)
    {
        Gson gson = new Gson();
        System.out.println("-------------------------------" + functionName + "-------------------------------");
        System.out.println(gson.toJson(result));
    }
    public static void main(String[] args)
    {
        DefaultProfile profile = DefaultProfile.getProfile
        (
            "cn-beijing",
            "ACCESS_ID",
            "ACCESS_KEY"
        );
        IAcsClient client = new DefaultAcsClient(profile);
        DDNS ddns = new DDNS();
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        describeDomainRecordsRequest.setDomainName("example.com");
        describeDomainRecordsRequest.setRRKeyWord("demo");
        describeDomainRecordsRequest.setType("A");
        DescribeDomainRecordsResponse describeDomainRecordsResponse = ddns.describeDomainRecords(describeDomainRecordsRequest, client);
        printLog("describeDomainRecords",describeDomainRecordsResponse);
        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();
        if(domainRecords.size() != 0)
        {
            DescribeDomainRecordsResponse.Record record = domainRecords.get(0);
            String recordID = record.getRecordId();
            String recordsValue = record.getValue();
            String currentHostIP = ddns.getCurrentHostIP();
            System.out.println("-------------------------------当前主机公网IP为："+currentHostIP+"-------------------------------");
            if(!currentHostIP.equals(recordsValue)){
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                updateDomainRecordRequest.setRR("ddnstest");
                updateDomainRecordRequest.setRecordId(recordID);
                updateDomainRecordRequest.setValue(currentHostIP);
                updateDomainRecordRequest.setType("A");
                UpdateDomainRecordResponse updateDomainRecordResponse = ddns.updateDomainRecord(updateDomainRecordRequest, client);
                printLog("updateDomainRecord",updateDomainRecordResponse);
            }
        }

    }

}
