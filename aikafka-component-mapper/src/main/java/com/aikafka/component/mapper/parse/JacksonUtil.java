package com.aikafka.component.mapper.parse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * XML相关的工具类
 * 项目名称:咪咕合管
 * 包名称: com.migu.tsg.pms.iodd.extenal.api.service.util
 * 类名称: JacksonMapperUtil
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/9/25
 * 版本： V1.0.0
 */
public class JacksonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private static XmlMapper xmlMapper = new XmlMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static final XmlMapper getXmlMapper() {
        return xmlMapper;
    }


    public static final String object2Xml(Object object) {
        StringWriter stringWriter = null;
        try {
            LOGGER.info("对象转xml,要转换的对象:{}", object.getClass().getName());
            stringWriter = new StringWriter();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            xmlMapper.writeValue(stringWriter, object);
            stringWriter.flush();
            LOGGER.info("对象转xml,转换成功");
            return stringWriter.toString();
        } catch (Exception e) {
            LOGGER.error("对象转xml出错:", e);
            throw new RuntimeException("对象转xml出错");
        } finally {
            closeStringWriter(stringWriter);
        }
    }

    public static final <T> T xml2Object(String xml, Class<T> clazz) {
        try {
            LOGGER.info("xml转对象{},要转换的xml:{}", clazz.getName(), xml);
            T obj = xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xml, clazz);
            LOGGER.info("xml转对象,转换成功");
            return obj;
        } catch (Exception e) {
            LOGGER.error("xml转对象出错:", e);
            throw new RuntimeException("xml转对象出错");
        }
    }

    private static final void closeStringWriter(StringWriter stringWriter) {
        if (stringWriter != null) {
            try {
                stringWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭StringWriter流失败");
            }
        }
    }

}
