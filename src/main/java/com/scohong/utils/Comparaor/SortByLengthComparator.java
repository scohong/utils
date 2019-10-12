package com.scohong.utils.Comparaor;

import com.scohong.entity.junengchi.Shop;

import java.util.Comparator;

/**
 * @Author: scohong
 * @Date: 2019/8/8 20:45
 * @Description:
 */
public class SortByLengthComparator implements Comparator<Shop> {

    @Override
    public int compare(Shop var1, Shop var2) {
        if (var1.getShopName().length() > var2.getShopName().length()) {
            return 1;
        } else if (var1.getShopName().length() == var2.getShopName().length()) {
            return 0;
        } else {
            return -1;
        }
    }


}

