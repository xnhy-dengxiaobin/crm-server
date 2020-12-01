package io.renren.modules.busi.controller;

import io.renren.common.utils.R;
import io.renren.modules.busi.props.CrmProp;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 *
 * @author æå
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/file")
@Slf4j
public class FileController extends AbstractController {

    @Autowired
    private CrmProp crmProp;

    @Autowired
    private SysUserService sysUserService;

    // 上传文件
    @RequestMapping("/upload/head")
    public R upload(@RequestParam MultipartFile upload, HttpServletRequest request) {
        String file = "";
        String realPath = getClass().getClassLoader().getResource("").getPath();
        try {
            //根据相对获取绝对路径(文件上传到的保存位置)
            realPath = realPath + "static/" + crmProp.getUploadPath() + "head";
            makeDirPath(realPath);
            //获取文件后缀
            String fileName = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().lastIndexOf("."));
            String extension = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
            //文件名我这里使用UUID和时间组成的
            String newFileNamePrefix = fileName +
                    new SimpleDateFormat("yyyyMMddHHss").format(new Date());
            String newFileName = newFileNamePrefix + "." + extension;
            //处理文件上传
            upload.transferTo(new File(realPath, newFileName));
            file = crmProp.getUploadPath() + "head" + "/" + newFileName;
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setUserId(getUserId());
            sysUserEntity.setHead(crmProp.getUploadPath() + "head" + "/" + newFileName);
            sysUserService.updateById(sysUserEntity);
        } catch (Exception e) {
            log.error("文件上传出错", e);
            R.error("文件上传出错");
        }
        return R.ok().put("fileUrl", file);
    }

    /**
     * 创建目标路径所涉及的目录
     */
    public static void makeDirPath(String targetAddr) {
        String realFileParentPath = targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }
}
