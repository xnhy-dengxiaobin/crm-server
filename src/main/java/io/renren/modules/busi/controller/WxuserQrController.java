package io.renren.modules.busi.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.WxuserQrEntity;
import io.renren.modules.busi.service.WxuserQrService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 个人推广码
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-21 23:17:58
 */
@RestController
@RequestMapping("busi/wxuserqr")
public class WxuserQrController {
    @Autowired
    private WxuserQrService wxuserQrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:wxuserqr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxuserQrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{unionId}")
    @RequiresPermissions("busi:wxuserqr:info")
    public R info(@PathVariable("unionId") String unionId){
		WxuserQrEntity wxuserQr = wxuserQrService.getById(unionId);

        return R.ok().put("wxuserQr", wxuserQr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:wxuserqr:save")
    public R save(@RequestBody WxuserQrEntity wxuserQr){
		wxuserQrService.save(wxuserQr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:wxuserqr:update")
    public R update(@RequestBody WxuserQrEntity wxuserQr){
		wxuserQrService.updateById(wxuserQr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:wxuserqr:delete")
    public R delete(@RequestBody String[] unionIds){
		wxuserQrService.removeByIds(Arrays.asList(unionIds));

        return R.ok();
    }

}
