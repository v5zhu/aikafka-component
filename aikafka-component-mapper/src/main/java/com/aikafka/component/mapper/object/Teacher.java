package com.aikafka.component.mapper.object;

import lombok.Data;

import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.mapper.object
 * 类名称: Teacher
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@Data
public class Teacher {
    private String name;

    private int age;

    private List<Student> students;
}
