package com.logbug.webdriver.membean;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
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
    List<RootVO> roots;
    List<RootcastVO> rootcasts;

    public void writeData(){
        FileUtil.writeUtf8String(JSONObject.toJSONString(this),new File("data.json"));
    }
}
