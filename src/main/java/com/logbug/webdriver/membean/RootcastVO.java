package com.logbug.webdriver.membean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @author : lin.chen1
 * @version : 1.0.0.0
 * @date : Created at 2022/6/9
 */
@Data
@ToString()
public class RootcastVO {

    String title;
    String subtitle;
    String description;
    String content;
    String href;
    String key;

}
