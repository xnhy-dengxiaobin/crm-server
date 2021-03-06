package io.renren.modules.busi.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.entity.PrepareCheckEntity;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.PrepareEntity;
import io.renren.modules.busi.service.PrepareService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * WX报备表
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
@RestController
@RequestMapping("busi/prepare")
public class PrepareController extends AbstractController {
    @Autowired
    private PrepareService prepareService;

    /**
     * 列表
     */
    @RequestMapping("/lst")
    public R lst(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = prepareService.qryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:prepare:list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = prepareService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:prepare:info")
    public R info(@PathVariable("id") Integer id) {
        PrepareEntity prepare = prepareService.getById(id);

        return R.ok().put("prepare", prepare);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:prepare:save")
    public R save(@RequestBody PrepareEntity prepare) {
        prepareService.save(prepare);

        return R.ok();
    }

    /**
     * wx保存
     */
    @RequestMapping("/wx/save")
    @SysLog("小程序报备")
    public R wxsave(@RequestBody PrepareEntity prepare) {
        prepare.setUserName(getUser().getName());
        String msg = prepareService.wxSave(prepare, getUserId());
        if (msg.indexOf("拒收无效") > 0) {
            return R.error(msg);
        }
        return R.ok();

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:prepare:update")
    @SysLog("修改报备")
    public R update(@RequestBody PrepareEntity prepare) {
        prepareService.updateById(prepare);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:prepare:delete")
    @SysLog("删除报备")
    public R delete(@RequestBody Integer[] ids) {
        prepareService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/check")
    @RequiresPermissions("busi:prepare:check")
    @SysLog("判客状态")
    public R check(@RequestBody PrepareCheckEntity checks) {
        checks.setOperId(getUserId().intValue());
        prepareService.check(checks);
        return R.ok();
    }

    /**
     * 查询和客户相关联的列表
     */
    @RequestMapping("/lstPage")
    public R lstPage(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = prepareService.lstPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/nf/{id}")
    public R nf(@PathVariable("id") Integer id) {
        PrepareEntity prepare = prepareService.gtById(id);

        return R.ok().put("prepare", prepare);
    }

    /***********************4 后台**************************/
    /**
     * 查询和客户相关联的列表
     */
    @RequestMapping("/listPage4Admin")
    public R listPage4Admin(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = prepareService.listPage4Admin(params);
        return R.ok().put("page", page);
    }

    /**
     * 修改
     */
    @RequestMapping("/refresh")
    @RequiresPermissions("busi:prepare:update")
    @SysLog("刷新有效期")
    public R refreshExpired(@RequestBody Map<String, Object> params) {
        prepareService.refreshExpired(ParamResolvor.getCommonList(params, "ids"));

        return R.ok();
    }
}
