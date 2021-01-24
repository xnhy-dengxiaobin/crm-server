package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.vo.BusiOrderVO;
import io.renren.modules.busi.vo.BusiTradeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Mapper
public interface BusiOrderDao extends BaseMapper<BusiOrderEntity> {
  List<BusiOrderEntity> listPage(Map<String, Object> params);

  Integer listPageCount(Map<String, Object> params);

  @Select("<script>select * from busi_trade where data_prepared=1 <if test=\"ids!=null\">\n" +
    "           and project_id in\n" +
    "            <foreach collection=\"ids\" item=\"id\" index=\"index\" open=\"(\" close=\")\" separator=\",\">\n" +
    "                #{id}\n" +
    "            </foreach>\n" +
    "        </if> and payformname!='一次性付款' and (cstname LIKE CONCAT('%',#{condition},'%') or csttel LIKE CONCAT('%',#{condition},'%')) order by qsdate desc \n" +
    "</script>")
  IPage<BusiTradeVO> promptPage(List<Integer> ids,IPage iPage, String condition);
}
