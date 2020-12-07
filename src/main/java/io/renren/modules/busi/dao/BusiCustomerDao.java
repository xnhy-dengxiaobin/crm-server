package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@Mapper
public interface BusiCustomerDao extends BaseMapper<BusiCustomerEntity> {
  IPage<BusiCustomerEntity> normalFollowPage(IPage page, String userId,String projectId);
  IPage<BusiCustomerEntity> timeoutPage(IPage page, String userId,String projectId,String keywords);
  IPage<BusiCustomerEntity> publicPage(IPage page, String projectId,String keywords);
    List<BusiCustomerEntity> selectByPhone(String phone);
  long countNormal(Object projectId);
  long countTimeout(Object projectId);
}
