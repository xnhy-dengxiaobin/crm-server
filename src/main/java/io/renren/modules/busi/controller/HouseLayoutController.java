package io.renren.modules.busi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.modules.busi.props.CrmProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.HouseLayoutEntity;
import io.renren.modules.busi.service.HouseLayoutService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 项目户型图
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-28 10:32:11
 */
@RestController
@RequestMapping("busi/houselayout")
@Slf4j
public class HouseLayoutController {
    @Autowired
    private HouseLayoutService houseLayoutService;

    @Autowired
    private CrmProp crmProp;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:houselayout:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = houseLayoutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:houselayout:info")
    public R info(@PathVariable("id") Integer id) {
        HouseLayoutEntity houseLayout = houseLayoutService.getById4Project(id);

        return R.ok().put("houseLayout", houseLayout);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:houselayout:save")
    public R save(@RequestBody HouseLayoutEntity houseLayout) {
        houseLayoutService.save(houseLayout);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:houselayout:update")
    public R update(@RequestBody HouseLayoutEntity houseLayout) {
        houseLayoutService.updateById(houseLayout);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:houselayout:delete")
    public R delete(@RequestBody Integer[] ids) {
        houseLayoutService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    // 上传文件
    @RequestMapping("/upload")
    public R upload(@RequestParam MultipartFile[] uploads, @RequestParam String dirId, HttpServletRequest request) throws IOException {
        List<String> files = new ArrayList<>();

//根据相对获取绝对路径(文件上传到的保存位置)
        String filePath = crmProp.getUploadPath() + dirId;
        log.debug("上传目录:{}", filePath);
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        log.debug("上传目录已创建:{}", filePath);
        try {
            for (MultipartFile upload : uploads) {
//获取文件后缀
                String fileName = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().lastIndexOf("."));
                String extension = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
//文件名我这里使用UUID和时间组成的
                String newFileNamePrefix = fileName +
                        new SimpleDateFormat("yyyyMMddHHss").format(new Date());
                String newFileName = newFileNamePrefix + "." + extension;
//处理文件上传
                filePath = filePath + File.separator + newFileName;
                File newFile = new File(filePath);
                log.debug("上传文件为:{}", filePath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                upload.transferTo(newFile);
                files.add(dirId + "/" + newFileName);
            }
        } catch (Exception e) {
            log.error("文件上传出错", e);
            R.error("文件上传出错");
        }
        return R.ok().put("files", files);
    }

    @RequestMapping("/downloadFP/{dirId}/{fileName}")
    public ResponseEntity<byte[]> downloadFP(HttpServletRequest request,@PathVariable String dirId, @PathVariable String fileName) {
        try {
            String path = crmProp.getUploadPath() + dirId + File.separator + fileName;
            HttpHeaders headers = new HttpHeaders();
            String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            MediaType contentType = null;
            switch (ext) {
                case "jpg":
                    contentType = MediaType.IMAGE_JPEG;
                    break;
                case "jpeg":
                    contentType = MediaType.IMAGE_JPEG;
                    break;
                case "png":
                    contentType = MediaType.IMAGE_PNG;
                    break;
                default:
                    contentType = MediaType.IMAGE_GIF;
            }
            headers.setContentDispositionFormData("attachment", name);
            headers.setContentType(contentType);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(new File(path))),
                    headers, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("文件下载出错", e);
        }
        return null;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable Integer id) {
        try {
            HouseLayoutEntity houseLayout = houseLayoutService.getById(id);
            String path = crmProp.getUploadPath() + houseLayout.getPath();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", houseLayout.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(new File(path + houseLayout.getName()))),
                    headers, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("文件下载出错", e);
        }
        return null;
    }
}
