/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.busi.service.BusiUserProjectService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private BusiUserProjectService busiUserProjectService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Long createUserId = (Long) params.get("createUserId");
        String appRole = (String) params.get("appRole");

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(createUserId != null, "create_user_id", createUserId)
                        .eq(StringUtils.isNotBlank(appRole), "app_role", appRole)
        );

        return new PageUtils(page);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public SysUserEntity queryByUnionId(String unionId) {
        return baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("union_id", unionId));
    }

    @Override
    @Transactional
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);

        //拼音首字母
        String s = PinyinHelper.toPinyin(user.getName(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);
        user.setFirstLetter(s);

        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        //用户和项目关系
        busiUserProjectService.increaseOrModify(user.getUserId(), user.getProjectIds());
    }

    @Override
    @Transactional
    public void saveWxUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);

        //拼音首字母
        String s = PinyinHelper.toPinyin(user.getName(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);
        user.setFirstLetter(s);

        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        //用户和项目关系
        busiUserProjectService.increaseOrModify(user.getUserId(), user.getProjectIds());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }

        //拼音首字母
        if (StringUtils.isNotEmpty(user.getName())) {
            String s = PinyinHelper.toPinyin(user.getName(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);
            user.setFirstLetter(s);
        }

        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        //用户和项目关系
        busiUserProjectService.increaseOrModify(user.getUserId(), user.getProjectIds());
    }

    @Override
    public void updateTeam(SysUserEntity user) {
        baseMapper.updateTeam(user.getTeamId(), user.getTeamName(), user.getUserId());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public List<SysUserEntity> queryNormalFollow(Long projectId) {
        return baseMapper.queryNormalFollow(projectId);
    }

    @Override
    public List<SysUserEntity> queryTimeoutList(Long projectId) {
        return baseMapper.queryTimeoutList(projectId);
    }

    @Override
    public List<SysUserEntity> querySalesUserByProjectId(Long projectId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        return baseMapper.querySalesUserByProjectId(params);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

//        //判断是否越权
//        if (!roleIdList.containsAll(user.getRoleIdList())) {
//            throw new RRException("新增用户所选角色，不是本人创建");
//        }
    }

    @Override
    public List<SysUserEntity> qryMiddlemen(Map<String, Object> params) {
        return getBaseMapper().slctMiddlemen(params);
    }

    /********************经纪人管理**********************/
    @Override
    public PageUtils middleManPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<MiddleTypeEntity> slct = getBaseMapper().selectMiddleMan(params);
        Integer cnt = getBaseMapper().cntMiddleMan(params);
        Page<MiddleTypeEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(slct);

        return new PageUtils(page);
    }

    @Override
    public void audit(Map<String, Object> params) {
        List<Integer> userIds = ParamResolvor.getCommonList(params, "userIds");
        Integer auditStatus = ParamResolvor.getInt(params, "auditStatus");

        for (Integer userId : userIds) {
            SysUserEntity user = new SysUserEntity();
            user.setUserId(Long.valueOf("" + userId));
            user.setAuditStatus(auditStatus);
            getBaseMapper().updateById(user);
        }
    }
}
