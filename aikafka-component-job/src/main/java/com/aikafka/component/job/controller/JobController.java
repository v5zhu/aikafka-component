package com.aikafka.component.job.controller;

import com.aikafka.component.job.BaseController;
import com.aikafka.component.job.entity.ScheduleJob;
import com.aikafka.component.job.service.impl.JobService;
import com.aikafka.component.job.service.impl.TaskService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class JobController extends BaseController {

    @Autowired
    private JobService jobService;
    @Autowired
    private TaskService taskService;

    @GetMapping("job/run")
    public ResponseEntity<JSONObject> runAJob(@RequestParam("jobId") Long jobId) {
        try {
            jobService.runAJobNow(jobId);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/pause")
    public ResponseEntity<JSONObject> pauseAJob(@RequestParam("jobId") Long jobId) {
        try {
            jobService.pauseJob(jobId);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/resume")
    public ResponseEntity<JSONObject> resumeAJob(@RequestParam("jobId") Long jobId) {
        try {
            jobService.resumeJob(jobId);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/stop")
    public ResponseEntity<JSONObject> stopAJob(@RequestParam("jobId") Long jobId) {
        try {
            jobService.stopJob(jobId);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/init")
    public ResponseEntity<JSONObject> changeJobStatuc(@RequestParam("jobId") Long jobId) {
        try {
            ScheduleJob job = taskService.getTaskById(jobId);
            jobService.addJob(job);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("job/updateCron")
    public ResponseEntity<JSONObject> updateCron(@RequestBody ScheduleJob job) {
        try {
            taskService.editTask(job);
            jobService.updateJobCron(job);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("job/cron/verify")
    public ResponseEntity<JSONObject> verifyCron(@RequestParam("cron") String cron) {
        try {
            jobService.verifyCronExpression(cron);
            return new ResponseEntity<>(success(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/all")
    public ResponseEntity<JSONObject> getAllJobs() {
        try {
            List<ScheduleJob> jobs = jobService.getAllJob();
            return new ResponseEntity<>(success(jobs), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("job/running")
    public ResponseEntity<JSONObject> getRunningJobs() {
        try {
            List<ScheduleJob> jobs = jobService.getRunningJob();
            return new ResponseEntity<>(success(jobs), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(failed(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(exception(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
