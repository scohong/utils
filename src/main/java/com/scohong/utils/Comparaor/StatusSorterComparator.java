package com.scohong.utils.Comparaor;


import com.scohong.entity.junengchi.UserSubmission;

import java.util.Comparator;

/**
 * @Author: scohong
 * @Date: 2019/8/15 10:32
 * @Description:
 */
public class StatusSorterComparator implements Comparator<UserSubmission> {
    private String sortType;
    private String param;

    public StatusSorterComparator(String param,String sortType) {
        this.param = param;
        this.sortType = sortType;
    }

    @Override
        public int compare(UserSubmission var1, UserSubmission var2) {
        if ("status".equals(param)) {
            if ("ascend".equals(sortType)) {
                return Integer.compare(var1.getStatus(), var2.getStatus());
            } else {
                return Integer.compare(var2.getStatus(), var1.getStatus());
            }
        } else if("uploadAt".equals(param)) {
            if ("ascend".equals(sortType)) {
                return var1.getUploadAt().before(var2.getUploadAt()) ? -1 :
                        (var1.getUploadAt().after(var2.getUploadAt()) ? 1 : 0);
            } else {
                return var1.getUploadAt().after(var2.getUploadAt()) ? -1 :
                        (var1.getUploadAt().before(var2.getUploadAt()) ? 1 : 0);
            }
        }
        return 0;
    }
}
