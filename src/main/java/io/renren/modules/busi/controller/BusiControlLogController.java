package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiControlLogEntity;
import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.service.BusiControlLogService;
import io.renren.modules.busi.service.BusiHouseService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 销控日志
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-27 13:25:39
 */
@RestController
@RequestMapping("busi/busicontrollog")
public class BusiControlLogController extends AbstractController {
    @Autowired
    private BusiControlLogService busiControlLogService;
    @Autowired
    private BusiHouseService busiHouseService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiControlLogService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/listAllByHouseId/{houseId}")
    public R listAllByHouseId(@PathVariable("houseId") Integer houseId){
        List<BusiControlLogEntity> list = busiControlLogService.list(new QueryWrapper<BusiControlLogEntity>()
                .lambda()
                .eq(BusiControlLogEntity::getHouseId, houseId).orderByDesc(BusiControlLogEntity::getCreateTime));
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BusiControlLogEntity busiControlLog = busiControlLogService.getById(id);

        return R.ok().put("busiControlLog", busiControlLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiControlLogEntity busiControlLog){
        if(busiControlLog.getControlStatus() == 1){
            BusiHouseEntity busiHouseEntity = new BusiHouseEntity();
            busiHouseEntity.setId(busiControlLog.getHouseId());
            busiHouseEntity.setStatus("10");
            busiHouseService.updateById(busiHouseEntity);
        }else if(busiControlLog.getControlStatus() == 0){
            BusiHouseEntity busiHouseEntity = new BusiHouseEntity();
            busiHouseEntity.setId(busiControlLog.getHouseId());
            busiHouseEntity.setStatus("1");
            busiHouseService.updateById(busiHouseEntity);
        }
        else{
            throw new RRException("无效状态");
        }
        busiControlLog.setCreateId(getUserId());
        busiControlLog.setCreateName(getUser().getName());
        busiControlLog.setCreateTime(new Date());
		busiControlLogService.save(busiControlLog);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicontrollog:update")
    public R update(@RequestBody BusiControlLogEntity busiControlLog){
		busiControlLogService.updateById(busiControlLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicontrollog:delete")
    public R delete(@RequestBody Integer[] ids){
		busiControlLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
