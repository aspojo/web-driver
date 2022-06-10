package com.logbug.webdriver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName roots
 */
@TableName(value ="roots")
@Data
public class Roots implements Serializable {
    /**
     * 
     */
    private String root;

    /**
     * 
     */
    private Boolean hasAudio;

    /**
     * 
     */
    private Boolean common;

    /**
     * 
     */
    private String title;

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
    @TableField("_key")
    private String key;

    /**
     * 
     */
    private Boolean related;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}