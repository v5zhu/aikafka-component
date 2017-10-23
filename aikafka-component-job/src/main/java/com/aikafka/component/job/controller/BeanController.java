package com.aikafka.component.job.controller;

import com.aikafka.component.job.BaseController;
import com.aikafka.component.job.service.impl.BeanService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.controller
 * 类名称: BeanController
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
@RestController
public class BeanController extends BaseController {

    @Autowired
    private BeanService beanService;

    @GetMapping("bean/names")
    public ResponseEntity<JSONObject> listBeanNames() {
        try {
            List<String> names = beanService.listBeanNames();
            return new ResponseEntity<>(success(names), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("bean/fullname")
    public ResponseEntity<JSONObject> getClassFullName(@RequestParam("beanName") String beanName) {
        try {
            String fullname = beanService.getClassFullName(beanName);
            return new ResponseEntity<>(success(fullname), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("bean/class/methods")
    public ResponseEntity<JSONObject> listClassMethods(@RequestParam("beanName") String beanName) {
        try {
            List<String> names = beanService.methods(beanName);
            return new ResponseEntity<>(success(names), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
