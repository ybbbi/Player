package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ybbbi.qqdemo.Utils.DbUtils;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.presenter.Interface.ContactIPresenter;
import com.ybbbi.qqdemo.view.Interface.ContactIView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ybbbi
 * 2020-01-12 16:28
 */
public class ContactPresenter implements ContactIPresenter {
    private ContactIView view;

    public ContactPresenter(ContactIView view) {
        this.view = view;
    }

    @Override
    public void initContact() {
        List<String> contacts = DbUtils.initContact(EMClient.getInstance().getCurrentUser());

        view.onInit(contacts);


        updateData();
    }

    private void updateData() {
        ThreadUtils.runOnChildThread(() -> {
            try {

                List<String> server_contact = EMClient.getInstance().contactManager().getAllContactsFromServer();
                Collections.sort(server_contact, new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                });
                ThreadUtils.runOnMainThread(() ->
                    view.onUpdate(true, server_contact, "")
                );
            } catch (HyphenateException e) {
                ThreadUtils.runOnMainThread(() ->
                    view.onUpdate(false, null, e.toString())

                );
                e.printStackTrace();
            }

        });
    }
}
