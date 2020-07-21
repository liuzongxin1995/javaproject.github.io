package com.liu.javaproject.service;

import com.liu.javaproject.model.common.ResponseHashResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author huangtao
 * @create 2019/6/21 19:22
 */
public interface FileCommonService<T> {
    /**
     * 上传单个文件
     * @param file
     * @param fileType
     * @param fileAccessUrlPrefix
     * @param localPathPrefix
     * @return
     */
    ResponseHashResult upload(MultipartFile file, String fileType, String fileAccessUrlPrefix, String localPathPrefix);

    /**
     * 上传多个文件(除了exe文件)
     * @param files
     * @param fileType
     * @param fileAccessUrlPrefix
     * @param localPathPrefix
     * @return
     */
    ResponseHashResult uploadV2(MultipartFile[] files, String fileType, String fileAccessUrlPrefix, String localPathPrefix);

    /**
     *
     * @param clazz
     * @param list
     * @param fileName
     * @param fileAccessUrlPrefix
     * @param localPathPrefix
     * @return
     */
    ResponseHashResult exportFile(Class<T> clazz, List<T> list, String fileName, String fileAccessUrlPrefix, String localPathPrefix);

 /*   ResponseHashResult uploadAppendix(MultipartFile[] files,String token);*/

    ResponseHashResult uploadAppendix2(File file);

}
