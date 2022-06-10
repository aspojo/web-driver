package com.logbug.webdriver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName rootcasts
 */
@TableName(value ="rootcasts")
@Data
public class Rootcasts implements Serializable {
    /**
     * 
     */
    @TableId("_key")
    private String key;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String subtitle;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String href;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}