package com.liu.javaproject.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: LiuR
 * @Date: 2020/1/16 14:37
 */
@Configuration
public class GenerateBean {

    /*@Bean
    public Cache myCache(){
        return new FifoCache(new SynchronizedCache(new PerpetualCache("new cache")));
    }*/

    @Bean
    public RestTemplate restTemplate(){
        OkHttp3ClientHttpRequestFactory okFactory = new OkHttp3ClientHttpRequestFactory();
        okFactory.setConnectTimeout(10000);
        okFactory.setReadTimeout(40000);
        return new RestTemplate(okFactory);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
       /* //单个文件最大
        factory.setMaxFileSize("20MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("200MB");*/
        return factory.createMultipartConfig();
    }
}
