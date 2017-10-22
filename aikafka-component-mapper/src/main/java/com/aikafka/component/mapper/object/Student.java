package com.aikafka.component.mapper.object;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 包名称: com.aikafka.component.mapper.object
 * 类名称: Student
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@Data
public class Student {

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Age")
    private int age;

    @JacksonXmlProperty(localName = "Course")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Course> courses;
}
