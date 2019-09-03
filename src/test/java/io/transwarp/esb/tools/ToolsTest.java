package io.transwarp.esb.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import io.transwarp.esb.service.DataFormatUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsTest {
    /*
     实现不足固定长度6，左补零
     */
    @Test
    public void test1(){
        int a=100;
        String s = String.valueOf(a);
        StringBuffer sBuffer = new StringBuffer();
        int sLen = s.length();
        for (int i=0;i<6-sLen;i++){
            sBuffer.append("0");
        }
        sBuffer.append(s);
        System.out.println(sBuffer.toString());
    }
    /*
    实现字符串截取
     */
    @Test
    public void test2(){
        StringBuffer sb = new StringBuffer();
        sb.append("helloworld!");
        String s1 = sb.substring(0,5);
        String s2 = sb.substring(5);
        System.out.println(s1+"-------"+s2);
        String s3 = sb.replace(0,5,"world").toString();
        System.out.println(s1+"-------"+s2+"-------"+s3);

    }
    /*
   获取根部JsonArray
    */
    @Test
    public void test3(){
        String jsonStr = "{\"result 1\":{\"columns\":[\"loan_currency\",\"loan_product_name\",\"cust_name\"],\"rows\":[[\"MNY\",\"流动资金贷款\",{\"name\":[\"vc\",\"et\"]}]]}," +
                "\"result 2\":{\"columns\":[\"loan_value_date\",\"cust_id\"],\"rows\":[[\"2019-06-25\",\"QY201600006082\"]]}}";
        String responseXmlBody = "";
        JSONObject jsonObjectResult = JSON.parseObject(jsonStr);
        JSONObject jsonObj = new JSONObject();
        for (Map.Entry<String,Object> entry:jsonObjectResult.entrySet()){
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjResult = jsonObjectResult.getJSONObject(entry.getKey());
            JSONArray jsonArrayColumns = jsonObjResult.getJSONArray("columns");
            JSONArray jsonArray = jsonObjResult.getJSONArray("rows");
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            if (jsonArrayColumns.size()>0){
                for (int i=0;i<jsonArrayColumns.size();i++){
                    Object object = jsonArray1.getObject(i,Object.class);
                    jsonObject.put(jsonArrayColumns.getString(i),object);
                }
            }
            jsonObj.put(entry.getKey(),jsonObject);
            System.out.println("json00000000000:"+jsonObj);
            responseXmlBody = DataFormatUtil.JsonToXml(jsonObj.toString());
            System.out.println(responseXmlBody);
        }
    }
    @Test
    public void test4() {
        String str = "{\n" +
                "    \"results\":[{objects: [" +
                "{\"person\":[{\"positions\":{\"text_rect\":[[78,1855],[157,1855],[157,1885],[78,1885]]},\"attributes\":{\"text\":\"刘泽华\"}}," +
                "{\"positions\":{\"text_rect\":[[248,1860],[291,1860],[291,1903],[248,1903]]},\"attributes\":{\"text\":\"7\"}}," +
                "{\"positions\":{\"text_rect\":[[363,1858],[407,1858],[407,1903],[363,1903]]},\"attributes\":{\"text\":\"3\"}}," +
                "{\"positions\":{\"text_rect\":[[487,1855],[527,1855],[527,1895],[487,1895]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[584,1852],[632,1852],[632,1900],[584,1900]]},\"attributes\":{\"text\":\"1\"}}," +
                "{\"positions\":{\"text_rect\":[[712,1855],[752,1855],[752,1895],[712,1895]]},\"attributes\":{\"text\":\"0\"}}," +
                "{\"positions\":{\"text_rect\":[[825,1844],[884,1844],[884,1903],[825,1903]]},\"attributes\":{\"text\":\"9\"}}," +
                "{\"positions\":{\"text_rect\":[[930,1852],[981,1852],[981,1903],[930,1903]]},\"attributes\":{\"text\":\"3\"}}," +
                "{\"positions\":{\"text_rect\":[[1032,1847],[1088,1847],[1088,1903],[1032,1903]]},\"attributes\":{\"text\":\"7\"}}," +
                "{\"positions\":{\"text_rect\":[[1163,1847],[1219,1847],[1219,1903],[1163,1903]]},\"attributes\":{\"text\":\"4\"}}," +
                "{\"positions\":{\"text_rect\":[[1278,1847],[1332,1847],[1332,1901],[1278,1901]]},\"attributes\":{\"text\":\"5\"}}," +
                "{\"positions\":{\"text_rect\":[[1383,1847],[1429,1847],[1429,1893],[1383,1893]]},\"attributes\":{\"text\":\"01\"}}]}," +
                "{\"person\":[{\"positions\":{\"text_rect\":[[78,1933],[156,1933],[156,1963],[78,1963]]},\"attributes\":{\"text\":\"廖昌杰\"}}," +
                "{\"positions\":{\"text_rect\":[[246,1915],[313,1915],[313,1982],[246,1982]]},\"attributes\":{\"text\":\"1\"}}," +
                "{\"positions\":{\"text_rect\":[[372,1936],[419,1936],[419,1982],[372,1982]]},\"attributes\":{\"text\":\"4\"}}," +
                "{\"positions\":{\"text_rect\":[[498,1938],[537,1938],[537,1976],[498,1976]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[592,1935],[640,1935],[640,1982],[592,1982]]},\"attributes\":{\"text\":\"1\"}}," +
                "{\"positions\":{\"text_rect\":[[713,1938],[753,1938],[753,1978],[713,1978]]},\"attributes\":{\"text\":\"0\"}}," +
                "{\"positions\":{\"text_rect\":[[823,1915],[889,1915],[889,1981],[823,1981]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[952,1936],[999,1936],[999,1982],[952,1982]]},\"attributes\":{\"text\":\"7\"}}," +
                "{\"positions\":{\"text_rect\":[[1051,1931],[1102,1931],[1102,1982],[1051,1982]]},\"attributes\":{\"text\":\"3\"}}," +
                "{\"positions\":{\"text_rect\":[[1169,1944],[1206,1944],[1206,1981],[1169,1981]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[1292,1925],[1340,1925],[1340,1973],[1292,1973]]},\"attributes\":{\"text\":\"1\"}}," +
                "{\"positions\":{\"text_rect\":[[1384,1925],[1442,1925],[1442,1982],[1384,1982]]},\"attributes\":{\"text\":\"67\"}}]}," +
                "{\"person\":[{\"positions\":{\"text_rect\":[[78,2013],[156,2013],[156,2041],[78,2041]]},\"attributes\":{\"text\":\"李金坤\"}}," +
                "{\"positions\":{\"text_rect\":[[237,2009],[290,2009],[290,2062],[237,2062]]},\"attributes\":{\"text\":\"7\"}}," +
                "{\"positions\":{\"text_rect\":[[353,1995],[420,1995],[420,2062],[353,2062]]},\"attributes\":{\"text\":\"6\"}}," +
                "{\"positions\":{\"text_rect\":[[490,2006],[546,2006],[546,2062],[490,2062]]},\"attributes\":{\"text\":\"4\"}}," +
                "{\"positions\":{\"text_rect\":[[589,2016],[635,2016],[635,2062],[589,2062]]},\"attributes\":{\"text\":\"3\"}}," +
                "{\"positions\":{\"text_rect\":[[737,2022],[771,2022],[771,2056],[737,2056]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[823,2008],[878,2008],[878,2062],[823,2062]]},\"attributes\":{\"text\":\"1\"}}," +
                "{\"positions\":{\"text_rect\":[[927,2006],[976,2006],[976,2056],[927,2056]]},\"attributes\":{\"text\":\"2\"}}," +
                "{\"positions\":{\"text_rect\":[[1046,2003],[1102,2003],[1102,2059],[1046,2059]]},\"attributes\":{\"text\":\"9\"}}," +
                "{\"positions\":{\"text_rect\":[[1158,2002],[1219,2002],[1219,2062],[1158,2062]]},\"attributes\":{\"text\":\"10\"}}," +
                "{\"positions\":{\"text_rect\":[[1282,2003],[1341,2003],[1341,2062],[1282,2062]]},\"attributes\":{\"text\":\"5\"}}," +
                "{\"positions\":{\"text_rect\":[[1379,1995],[1446,1995],[1446,2062],[1379,2062]]},\"attributes\":{\"text\":\"93\"}}]}]}]}";


        JSONArray jsonArrayPerson = new JSONArray();
        JSONObject jsonObject = JSON.parseObject(str);
        JSONArray jsonArrayObjects = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArrayObjects.size(); i++) {
            JSONArray jsonPersonArray = jsonArrayObjects.getJSONObject(i).getJSONArray("objects");
            for (int j = 0; j < jsonPersonArray.size(); j++) {
                jsonArrayPerson.add(jsonPersonArray.get(j));
            }
        }
        List<List<String>> personAllList = new ArrayList<>();
        for (int i = 0; i < jsonArrayPerson.size(); i++) {
            List<String> personList = new ArrayList<>();
            JSONArray jsonArrayPositions = jsonArrayPerson.getJSONObject(i).getJSONArray("person");
            System.out.println(jsonArrayPositions);
            for (int j = 0; j < jsonArrayPositions.size(); j++) {
                if (jsonArrayPositions.getJSONObject(j).containsKey("attributes")) {
                    String personString = jsonArrayPositions.getJSONObject(j).getJSONObject("attributes").getString("text");
                    personList.add(personString);
                }
            }
            personAllList.add(personList);
        }
        System.out.println(personAllList);

        StringBuffer resultBuffer = new StringBuffer();
        for (int i = 0; i < personAllList.size(); i++) {
            resultBuffer.append("\n");
            List<String> person = personAllList.get(i);
            for (int j = 0; j < person.size(); j++) {
                resultBuffer.append(person.get(j) + "\t");
            }

        }
    }
}
