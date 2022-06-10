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
public class RootVO {
    String root;
    boolean hasAudio;
    boolean isCommon;
    String title;
    String description;
    String audio;
    String content;
    @ToString.Exclude()
    @JSONField(serialize = false)
    String href;

}
