package com.aikafka.component.controller;

import com.aikafka.component.mapper.object.Course;
import com.aikafka.component.mapper.object.Student;
import com.aikafka.component.mapper.object.Teacher;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springside.modules.mapper.BeanMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.mapper.exec
 * 类名称: Mapper
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/21
 * 版本： V1.0.0
 */
public class MapperUtils {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Teacher teacher = new Teacher();
        teacher.setName("王老师");
        teacher.setAge(50);
        Student student = new Student();
        student.setName("zhangsan");
        student.setAge(20);

        Course course = new Course();
        course.setName("数学");
        course.setScore(6);

        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();


        courses.add(course);

        student.setCourses(courses);

        students.add(student);

        teacher.setStudents(students);

        System.out.println(JSONObject.toJSONString(teacher));

        Teacher other = BeanMapper.map(teacher, Teacher.class);
        System.out.println(JSONObject.toJSONString(other));


        Teacher t = new Teacher();
//        BeanUtils.copyProperties(teacher, t);
//        System.out.println(JSONObject.toJSONString(t));

        org.apache.commons.beanutils.BeanUtils.copyProperties(t,teacher);
        System.out.println(JSONObject.toJSONString(t));

    }
}
