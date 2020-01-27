package com.ybbbi.qqdemo.Utils;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import java.util.LinkedList;

/**
 * ybbbi
 * 2020-01-27 12:59
 */
public class ActivityManager {


        private static ActivityManager instance;
        private static LinkedList<Activity> activityList;



        public ActivityManager() {
            if (activityList == null) {
                activityList = new LinkedList<Activity>();
            }

        }
        public LinkedList<Activity> getList(){
            if(activityList!=null){
                return activityList;
            }
            else{
                return null;
            }
        }

        public static ActivityManager getInstance() {
            if (instance == null) {
                instance = new ActivityManager();
            }
            return instance;
        }

        public void addActivity(Activity acy) {
            activityList.add(acy);
        }
        public void remoActivity(Activity acy){
            activityList.remove(acy);
        }

}
