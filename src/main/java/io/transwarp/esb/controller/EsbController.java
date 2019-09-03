package io.transwarp.esb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.esb.service.DataFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;
import io.transwarp.esb.service.util.WriteExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(description = "EsbRequestController")
@RequestMapping(value = "/base")
@EnableAspectJAutoProxy
public class EsbController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private DataFormatUtil dataFormatUtil;

    @ApiOperation(value = "esbRequestUnifiedInterface",notes = "参数类型为标准xml格式")
    @RequestMapping(value = "/esb",method= RequestMethod.POST)
    public String esb(@RequestBody String xmlString){
        System.out.print(xmlString);
        return  xmlString;
    }
    @ApiOperation(value = "testValue")
    @RequestMapping(value = "/test",method= RequestMethod.POST)
    public String test(@RequestBody String xmlString){
        System.out.println(xmlString);
        String jsonString = DataFormatUtil.XmlToJson(xmlString);
        System.out.println(jsonString);
        String xmlStringNew  = DataFormatUtil.JsonToXml(jsonString);
        System.out.println(xmlStringNew);
        String descXml = DataFormatUtil.JsonToXmlReplaceBlank(jsonString);
        System.out.println(descXml);
        return jsonString;
    }

    @ApiOperation(value = "testWriteExcel")
    @GetMapping(value = "/test_write_excel",produces = "application/vnd.ms-excel")
    public String testWriteExcel(HttpServletRequest request,HttpServletResponse response){
        WriteExcelUtil.writeExcel(request,response);
        return "ok";
    }
}
