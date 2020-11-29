package io.renren.modules.busi.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.ReceptionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 接待记录
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-29 00:51:30
 */
@RestController
@RequestMapping("busi/reception")
public class ReceptionController extends AbstractController {
    @Autowired
    private ReceptionService receptionService;

    /**
     * 列表
     */
    @RequestMapping("/lst")
    public R lst(@RequestBody Map<String, Object> params) {
        PageUtils page = receptionService.qryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:reception:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = receptionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:reception:info")
    public R info(@PathVariable("id") Integer id) {
        ReceptionEntity reception = receptionService.getById(id);

        return R.ok().put("reception", reception);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Map<String, Object> params) {
        Map<String, Object> receptionMap = ParamResolvor.getMap(params, "reception");
        ReceptionEntity receptionEntity = new ReceptionEntity();
        receptionEntity.setReceptionistId(getUserId().intValue());
        receptionEntity.setProjectId(ParamResolvor.getInt(receptionMap, "projectId"));
        receptionEntity.setCustomerId(ParamResolvor.getInt(receptionMap, "customerId"));
        receptionEntity.setSalerId(ParamResolvor.getInt(receptionMap, "salerId"));
        receptionEntity.setCnt(ParamResolvor.getInt(receptionMap, "cnt"));
        receptionEntity.setMemo(ParamResolvor.getString(receptionMap, "memo"));
        receptionEntity.setStatus(0);
        receptionEntity.setCreateTime(new Date());

        Map<String, Object> customerMap = ParamResolvor.getMap(params, "customer");
        BusiCustomerEntity busiCustomerEntity = new BusiCustomerEntity();
        busiCustomerEntity.setId(ParamResolvor.getIntAsDefault(customerMap, "id", 0));
        busiCustomerEntity.setMobilePhone(ParamResolvor.getString(customerMap, "mobilePhone"));
        busiCustomerEntity.setName(ParamResolvor.getString(customerMap, "name"));
        busiCustomerEntity.setSex(ParamResolvor.getString(customerMap, "sex"));
        busiCustomerEntity.setSource(ParamResolvor.getString(customerMap, "source"));
        busiCustomerEntity.setSourceName(ParamResolvor.getString(customerMap, "sourceName"));
        busiCustomerEntity.setSourceMobile(ParamResolvor.getString(customerMap, "sourceMobile"));
        busiCustomerEntity.setMatchUserId(ParamResolvor.getInt(customerMap, "matchUserId") + "");
        busiCustomerEntity.setOldMatchUserId(ParamResolvor.getInt(customerMap, "oldMatchUserId") + "");
        busiCustomerEntity.setOldMatchUserName(ParamResolvor.getString(customerMap, "oldMatchUserName") + "");
        busiCustomerEntity.setCreateTime(new Date());

        receptionService.saveReception(receptionEntity, busiCustomerEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:reception:update")
    public R update(@RequestBody ReceptionEntity reception) {
        receptionService.updateById(reception);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:reception:delete")
    public R delete(@RequestBody Integer[] ids) {
        receptionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
