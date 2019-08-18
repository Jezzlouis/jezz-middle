package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbAdminPO;
import com.jezz.session.domain.po.TbAdminPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAdminPOMapper {
    int countByExample(TbAdminPOExample example);

    int deleteByExample(TbAdminPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAdminPO record);

    int insertSelective(TbAdminPO record);

    List<TbAdminPO> selectByExample(TbAdminPOExample example);

    TbAdminPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAdminPO record, @Param("example") TbAdminPOExample example);

    int updateByExample(@Param("record") TbAdminPO record, @Param("example") TbAdminPOExample example);

    int updateByPrimaryKeySelective(TbAdminPO record);

    int updateByPrimaryKey(TbAdminPO record);
}