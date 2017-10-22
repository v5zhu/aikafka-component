package com.aikafka.component.mapper.object;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * TODO〈一句话类描述〉
 * 包名称: com.aikafka.component.mapper.object
 * 类名称: Course
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
@Data
public class Course {

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Score")
    private int score;
}
