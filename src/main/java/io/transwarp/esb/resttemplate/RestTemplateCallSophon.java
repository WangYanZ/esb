package io.transwarp.esb.resttemplate;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangyan
 * @description
 * @date 2019/7/31 16:29
 */

public class RestTemplateCallSophon {
    /*
    description post请求
     */
    @PostMapping
    public static String callSophonUrl(String jsonStringToSophon){
        String sophonUrl = getSophonUrl(jsonStringToSophon);

        String jsonStringFromSophon;
        RestTemplate restTemplate = new RestTemplate();
        //添加头文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //上传内容
        HttpEntity<String> request = new HttpEntity<>(jsonStringToSophon, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( sophonUrl, request , String.class );
        jsonStringFromSophon = response.getBody();
        return jsonStringFromSophon;
    }

    //从上传的json文件解析出url
    public static String getSophonUrl(String jsonStringToSophon){
        JSONObject jsonObject = JSON.parseObject(jsonStringToSophon);
        String jsonObjectRoot = jsonObject.getString("root");
        String jsonObjectRootUrl = JSON.parseObject(jsonObjectRoot).getString("a");
        String sophonUrl = jsonObjectRootUrl;
        return sophonUrl;
    }



    /*
    description post封装请求
   */
    @PostMapping
    public static void callSophonUrlTestPost(){

        String url = "";
        RestTemplate restTemplate = new RestTemplate();
        //添加头文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //上传内容
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("age","18");
        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        String body = response.getBody();
    }

    /*
    description get无参请求
     */
    @GetMapping
    public static void callSophonUrlTestGet(){
         RestTemplate restTemplate = new RestTemplate();
         String url = "http://localhost:8080/base/testGet";
         String responseEntity = restTemplate.getForObject(url, String.class);
         System.out.println(responseEntity.toString());
    }
}
