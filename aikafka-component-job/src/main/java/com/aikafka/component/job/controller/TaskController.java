package com.aikafka.component.job.controller;

import com.aikafka.component.job.BaseController;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.service.impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @PostMapping("task/add")
    public ResponseEntity addJob(@RequestBody ScheduleJob job) {
        try {
            taskService.addTask(job);
            return new ResponseEntity(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("task/edit")
    public ResponseEntity editJob(@RequestBody ScheduleJob job) {
        try {
            taskService.editTask(job);
            return new ResponseEntity(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
