package com.epam.rest;

import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;
import com.epam.excel.ReadExcelChecklistRule;
import com.epam.service.IIcsCheckRuleDetailService;
import com.epam.service.IIcsCheckRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @Description: 监督检查用语
 * @Author: nbacheng
 * @Date: 2023-09-11
 * @Version: V1.0
 */
@Api(tags = "监督检查用语")
@RestController
@RequestMapping("/ics/icsCheckRule")
@Slf4j
public class IcsCheckRuleController {
    @Autowired
    private IIcsCheckRuleService icsCheckRuleService;
    @Autowired
    private IIcsCheckRuleDetailService icsCheckRuleDetailService;


    @ApiOperation(value = "根据excel文件初始化数据", notes = "根据excel文件初始化数据")
    @GetMapping(value = "/init")
    public void init(Integer sheet, String filePath) throws FileNotFoundException {
        if (StringUtils.isEmpty(filePath)) {
            filePath = "C:\\Users\\ASUS\\Desktop\\监督检查用语.xlsx";
        }
        Map map = ReadExcelChecklistRule.getExcelInfo1( sheet,filePath);
        List<IcsCheckRule> list = (List<IcsCheckRule>) map.get("checkRule");
        List<IcsCheckRuleDetail> detail = (List<IcsCheckRuleDetail>) map.get("detail");
        icsCheckRuleService.saveBatch(list);
        icsCheckRuleDetailService.saveBatch(detail);
    }
}
