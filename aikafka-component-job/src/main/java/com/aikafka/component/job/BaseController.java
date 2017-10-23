package com.aikafka.component.job;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job
 * 类名称: BaseController
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/23
 * 版本： V1.0.0
 */
public class BaseController<T> {

    protected static <T> JSONObject success(T data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "true");
        jsonObject.put("code", 200);
        jsonObject.put("data", data);
        return jsonObject;
    }

    protected static <T> JSONObject failed(T data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "false");
        jsonObject.put("code", -1);
        jsonObject.put("data", data);
        return jsonObject;
    }

    protected static JSONObject success() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "true");
        jsonObject.put("code", 200);
        jsonObject.put("msg", "操作成功");
        return jsonObject;
    }

    protected static JSONObject failed() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "false");
        jsonObject.put("code", -1);
        jsonObject.put("msg", "操作失败");
        return jsonObject;
    }

    protected static JSONObject exception() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "false");
        jsonObject.put("code", -999);
        jsonObject.put("msg", "操作异常");
        return jsonObject;
    }
}
