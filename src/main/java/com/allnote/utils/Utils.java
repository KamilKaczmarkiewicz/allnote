package com.allnote.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Sort.Order> createSortOrder(List<String> sortList) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction = Sort.Direction.fromString(sortList.get(sortList.size() - 1));
        sortList.remove(sortList.size() - 1);
        for (String sort : sortList) {
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

}
