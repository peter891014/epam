package com.epam.aliyun;

//import com.aliyun.oss.OSSClient;
//import com.jzzh.web.model.common.util.JSONUtils;
//import com.jzzh.web.model.common.util.StringUtils;
//import com.jzzh.web.server.controller.base.BaseController;
//import com.jzzh.web.server.utils.Result;
//import com.jzzh.web.server.utils.ResultConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/oss")
public class OssUploadFileController   {


	@Autowired
	private OssUtils ossUtils;

	@GetMapping(path = "/getPolicy")
//	@ApiOperation(value = "获取签名信息（获取文件签名）")
	public ResponseEntity<PolicyVo> getPolicy(String dir) {
		return new ResponseEntity(ossUtils.getPolicy(dir), HttpStatus.OK);
	}

}
