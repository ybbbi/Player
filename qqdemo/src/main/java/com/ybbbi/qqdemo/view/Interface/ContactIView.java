package com.ybbbi.qqdemo.view.Interface;

import java.util.List;

/**
 * ybbbi
 * 2020-01-12 16:30
 */
public interface ContactIView {
    void onInit(List<String> contacts);
    void onUpdate(boolean isSuccess,List<String> contacts,String error);
    void onDelete(boolean isSuccess, String s);
}
