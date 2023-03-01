package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.itheima.reggie.common.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


/**
 * @author 张壮
 * @description TODO
 * @since 2023/2/26 17:19
 **/
@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "文件处理(上传,下载) 接口")
public class CommonController {


    @Value("${reggie.path}")
    String basePath;




    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        // file 是临时文件,存在服务器的 C:\Users\zhuang\AppData\Local\Temp\Tomcat*** 中
        log.info(file.toString());


        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 服务器存储的文件名
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 将临时文件转存到制定位置
            file.transferTo(new File(basePath + fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @ApiOperation(value = "文件下载")

    @GetMapping("/download")
    public void download(String name,@ApiIgnore HttpServletResponse response) {

        try (
                FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
                ServletOutputStream outputStream = response.getOutputStream();) {

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

