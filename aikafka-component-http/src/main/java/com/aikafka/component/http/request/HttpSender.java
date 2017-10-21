package com.aikafka.component.http.request;

import com.aikafka.component.http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class HttpSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);

    private String defaultContentEncoding;

    private int connectTimeout = 10000;

    private int readTimeout = 10000;

    /**
     * 默认构造函数，使用默认的响应内容编码和超时时间
     */
    public HttpSender() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /**
     * 构造函数，使用自定义的响应内容编码，使用默认的超时时间
     *
     * @param contentEncoding 编码字符，如：GBK
     */
    public HttpSender(String contentEncoding) {
        this.defaultContentEncoding = contentEncoding;
    }

    /**
     * 构造函数，使用能够自定义的超时时间，使用默认的响应内容编码
     *
     * @param connectTimeout 链接超时时间
     * @param readTimeout    读取内容超时时间
     */
    public HttpSender(int connectTimeout, int readTimeout) {
        this.defaultContentEncoding = Charset.defaultCharset().name();
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 构造函数，使用自定义的响应内容编码，自定义的超时时间
     *
     * @param contentEncodeing 编码字符，如：GBK
     * @param connectTimeout   链接超时时间
     * @param readTimeout      读取超时时间
     */
    public HttpSender(String contentEncodeing, int connectTimeout, int readTimeout) {
        this.defaultContentEncoding = contentEncodeing;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 发送GET请求
     *
     * @param urlString url地址
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString url地址
     * @param params    请求参数
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString url地址
     * @param params    请求参数
     * @param propertys 设置属性
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     *
     * @param urlString url地址
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString url地址
     * @param params    请求参数
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString url地址
     * @param params    请求参数
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    public HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> headers, byte[] body)
            throws IOException {
        return this.send(urlString, "POST", params, headers, body);
    }

    public HttpResponse sendPost(boolean useProxy, String proxyIp, int proxyPort, String urlString,
                                Map<String, String> params, Map<String, String> headers, byte[] body)
            throws IOException {
        return this.send(useProxy, proxyIp, proxyPort, urlString, "POST", params, headers, body);
    }

    private HttpResponse send(String urlString, String method, Map<String, String> parameters,
                             Map<String, String> headers, byte[] body) throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            //避免findbugs中级使用EntrySet遍历
            urlString += buildSearchParam(parameters);
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(this.connectTimeout);
        urlConnection.setReadTimeout(this.readTimeout);

        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                urlConnection.addRequestProperty(header.getKey(), header.getValue());
            }
        }

        //这段代码没意义？
        /*if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                param.append("&")
                        .append(key)
                        .append("=")
                        .append(entry.getKey());
            }
        }*/
        if (body != null) {
            urlConnection.getOutputStream().write(body);
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private StringBuffer buildParams(Map<String,String> params){
        StringBuffer param = new StringBuffer();
        for (Entry<String, String> entry : params.entrySet()) {
            param.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }
        return param;
    }

    private StringBuffer buildSearchParam(Map<String, String> parameters) {
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (Entry<String, String> entry : parameters.entrySet()) {
            if (i == 0) {
                buffer.append("?");
            } else {
                buffer.append("&");
            }
            buffer.append(entry.getKey()).append(entry.getValue());
            i++;
        }
        return buffer;
    }

    private HttpResponse send(boolean useProxy, String proxyIp, int proxyPort, String urlString,
                             String method, Map<String, String> parameters,
                             Map<String, String> headers, byte[] body) throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            urlString += buildSearchParam(parameters);
        }
        URL url = new URL(urlString);
        if (useProxy) {
            LOGGER.info("当前请求启用代理,代理IP:{},端口:{}", proxyIp, proxyPort);
            Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyIp, proxyPort));
            urlConnection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            LOGGER.info("当前请求未启用代理");
            urlConnection = (HttpURLConnection) url.openConnection();
        }

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(this.connectTimeout);
        urlConnection.setReadTimeout(this.readTimeout);

        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                urlConnection.addRequestProperty(header.getKey(), header.getValue());
            }
        }
        //这段代码没使用?
        /*if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
        }*/
        if (body != null) {
            urlConnection.getOutputStream().write(body);
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private HttpResponse send(String urlString, String method, Map<String, String> parameters,
                             Map<String, String> propertys) throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            //避免findbugs 中级
            urlString += buildSearchParam(parameters);
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(this.connectTimeout);
        urlConnection.setReadTimeout(this.readTimeout);

        if (propertys != null) {
            for (Entry<String, String> entry : propertys.entrySet()) {
                urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (Entry<String, String> entry : parameters.entrySet()) {
                param.append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
            // urlConnection.getOutputStream().write("".equals(param.toString()) ? param.toString().getBytes()
            // : param.toString().substring(1).getBytes());
            // 为了绕过findBugs 检查添加编码
            urlConnection.getOutputStream().write("".equals(param.toString()) ? param.toString().getBytes("UTF-8")
                    : param.toString().substring(1).getBytes("UTF-8"));
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpResponse httpResponser = new HttpResponse();
        try {
            InputStream in = urlConnection.getInputStream();
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = this.defaultContentEncoding;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ecod));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            httpResponser.urlString = urlString;

            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();

            // httpResponser.content = new String(temp.toString().getBytes());
            // 为了绕过findBugs 检查添加编码
            httpResponser.content = new String(temp.toString().getBytes("UTF-8"), "UTF-8");
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();

            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}