package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.exception.BusiException;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.busi.service.ReceptionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


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

    @Autowired
    private BusiCustomerService busiCustomerService;

    @Autowired
    private SysUserService sysUserService;

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
    @RequestMapping("/listBySalerId")
    public R listBySalerId(@RequestBody Map<String, Object> params) {
        params.put("salerId", getUserId());
        params.put("status", ParamResolvor.getIntAsDefault(params, "status", -1) + "");
//        params.put("projectId",getProjectId());
        PageUtils maps = receptionService.listBySalerId(params);
        return R.ok().put("page", maps);
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
        try {
            Map<String, Object> customerMap = ParamResolvor.getMap(params, "customer");

            Map<String, Object> receptionMap = ParamResolvor.getMap(params, "reception");
            ReceptionEntity receptionEntity = new ReceptionEntity();
            receptionEntity.setReceptionistId(getUserId().intValue());
            receptionEntity.setReceptionistName(getUser().getName());
            receptionEntity.setProjectId(ParamResolvor.getInt(receptionMap, "projectId"));
            receptionEntity.setCustomerId(ParamResolvor.getInt(receptionMap, "customerId"));
            receptionEntity.setSalerId(ParamResolvor.getInt(receptionMap, "salerId"));
            receptionEntity.setCnt(ParamResolvor.getInt(receptionMap, "cnt"));
            receptionEntity.setMemo(ParamResolvor.getString(receptionMap, "memo"));
            receptionEntity.setStatus(0);
            receptionEntity.setCreateTime(new Date());

            BusiCustomerEntity busiCustomerEntity = new BusiCustomerEntity();
            busiCustomerEntity.setId(ParamResolvor.getIntAsDefault(customerMap, "id", 0));
            busiCustomerEntity.setMobilePhone(ParamResolvor.getString(customerMap, "mobilePhone"));
            busiCustomerEntity.setName(ParamResolvor.getString(customerMap, "name"));
            busiCustomerEntity.setSex(ParamResolvor.getString(customerMap, "sex"));
            busiCustomerEntity.setSource(ParamResolvor.getString(customerMap, "source"));
            busiCustomerEntity.setSourceId(ParamResolvor.getInt(customerMap, "sourceId"));
            busiCustomerEntity.setSourceName(ParamResolvor.getString(customerMap, "sourceName"));
            busiCustomerEntity.setSourceUserId(ParamResolvor.getInt(customerMap, "sourceUserId"));
            busiCustomerEntity.setSourceUserName(ParamResolvor.getString(customerMap, "sourceUserName"));
            busiCustomerEntity.setSourceMobile(ParamResolvor.getString(customerMap, "sourceMobile"));
            busiCustomerEntity.setMatchUserId(ParamResolvor.getInt(customerMap, "matchUserId") + "");
            busiCustomerEntity.setMatchUserName(ParamResolvor.getString(customerMap, "matchUserName"));
            busiCustomerEntity.setOldMatchUserId(ParamResolvor.getInt(customerMap, "oldMatchUserId") + "");
            busiCustomerEntity.setOldMatchUserName(ParamResolvor.getString(customerMap, "oldMatchUserName") + "");
            busiCustomerEntity.setCreateTime(new Date());

            SysUserEntity matchUser = sysUserService.getById(busiCustomerEntity.getMatchUserId());
            BusiCustomerRoamEntity busiCustomerRoamEntity = new BusiCustomerRoamEntity();
            busiCustomerRoamEntity.setUserId(getUserId().intValue());
            busiCustomerRoamEntity.setRemark("分配，被" + getUser().getName() + "分配至" + matchUser.getName());
            busiCustomerRoamEntity.setCreateTime(new Date());

            int prepareId = ParamResolvor.getIntAsDefault(params, "prepareId", 0);

            receptionService.saveReception(receptionEntity, busiCustomerEntity, busiCustomerRoamEntity, prepareId);

            return R.ok();
        } catch (BusiException e) {
            return R.error(e.getMessage());
        }
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
