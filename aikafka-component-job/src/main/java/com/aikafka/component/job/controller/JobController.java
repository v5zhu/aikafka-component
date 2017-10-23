package com.aikafka.component.job.controller;

import com.aikafka.component.job.annotation.SelfDefinedBean;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.service.impl.JobService;
import com.aikafka.component.job.spring.utils.SpringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.controller
 * 类名称: JobController
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/22
 * 版本： V1.0.0
 */
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("job/add")
    public ResponseEntity addJob(@RequestBody ScheduleJob job) {
        try {
            Map map = SpringUtils.getApplicationContext().getBeansWithAnnotation(SelfDefinedBean.class);
            System.out.println(JSONObject.toJSONString(map));
            jobService.addJob(job);
            return new ResponseEntity("success", HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity("failed", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
