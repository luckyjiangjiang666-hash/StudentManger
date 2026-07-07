package com.jxl.studentmanger.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jxl.studentmanger.exception.BusinessException;
import com.jxl.studentmanger.response.R;
import com.jxl.studentmanger.response.ResponseCode;
import com.jxl.studentmanger.util.Captcha;
import com.jxl.studentmanger.util.CaptchaCache;
import com.jxl.studentmanger.util.UploadFile;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RestController
public class CommonController {
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private CaptchaCache captchaCache;

    @Value("${file.server.dir}")
    private String fileDir = "";
    @Value("${file.server.path}")
    private String path = "";

    @RequestMapping("/common/getCaptcha")
    @CrossOrigin
    public R<Captcha> getCaptcha(){
        String captchaText = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(captchaText);
        String base64Code;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            base64Code = Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.CREATE_CAPTCHA_ERROR);
        }
        /*
         * 返回验证码对象
         * 图片 base64格式
         */
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Captcha captchaVO = new Captcha();
        captchaVO.setCaptchaId(uuid);
        captchaVO.setCaptchaImage("data:image/png;base64," + base64Code);
        captchaCache.storeCaptcha(uuid, captchaText);
        return R.data(captchaVO);
    }

    /*
     * 上传文件
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/common/uploadFile")
    @CrossOrigin
    public R<UploadFile> upload(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file) throws ServletException, IOException {
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        String fname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
        String basePath = fileDir;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String furl = path + File.separator +fname;
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + furl;
        File dest = new File(basePath, fname);
        try {
            file.transferTo(dest);
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(furl);
            uploadFile.setUrl(url);
            return R.data(uploadFile);
        } catch (IOException e) {
            return R.fail();
        }
    }
}

