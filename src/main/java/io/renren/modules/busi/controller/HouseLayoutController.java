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
    @RequiresPermissions("busi:houselayout:upload")
    public R upload(@RequestParam MultipartFile[] uploads, @RequestParam String dirId, HttpServletRequest request) throws IOException {
        List<String> files = new ArrayList<>();
        String realPath = getClass().getClassLoader().getResource("").getPath();

//根据相对获取绝对路径(文件上传到的保存位置)
        realPath = realPath + "static/" + crmProp.getUploadPath() + dirId;
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

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
                File newFile = new File(dir, newFileName);
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

    @RequestMapping("/download")
    @RequiresPermissions("busi:houselayout:download")
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
