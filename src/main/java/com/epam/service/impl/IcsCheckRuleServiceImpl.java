package com.epam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;
import com.epam.mapper.IcsCheckRuleDetailMapper;
import com.epam.mapper.IcsCheckRuleMapper;
import com.epam.service.IIcsCheckRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 监督检查用语
 * @Author: nbacheng
 * @Date:   2023-09-11
 * @Version: V1.0
 */
@Service
public class IcsCheckRuleServiceImpl extends ServiceImpl<IcsCheckRuleMapper, IcsCheckRule> implements IIcsCheckRuleService {

	@Autowired
	private IcsCheckRuleMapper icsCheckRuleMapper;
	@Autowired
	private IcsCheckRuleDetailMapper icsCheckRuleDetailMapper;

	@Override
	@Transactional
	public void saveMain(IcsCheckRule icsCheckRule, List<IcsCheckRuleDetail> icsCheckRuleDetailList) {
		icsCheckRuleMapper.insert(icsCheckRule);
		if(icsCheckRuleDetailList!=null && icsCheckRuleDetailList.size()>0) {
			for(IcsCheckRuleDetail entity:icsCheckRuleDetailList) {
				//外键设置
				entity.setCheckRuleId(icsCheckRule.getId());
				icsCheckRuleDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(IcsCheckRule icsCheckRule,List<IcsCheckRuleDetail> icsCheckRuleDetailList) {
		icsCheckRuleMapper.updateById(icsCheckRule);

		//1.先删除子表数据
		icsCheckRuleDetailMapper.deleteByMainId(icsCheckRule.getId().toString());

		//2.子表数据重新插入
		if(icsCheckRuleDetailList!=null && icsCheckRuleDetailList.size()>0) {
			for(IcsCheckRuleDetail entity:icsCheckRuleDetailList) {
				//外键设置
				entity.setCheckRuleId(icsCheckRule.getId());
				icsCheckRuleDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		icsCheckRuleDetailMapper.deleteByMainId(id);
		icsCheckRuleMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			icsCheckRuleDetailMapper.deleteByMainId(id.toString());
			icsCheckRuleMapper.deleteById(id);
		}
	}

}
