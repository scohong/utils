package com.scohong.entity.video;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 获取节目目录，有二级目录
 * @Author: scohong
 * @Date: 2019/9/9 20:50
 * @Description:
 */
@Data
public class ProgramSelector {
    private String value;
    private String label;
    @Builder.Default
    private boolean isLeaf = false;
    List<ProgramSelector> children;

    public ProgramSelector(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
