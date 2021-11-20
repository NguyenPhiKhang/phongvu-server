package com.kltn.phongvuserver.utils;

import com.kltn.phongvuserver.models.Category;

import java.util.List;
import java.util.Set;

public class CategoryUtil {
    public static void getListIdCategory(Category category, Set<Integer> listId) {
        Set<Category> listCAT = category.getCategories();

        if (listCAT.size() > 0) {
            listCAT.forEach(c->{
                getListIdCategory(c, listId);
            });
        } else {
            listId.add(category.getId());
        }
    }

    public static void getListSubCategory(Category category, List<Category> listId) {
        Set<Category> listCAT = category.getCategories();

        if (listCAT.size() > 0) {
            listCAT.forEach(c->{
                getListSubCategory(c, listId);
            });
        } else {
            listId.add(category);
        }
    }
}
