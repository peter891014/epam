package com.epam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epam.entity.IcsCheckRuleDetail;
import com.epam.mapper.IcsCheckRuleDetailMapper;
import com.epam.service.IIcsCheckRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 监督检查用语存在问题类别可选项
 * @Author: nbacheng
 * @Date:   2023-09-11
 * @Version: V1.0
 */
@Service
public class IcsCheckRuleDetailServiceImpl extends ServiceImpl<IcsCheckRuleDetailMapper, IcsCheckRuleDetail> implements IIcsCheckRuleDetailService {

	@Autowired
	private IcsCheckRuleDetailMapper icsCheckRuleDetailMapper;

	@Override
	public List<IcsCheckRuleDetail> selectByMainId(String mainId) {
		return icsCheckRuleDetailMapper.selectByMainId(mainId);
	}
}
