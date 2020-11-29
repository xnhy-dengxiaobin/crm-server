package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.entity.BusiUserProjectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@Mapper
public interface BusiUserProjectDao extends BaseMapper<BusiUserProjectEntity> {
    /**
     * 根据当前用户的token查询关联项目
     * @param userId
     * @return
     */
	List<BusiProjectEntity> selectProjectByUser(Long userId);

    /**
     * TODO：admin查询所有用户
     * @return
     */
	List<BusiProjectEntity> selectProjectByAdmin();
}
