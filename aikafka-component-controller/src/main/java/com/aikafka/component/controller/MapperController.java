package com.aikafka.component.controller;

import com.aikafka.component.mapper.object.Teacher;
import com.aikafka.component.mapper.parse.JacksonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO〈一句话类描述〉
 * 包名称: com.aikafka.component.controller
 * 类名称: MapperController
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@RestController
public class MapperController {

    @PostMapping("/getxml")
    public ResponseEntity test(@RequestBody Teacher teacher) {
        String xml = JacksonUtil.object2Xml(teacher);
        return new ResponseEntity(xml, HttpStatus.OK);
    }
}
