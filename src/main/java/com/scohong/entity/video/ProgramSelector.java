package com.scohong.entity.video;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
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
