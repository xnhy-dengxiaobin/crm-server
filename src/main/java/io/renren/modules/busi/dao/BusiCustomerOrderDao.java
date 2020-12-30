package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiCustomerOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.vo.BusiCustomerOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Mapper
public interface BusiCustomerOrderDao extends BaseMapper<BusiCustomerOrderEntity> {
  @Select("select * from ")
  public List<BusiCustomerOrderVO> queryListInfo(String[] ids);
}
