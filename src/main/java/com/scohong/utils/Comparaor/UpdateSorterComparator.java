package com.scohong.utils.Comparaor;


import com.scohong.entity.junengchi.UserSubmission;

import java.util.Comparator;

/**
 * @Author: scohong
 * @Date: 2019/8/15 10:35
 * @Description:
 */
public class UpdateSorterComparator implements Comparator<UserSubmission> {


    @Override
    public int compare(UserSubmission o1, UserSubmission o2) {
        if (o1.getUploadAt().before(o2.getUploadAt()) ) {
            return -1;
        } else if (o1.getUploadAt().after(o2.getUploadAt())) {
            return 1;
        } else {
            return 0;
        }
    }
}
