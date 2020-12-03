package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.ReceptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

import java.util.List;
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
	List<ReceptionEntity> slct(Map<String, Object> params);
	Long cnt(Map<String, Object> params);

    List<ReceptionEntity> listBySalerId(Map<String, Object> params);
    Long listBySalerIdCnt(Map<String, Object> params);
}
