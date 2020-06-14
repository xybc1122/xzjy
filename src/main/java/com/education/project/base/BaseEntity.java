package com.education.project.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BaseEntity {
    @TableField(exist = false)
    private Integer current = 1;
    @TableField(exist = false)
    private Integer offset = 10;
}
