package com.aikafka.component.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.controller
 * 类名称: MapperController
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@RestController
public class MapperController {

    @GetMapping("/test")
    public ResponseEntity test() {
        System.out.println(1234567);
        return new ResponseEntity(1234, HttpStatus.OK);
    }
}
