package com.scohong.entity.junengchi;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/8/14 17:21
 * @Description: 分页，用于后台数据管理接口
 */
@Data
public class Pagination {
    private int total;
    private int pageSize;
    private int current;

    public Pagination() {
    }
    public Pagination(int pageSize, int current) {
        this.pageSize = pageSize;
        this.current = current;
    }
}
