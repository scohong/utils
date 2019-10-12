package com.scohong.utils.filter;

import com.scohong.entity.junengchi.UserSubmission;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/8/15 11:14
 * @Description:
 */
@Slf4j
public class ProgramTypeFilter {

    public static List<UserSubmission> typeFilter(String types, List<UserSubmission> userSubmissions) {
        log.info("types: "+ types);
        List<UserSubmission> resList = new ArrayList<>();
        for (UserSubmission userSub : userSubmissions
            ) {
            String type = userSub.getTypes();
            if (type != null && !type.isEmpty()) {
                if (types.indexOf(type) != -1) {
                    resList.add(userSub);
                }
            }
        }
        return resList;
    }
}
