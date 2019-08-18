package com.jezz.session.mapper;

import com.jezz.session.domain.po.TbOrganizationPO;
import com.jezz.session.domain.po.TbOrganizationPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrganizationPOMapper {
    int countByExample(TbOrganizationPOExample example);

    int deleteByExample(TbOrganizationPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbOrganizationPO record);

    int insertSelective(TbOrganizationPO record);

    List<TbOrganizationPO> selectByExample(TbOrganizationPOExample example);

    TbOrganizationPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbOrganizationPO record, @Param("example") TbOrganizationPOExample example);

    int updateByExample(@Param("record") TbOrganizationPO record, @Param("example") TbOrganizationPOExample example);

    int updateByPrimaryKeySelective(TbOrganizationPO record);

    int updateByPrimaryKey(TbOrganizationPO record);
}