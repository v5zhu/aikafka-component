package com.aikafka.component.http.response;

import lombok.Data;

import java.util.Vector;

@Data
public class HttpResponse {

    public String urlString;

    public int defaultPort;

    public String file;

    public String host;

    public String path;

    public int port;

    public String protocol;

    public String query;

    public String ref;

    public String userInfo;

    public String contentEncoding;

    public String content;

    public String contentType;

    public int code;

    public String message;

    public String method;

    public int connectTimeout;

    public int readTimeout;

    public Vector<String> contentCollection;

}