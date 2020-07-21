package com.liu.javaproject.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.liu.javaproject.model.common.*;
import com.liu.javaproject.model.enums.FileTypeEnum;
import com.liu.javaproject.service.FileCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author huangtao
 * @create 2019/6/21 19:32
 */
@Service
@Slf4j

public class FileCommonServiceImpl<T> implements FileCommonService<T> {

    public static final long MAX_FILE_SIZE_200KB = 200 * 1024;
    public static final long MAX_FILE_SIZE_1MB = 1024 * 1024;

    /*@Value("${url.fileUploadBaseUrl}")
    private String fileUploadBaseUrl;*/

    @Override
    public ResponseHashResult upload(MultipartFile file, String fileType, String fileAccessUrlPrefix, String localPathPrefix) {
        if (StringUtils.isEmpty(file) || StringUtils.isEmpty(fileType)) {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        Map<String, Object> map = new HashMap<>();
        String fileName = file.getOriginalFilename();
        if (fileName.contains(",")) {
            return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_CANNOTCONTAINCOMMA));
        }

        //检查文件大小是否超出限制
        if (checkFileSize(fileType, file)) {
            return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_IS_TOO_LARGE));
        }

        // 上传文件格式检查
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!checkFileFormat(fileType, suffix.trim().toLowerCase())) {
            return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_FORMAT_NOTALLOW));
        }

        // 文件类型目录名
        String typeDirectoryName = FileTypeEnum.getValueByKey(fileType);
        if (StringUtils.isEmpty(typeDirectoryName)) {
            return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_TYPE_NOT_SUPPORT));
        }

        // 本地随机文件名
        String localFileName = UUID.randomUUID().toString().replaceAll("-", "");

        String savePath = BosConstants.FILE_PREFIX + typeDirectoryName + "/" + localFileName + "-" + fileName;
        String accessPath = fileAccessUrlPrefix + savePath;
        map.put("savePath", savePath);
        map.put("accessPath", accessPath);

        File filePath = new File(localPathPrefix + BosConstants.FILE_PREFIX + typeDirectoryName);

        System.out.println("filePath = " + filePath.getAbsolutePath());
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        File localFile = new File(localPathPrefix + File.separator + savePath);
        try {
            file.transferTo(localFile);
            localFile.setReadable(true, false);
            return new ResponseHashResult(new ResponseStatus(ResultCode.SUCCESS), map);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_UPLOAD_FAILED));
        }
    }

    @Override
    public ResponseHashResult uploadV2(MultipartFile[] files, String fileType, String fileAccessUrlPrefix, String localPathPrefix) {
        if (files == null || files.length <= 0) {
            return new ResponseHashResult(new ResponseStatus(10001, "请选择上传的文件", false));
        }

        if (StringUtils.isEmpty(fileType)) {
            return new ResponseHashResult(new ResponseStatus(10001, "请选择文件类型", false));
        }

        List<Map<String, String>> list = new ArrayList<>();
        List<Map<String, Object>> onlineList = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (MultipartFile file : files) {
            if (file != null) {
                Map<String, String> map = new HashMap<>();
                String fileName = file.getOriginalFilename();
                if (fileName.contains(",")) {
                    return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_CANNOTCONTAINCOMMA));
                }

                // 上传文件格式检查
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (!checkFileFormat(fileType, suffix.trim().toLowerCase())) {
                    return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_FORMAT_NOTALLOW));
                }

                //检查文件大小是否超出限制
                if (checkFileSize(fileType, file)) {
                    return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_IS_TOO_LARGE));
                }

                // 文件类型目录名
                String typeDirectoryName = FileTypeEnum.getValueByKey(fileType);
                if (StringUtils.isEmpty(typeDirectoryName)) {
                    return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_TYPE_NOT_SUPPORT));
                }

                // 本地随机文件名
                String localFileName = UUID.randomUUID().toString().replaceAll("-", "");

                String savePath = BosConstants.FILE_PREFIX + typeDirectoryName + "/" + localFileName + "-" + fileName;
                String accessPath = fileAccessUrlPrefix + savePath;
                map.put("savePath", savePath);
                map.put("accessPath", accessPath);

                //	File filePath = new File(projectPath + BosConstants.FILE_PREFIX + typeDirectoryName);
                File filePath = new File(localPathPrefix + BosConstants.FILE_PREFIX + typeDirectoryName);

                System.out.println("filePath = " + filePath.getAbsolutePath());
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }

                File localFile = new File(localPathPrefix + File.separator + savePath);
                try {
                    file.transferTo(localFile);
                    log.info("bos上传文件，入参为{}，localFile为{}",file,localFile);
                    localFile.setReadable(true, false);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("bos上传文件失败，入参{}，错误信息{}",files,e.getMessage());
                    return new ResponseHashResult(new ResponseStatus(ResultCode.FILE_UPLOAD_FAILED));
                }
                list.add(map);

                //盘存上传附件至采购
                if ("10".equals(fileType)) {
                    ResponseHashResult responseHashResult = uploadAppendix2(localFile);
                    //上传文件成功，将路径给前端
                    if (responseHashResult.getStatus().isSuccess()) {
                        Map<String, Object> data = responseHashResult.getData();
                        onlineList.add(data);
                    }else{
                        //上传文件失败
                        return responseHashResult;
                    }
                }
            }
        }
        hashMap.put("list", list);
        hashMap.put("onlineList", onlineList);
        return new ResponseHashResult(new ResponseStatus(ResultCode.SUCCESS),hashMap);
    }

    /**
     * easy-poi导出文件
     * @param clazz
     * @param list
     * @param fileName
     * @param fileAccessUrlPrefix
     * @param localPathPrefix
     * @return
     */
    @Override
    public ResponseHashResult exportFile(Class<T> clazz, List<T> list, String fileName, String fileAccessUrlPrefix, String localPathPrefix)
    {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), clazz, list);
        workbook.setSheetName(0, fileName);
        workbook.getSheetAt(0).setDefaultRowHeight((short)21);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String savePath = GoodsConstants.EXCEL_FILE_PREFIX + fileName + sdf.format(new Date()) + ".xls";
        String accessPath = fileAccessUrlPrefix + savePath;

        HashMap<String, Object> map = new HashMap<>();
        map.put("savePath", savePath);
        map.put("accessPath", accessPath);

        OutputStream os = null;
        try
        {
            File localFileDir = new File(localPathPrefix + GoodsConstants.EXCEL_FILE_PREFIX);
            if (!localFileDir.exists())
            {
                localFileDir.mkdirs();
            }

            File localFile = new File(localPathPrefix, savePath);
            os = new FileOutputStream(localFile);
            workbook.write(os);
            os.flush();
        }
        catch (IOException e)
        {
            log.error("[error : FileCommonServiceImpl.exportFile()], IO operator error!", e);
            return new ResponseHashResult(new ResponseStatus(ResultCode.FAIL));
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    log.error("[error : FileCommonServiceImpl.exportFile()], close stream error!");
                }
            }
        }
        return new ResponseHashResult(new ResponseStatus(ResultCode.SUCCESS),map);
    }

    @Override
    public ResponseHashResult uploadAppendix2(File file) {
        //1.--------------向采购上传文件-----------------
        /*HashMap<String, Object> data = new HashMap<>();
            String url = fileUploadBaseUrl + "/v0/file/upload";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            HttpHeaders headers = new HttpHeaders();
            // 传参为form-data格式
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            map.add("fileType","47");
            map.add("serviceName","bos");
            map.add("accessFlag",true);
            String token = CurrentUtil.getCurrentToken();
            headers.add("Token",token);

            FileSystemResource fileSystemResource = new FileSystemResource(file);

            map.add("file",fileSystemResource);
            HttpEntity httpEntity = new HttpEntity(map, headers);
            try {
                ResponseHashResult res = restTemplate.postForObject(url, httpEntity, ResponseHashResult.class);
                if(res != null) {
                    if (!res.getStatus().isSuccess()) {
                        log.info("[info]向采购上传附件失败，入参:{}，token为:{},返回结果:{}", map,token, res);
                        return res;
                    } else {
                        log.info("[info]向采购上传附件成功，入参:{}，token为:{},返回结果:{}", map,token, res);
                         data = (HashMap<String, Object>) res.getData();

                    }
                } else {
                    log.info("[info]向采购上传附件失败，入参:{}，token为:{},返回结果为null", map,token);
                }

            } catch (RestClientException e) {
                e.printStackTrace();
                log.error("[error]向采购上传附件发生异常，入参:{},token为:{},异常信息为:{}",map,token,e.getMessage());
                return new ResponseHashResult(new ResponseStatus(ResultCode.FAIL));
            }

        return new ResponseHashResult(new ResponseStatus(ResultCode.SUCCESS),data);*/
        return null;
    }

    /**
     * 根据文件类型判断是否超过大小限制
     * @param fileType
     * @return true-超出限制 false-未超出限制
     */
    private boolean checkFileSize(String fileType, MultipartFile file) {
        switch (fileType) {
            //头像图片
            case "1":
                if (file.getSize() > MAX_FILE_SIZE_200KB) {
                    return true;
                }
                break;
            //商品图片
            case "4":
            case "5":
                if (file.getSize() > MAX_FILE_SIZE_1MB) {
                    return true;
                }
                break;
            default:
                break;
        }

        return false;
    }

    /**
     * 根据文件类型判断格式是否允许
     * @param fileType 文件类型
     * @param suffix 文件后缀（小写）
     * @return true-允许 false-不允许
     */
    private boolean checkFileFormat(String fileType, String suffix) {
        String suffixList = "jpg,jpeg,png,gif";
        switch (fileType) {
            //头像图片
            case "1":
            case "2":
            case "3":
            //商品图片
            case "4":
            case "5":
            case "7":
            case "8":
                if (suffixList.contains(suffix)) {
                    return true;
                }
                break;
            //维修申请附件
            case "6":
            //投诉附件
            case "9":
            //盘存附件
            case "10":
                if(!"exe".equals(suffix)) {
                    return true;
                }
                break;
            //其他
            case "other":
                return true;
            default:
                break;
        }

        return false;
    }

}
