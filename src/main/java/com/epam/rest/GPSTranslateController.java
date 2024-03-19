package com.epam.rest;

import com.epam.entity.IcsCheckRule;
import com.epam.entity.IcsCheckRuleDetail;
import com.epam.entity.Location;
import com.epam.excel.ReadExcelChecklistRule;
import com.epam.service.IIcsCheckRuleDetailService;
import com.epam.service.IIcsCheckRuleService;
import com.epam.utils.GPSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 监督检查用语
 * @Author: nbacheng
 * @Date: 2023-09-11
 * @Version: V1.0
 */
@Api(tags = "地图坐标转换")
@RestController
@RequestMapping("/gps")
@Slf4j
public class GPSTranslateController {


    @ApiOperation(value = "translate", notes = "translate")
    @PostMapping(value = "/translate")
    public List<double[]> translate(@RequestBody List<double []> list)  {
//        List<Location> locationList = list.stream().map( o->  new Location(o[1],o[0]) ).collect(Collectors.toList());
        List<double[]> result= new ArrayList<>();
        list.stream().forEach(o->{
            double [] gcj =GPSUtil.bd09_To_Gcj02(o[1],o[0]);
            double [] gcj1 = new double[]{gcj[1],gcj[0]};
            result.add(gcj1);
        });
        return result;

    }
}
