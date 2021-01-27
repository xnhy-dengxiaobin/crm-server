package io.renren.modules.busi.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.SignOnEntity;
import io.renren.modules.busi.service.SignOnService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 员工签到表

 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-04 20:26:45
 */
@RestController
@RequestMapping("busi/signon")
public class SignOnController extends AbstractController {
    @Autowired
    private SignOnService signOnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:signon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = signOnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:signon:info")
    public R info(@PathVariable("id") Integer id){
		SignOnEntity signOn = signOnService.getById(id);

        return R.ok().put("signOn", signOn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存签到")
    public R save(@RequestBody SignOnEntity signOn){
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<SignOnEntity> signOns = signOnService.getBaseMapper().selectByMap(new HashMap<String, Object>(){{
            put("user_id", getUserId());
            put("sign_on_date", today);
        }});
        if(CollectionUtils.isNotEmpty(signOns)){
            return R.error("重复签到").put("result", 30);
        }

        signOns = signOnService.getBaseMapper().selectByMap(new HashMap<String, Object>(){{
            put("code", signOn.getCode());
        }});
        if(CollectionUtils.isNotEmpty(signOns)){
            return R.error("签到码只能使用一次").put("result", 20);
        }

        signOn.setUserId(getUserId().intValue());
        signOn.setCreatedTime(new Date());
        signOn.setSignOnDate(new Date());
		signOnService.save(signOn);

        return R.ok().put("result", 1);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:signon:update")
    public R update(@RequestBody SignOnEntity signOn){
		signOnService.updateById(signOn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:signon:delete")
    public R delete(@RequestBody Integer[] ids){
		signOnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/isSignOn")
    public R isSignOn(){
        boolean signOn = signOnService.isSignOn(getUserId().intValue());
        return R.ok().put("isSignOn", signOn);
    }
}
