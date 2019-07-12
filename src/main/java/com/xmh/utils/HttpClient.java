package com.xmh.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 谢明辉
 * @createDate 2019-1-8
 * @description
 */
public class HttpClient<T> {

    private RestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();
    private Map<String, String> params = new HashMap<>();
    private String uri;

    public HttpClient(String uri) {
        this.uri = uri;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(20000);
        restTemplate = new RestTemplate(factory);
    }

    public HttpClient(String uri, int readTimeout, int connectionTimeout) {
        this.uri = uri;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectionTimeout);
        restTemplate = new RestTemplate(factory);
    }

    /**
     * 添加头部信息
     *
     * @param key   key
     * @param value value
     * @return com.viontech.keliu.http.HttpClient
     * @createDate 2019-1-8
     */
    public HttpClient<T> addHeader(String key, String value) {
        this.headers.add(key, value);
        return this;
    }


    /**
     * 添加url参数
     *
     * @param key   key
     * @param value value
     * @return com.viontech.keliu.http.HttpClient 返回自己
     * @createDate 2019-1-8
     */
    public HttpClient<T> addParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param data          请求体
     * @param responseClass 接收数据的类
     * @return org.springframework.http.ResponseEntity<T>
     * @createDate 2019-1-8
     */
    public T post(Object data, Class<T> responseClass) {
        try {
            HttpEntity<String> entity;
            if (data == null) {
                data = new Object();
            }
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(data);


            entity = new HttpEntity<>(body, headers);
            return restTemplate.postForObject(uri, entity, responseClass);
        } catch (Exception e) {
            System.out.println(MyUtils.getErrorInfo(e));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get请求
     *
     * @param responseClass 接收数据的类
     * @return org.springframework.http.ResponseEntity<T> 请求的结果
     * @createDate 2019-1-8
     */
    public ResponseEntity<T> get(Class<T> responseClass) {
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(getUriFormat(), HttpMethod.GET, entity, responseClass);
    }

    /**
     * 拼接url
     *
     * @return java.lang.String 拼接好参数的url
     * @createDate 2019-1-8
     */
    private String getUriFormat() {
        if (params.size() > 0) {
            int count = 0;
            StringBuilder sb = new StringBuilder(uri).append("?");
            for (String key : params.keySet()) {
                count++;
                sb.append(key).append("=").append(params.get(key));
                if (count < params.size()) {
                    sb.append("&");
                }
            }
            return sb.toString();
        }
        return this.uri;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpClient<T> jsonType() {
        this.headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return this;
    }

}
