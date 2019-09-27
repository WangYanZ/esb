package io.transwarp.esb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.transwarp.esb.util.DataConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class CallSophonService {

    private static String xmlString;

    public CallSophonService() {
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    返回给esb的接口
     */
    public String toEsb(String xmlString){
        this.xmlString = xmlString;
        System.out.println("ok--------------"+xmlString);
        return "ok";
//        return toEsbBefore();
    }
    private String toEsbBefore(){
        StringBuffer xmlStringToEsb = new StringBuffer();
        //sophon
        String jsonStringFromSophon = callSophonUrl();
        logger.info("jsonStringFromSophon: {}",jsonStringFromSophon);
        String responseXmlBody = responseBody(jsonStringFromSophon);
        logger.info("responseXmlBody:{}",responseXmlBody);

        Map<String,String> responseBodystatus = getResponseBodyStatus(jsonStringFromSophon);
        //pubhead
        String responseXmlPubHeader = "";
        //xmlhead
        String responseXmlHeader = "";
        if (responseBodystatus.containsKey("success")){
            String retCode = "AAAAAAAAAA";
            responseXmlPubHeader = responsePubHeader(retCode);
            responseXmlHeader = responseXmlHeader("N",retCode,"success");
        }
        if (responseBodystatus.containsKey("error")){
            String retCode = new StringBuffer(requestPubHeader()).substring(12,18)+"0000";
            responseXmlPubHeader = responsePubHeader(retCode);
            String errorMsg = JSON.parseObject(responseBodystatus.get("error")).getString("msg");
            responseXmlHeader = responseXmlHeader("F",retCode,errorMsg);
        }

        xmlStringToEsb.append(responseXmlPubHeader);  //publicheader
        xmlStringToEsb.append("<root>"+responseXmlHeader+"<body>"+responseXmlBody+"</body></root>");
        //计算长度
        String xmlStringToEsbN = pklLength(xmlStringToEsb.toString());
        logger.info("responseAll:{}",xmlStringToEsbN);
        return xmlStringToEsbN;
    }
    /*
    请求公共报文头解析
     */
    private String requestPubHeader(){

//        StringBuffer requestXmlString = new StringBuffer(xmlString);
//        String requestPubHeader = requestXmlString.substring(0,200);

        int index = xmlString.indexOf("<");
        String requestPubHeader = xmlString.substring(0,index);
        return requestPubHeader;
    }
    private String requestRoot(){
//        String[] requestXmlString = xmlString.split("<?xml version='1.0' encoding='GBK'?>");
//        System.out.println(requestXmlString);
//        String requestRoot = requestXmlString[1];

//        StringBuffer requestXmlString = new StringBuffer(xmlString);
//        String requestRoot = requestXmlString.substring(237);
        int index = xmlString.indexOf("<root>");
        String requestRoot = xmlString.substring(index);

        return requestRoot;
    }
    /*
    请求：调用sophon
     */
    @PostMapping
    private String callSophonUrl(){
        Map<String,String> urlandBodyMap = getUrlandBody();
        String sophonUrl = urlandBodyMap.get("sophonUrl");
        String apiKey = urlandBodyMap.get("apiKey");
        String body = urlandBodyMap.get("body");

        String jsonStringFromSophon;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1ZGVjNTQzMC03YTM3LTRiNGUtODVhOC05MjI0NDJiODBmZDIiLCJleHAiOjE1NjY2MzMzNDksImlhdCI6MTU2NTc2OTM0OX0.bl2UBZ4-pzeuUD1PM4q-BGpvAuICeTjF4Kv2x8NvFlX1KZBSRdla2NxaG8g35BrcfRnuFp87LORg1c2TfJMfDQ");
        headers.add("Authorization",apiKey);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity( "http://172.26.0.41:8003/web-service/golf/api", request , String.class );
        ResponseEntity<String> response = restTemplate.postForEntity( sophonUrl, request , String.class );
        jsonStringFromSophon = response.getBody();
        return jsonStringFromSophon;
    }
    /*
    请求：xml报文头解析sophonUrl、apiKey和body
     */
    private Map<String,String> getUrlandBody(){
        String requestXmlStringRoot = requestRoot();
//        String requestJsonStringRoot = DataConversionUtil.XmlToJson(DataConversionUtil.XmlReplaceBlank(requestXmlStringRoot).trim());
        logger.info("requestXmlStringRoot:{}",requestXmlStringRoot);
        String requestJsonStringRoot = DataConversionUtil.XmlToJson(requestXmlStringRoot);
        String jsonObjectRoot = JSON.parseObject(requestJsonStringRoot).getString("root");
        String jsonObjectHead = JSON.parseObject(jsonObjectRoot).getString("head");
        String jsonObjectBody = JSON.parseObject(jsonObjectRoot).getString("body");
        String jsonObjectSophonUrl = JSON.parseObject(jsonObjectHead).getString("sophonUrl");
        String jsonObjectApiKey = JSON.parseObject(jsonObjectHead).getString("apiKey");

        String requestBody = requestBody(jsonObjectBody);
        logger.info("toSophon-requestSophonUrl:  {}",jsonObjectSophonUrl);
        logger.info("toSophon-requestApiKey:  {}",jsonObjectApiKey);
        logger.info("toSophon-requestBody:  {}",requestBody);

        Map<String,String> urlandBodyMap = new HashMap<>();
        urlandBodyMap.put("sophonUrl",jsonObjectSophonUrl);
        urlandBodyMap.put("apiKey",jsonObjectApiKey);
        urlandBodyMap.put("body",requestBody);
        return urlandBodyMap;
    }
    /*
    请求body封装
     */
    private String requestBody(String jsonObjectBody){

        JSONObject jsonObjectDetail = new JSONObject();
        List<String> columnsList = new ArrayList<>();
        List<Object> rowsList = new ArrayList<>();

        JSONObject jsonObject = JSONObject.parseObject(jsonObjectBody);
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            System.out.println(entry.getKey()+entry.getValue());
            columnsList.add(entry.getKey());
            rowsList.add(entry.getValue());
        }
        List<Object> rowsListAll = new ArrayList<>();
        rowsListAll.add(rowsList);

        jsonObjectDetail.put("columns",columnsList);
        jsonObjectDetail.put("rows",rowsListAll);

        JSONObject jsonObjectData = new JSONObject();
        jsonObjectData.put("data",jsonObjectDetail);

        JSONObject jsonObjectFeeds = new JSONObject();
        jsonObjectFeeds.put("feeds",jsonObjectData);

        return jsonObjectFeeds.toString();
    }
    /*
    返回body和status
     */
    private Map<String,String> getResponseBodyStatus(String jsonStringFromSophon){
        Map<String,String> responseBodyStatus = new HashMap<>();
        JSONObject jsonObjectResult = JSON.parseObject(jsonStringFromSophon);
        if (jsonObjectResult.containsKey("code")){
            responseBodyStatus.put("error",jsonStringFromSophon);
        }else {
             responseBodyStatus.put("success",jsonStringFromSophon);
        }
        return responseBodyStatus;
    }

    /*
    返回body封装为xml(包括正确返回和错误返回)
     */
    private String responseBody(String jsonStringFromSophon){
        String responseXmlBody = "";
        JSONObject jsonObjectResult = JSON.parseObject(jsonStringFromSophon);
        JSONObject jsonObject = new JSONObject();
        if (jsonObjectResult.containsKey("result 1")){
            JSONObject jsonObjectResult2 = jsonObjectResult.getJSONObject("result 1");
            JSONArray jsonArrayColumns = jsonObjectResult2.getJSONArray("columns");
            JSONArray jsonArrayRows = jsonObjectResult2.getJSONArray("rows");
            JSONArray jsonArrayRow = jsonArrayRows.getJSONArray(0);
            if (jsonArrayColumns.size()>0){
                for (int i=0;i<jsonArrayColumns.size();i++){
                    Object object = jsonArrayRow.getObject(i,Object.class);
                    jsonObject.put(jsonArrayColumns.getString(i),object);
                }
            }
            responseXmlBody = DataConversionUtil.JsonToXml(jsonObject.toString());
        }else {
            responseXmlBody = DataConversionUtil.JsonToXml(jsonStringFromSophon);
        }
        return  responseXmlBody;
    }

    /*
    返回长度计算
     */
    private String pklLength(String xmlStringToEsb){
        StringBuffer xmlStringToEsbBuffer = new StringBuffer(xmlStringToEsb);
        String pklLengthString = String.valueOf(xmlStringToEsbBuffer.substring(6).length());
        int pklLength = pklLengthString.length();
        StringBuffer pklLengthStringBuffer = new StringBuffer();
        for (int i=0;i<6-pklLength;i++){
            pklLengthStringBuffer.append("0");
        }
        pklLengthStringBuffer.append(pklLengthString);
        xmlStringToEsbBuffer.replace(0,6,pklLengthStringBuffer.toString());
//        logger.info("PkgLength: {}",requestPubHeaderBuffer.substring(0,6));
        return xmlStringToEsbBuffer.toString();
    }
/*
返回公共报文头
 */
    private String responsePubHeader(String RetCode){
        StringBuffer requestPubHeaderBuffer = new StringBuffer(requestPubHeader());
        String responsePubHeader;
        requestPubHeaderBuffer.replace(86,92,"ESB-18");
        requestPubHeaderBuffer.replace(120,122,"04");
        requestPubHeaderBuffer.append(RetCode);
        return requestPubHeaderBuffer.toString();
    }
    /*
    返回xml报文头
     */
    private String responseXmlHeader(String ErrorTyp,String ErrorCode,String ErrorMsg){

        StringBuffer respXmlHead = new StringBuffer();
        respXmlHead.append("<head><RspSeq>"+"000000"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"000"+"</RspSeq>");
        respXmlHead.append("<RspDate>"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"</RspDate>");
        respXmlHead.append("<RspTime>"+new SimpleDateFormat("HHmmss").format(new Date())+"</RspTime>");
        respXmlHead.append("<ErrorTyp>"+ErrorTyp+"</ErrorTyp>");
        respXmlHead.append("<ErrorCode>"+ErrorCode+"</ErrorCode>");
        respXmlHead.append("<ErrorMsg>"+ErrorMsg+"</ErrorMsg></head>");
        return respXmlHead.toString();
    }


}
