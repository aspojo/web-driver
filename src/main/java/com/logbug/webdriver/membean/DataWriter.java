package com.logbug.webdriver.membean;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logbug.webdriver.service.RootcastsService;
import com.logbug.webdriver.service.RootsService;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author : lin.chen1
 * @version : 1.0.0.0
 * @date : Created at 2022/6/10
 */
@Service
@Data
public class DataWriter {

    @Autowired
    RootsService rootsService;
    @Autowired
    RootcastsService rootcastsService;

    public void writeData(){
        JSONObject json=new JSONObject();
        json.put("roots",rootsService.lambdaQuery().list());
        json.put("rootcasts",rootcastsService.lambdaQuery().list());
        FileUtil.writeUtf8String(json.toString(),new File("data.json"));
    }
}
