package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.modules.busi.dao.*;
import io.renren.modules.busi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.service.BusiCustomerFollowService;
import org.springframework.transaction.annotation.Transactional;


@Service("busiCustomerFollowService")
public class BusiCustomerFollowServiceImpl extends ServiceImpl<BusiCustomerFollowDao, BusiCustomerFollowEntity> implements BusiCustomerFollowService {

    @Autowired
    private BusiCustomerFollowDao busiCustomerFollowDao;

    @Autowired
    private BusiCustomerDao busiCustomerDao;

    @Autowired
    private ReceptionDao receptionDao;

    @Autowired
    private BusiProjectDao busiProjectDao;

    @Autowired
    private BusiCustomerProjectDao busiCustomerProjectDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerFollowEntity> page = this.page(
                new Query<BusiCustomerFollowEntity>().getPage(params),
                new QueryWrapper<BusiCustomerFollowEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer toDayCount(String projectId) {
        LocalDate localDate = LocalDate.now();
        return baseMapper.toDayCount(projectId, localDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFollow(BusiCustomerFollowEntity busiCustomerFollow, BusiCustomerEntity busiCustomerEntity) {
        busiCustomerFollowDao.insert(busiCustomerFollow);
        busiCustomerDao.updateById(busiCustomerEntity);
        if (busiCustomerFollow.getProjectIds() != null) {
            for (Integer projectId : busiCustomerFollow.getProjectIds()) {
                BusiProjectEntity byId = busiProjectDao.selectById(projectId);
                BusiCustomerProjectEntity entity = new BusiCustomerProjectEntity();
                entity.setCustomerId(busiCustomerFollow.getCustomerId());
                entity.setProjectId(byId.getId());
                entity.setProject(byId.getName());
                busiCustomerProjectDao.insert(entity);
            }
        }
    }
}
