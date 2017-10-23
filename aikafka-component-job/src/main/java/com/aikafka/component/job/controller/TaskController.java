package com.aikafka.component.job.controller;

import com.aikafka.component.job.BaseController;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.service.impl.TaskService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("task/delete")
    public ResponseEntity deleteJob(@RequestParam("jobId") Long jobId) {
        try {
            taskService.delTaskById(jobId);
            return new ResponseEntity(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("task/switch")
    public ResponseEntity changeJobStatuc(@RequestParam("jobId") Long jobId,
                                          @RequestParam("cmd") String cmd) {
        try {
            taskService.changeStatus(jobId, cmd);
            return new ResponseEntity(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("task/info")
    public ResponseEntity changeJobStatuc(@RequestParam("jobId") Long jobId) {
        try {
            ScheduleJob job = taskService.getTaskById(jobId);
            return new ResponseEntity(job, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("task/byname")
    public ResponseEntity searchByName(@RequestParam("jobName") String jobName,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            PageInfo pageInfo = taskService.getTasks(jobName, page, pageSize);
            return new ResponseEntity(pageInfo, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("task/all")
    public ResponseEntity searchByName(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            PageInfo pageInfo = taskService.getAllTask(page, pageSize);
            return new ResponseEntity(pageInfo, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity(failed(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}