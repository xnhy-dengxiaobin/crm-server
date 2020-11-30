package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.ReceptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 接待记录
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-29 00:51:30
 */
@Mapper
public interface ReceptionDao extends BaseMapper<ReceptionEntity> {

    @Select("select br.*,bc.name from busi_reception br left join busi_customer bc on br.customer_id = bc.id where saler_id = #{salerId}")
    IPage<Map<String, Object>> listBySalerId(IPage<ReceptionEntity> page, Long salerId);
}
