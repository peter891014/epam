package com.epam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epam.entity.IcsCheckRuleDetail;

import java.util.List;

/**
 * @Description: 监督检查用语存在问题类别可选项
 * @Author: nbacheng
 * @Date:   2023-09-11
 * @Version: V1.0
 */
public interface IIcsCheckRuleDetailService extends IService<IcsCheckRuleDetail> {

	public List<IcsCheckRuleDetail> selectByMainId(String mainId);
}
