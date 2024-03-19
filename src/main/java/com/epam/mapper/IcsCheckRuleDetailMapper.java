package com.epam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.epam.entity.IcsCheckRuleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 监督检查用语存在问题类别可选项
 * @Author: nbacheng
 * @Date:   2023-09-11
 * @Version: V1.0
 */
public interface IcsCheckRuleDetailMapper extends BaseMapper<IcsCheckRuleDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<IcsCheckRuleDetail> selectByMainId(@Param("mainId") String mainId);
}
