package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiContractDao;
import io.renren.modules.busi.entity.BusiContractEntity;
import io.renren.modules.busi.service.BusiContractService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiContractService")
public class BusiContractServiceImpl extends ServiceImpl<BusiContractDao, BusiContractEntity> implements BusiContractService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiContractEntity> page = this.page(
                new Query<BusiContractEntity>().getPage(params),
                new QueryWrapper<BusiContractEntity>()
        );

        return new PageUtils(page);
    }

}
