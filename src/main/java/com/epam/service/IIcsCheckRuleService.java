package com.epam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 监督检查用语
 * @Author: nbacheng
 * @Date:   2023-09-11
 * @Version: V1.0
 */
public interface IIcsCheckRuleService extends IService<IcsCheckRule> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(IcsCheckRule icsCheckRule, List<IcsCheckRuleDetail> icsCheckRuleDetailList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(IcsCheckRule icsCheckRule, List<IcsCheckRuleDetail> icsCheckRuleDetailList);

	/**
	 * 删除一对多
	 */
	public void delMain(String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

}
