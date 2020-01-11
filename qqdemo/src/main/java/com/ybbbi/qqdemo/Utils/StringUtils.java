package com.ybbbi.qqdemo.Utils;

public class StringUtils {
    /**
     * 判断和理性，开头为字母，长度区间为6-18
     * @param username
     * @return
     */
    public static boolean CheckUserName(String username){
        //先判断是否为空

        //再判断和理性
        if(username.isEmpty()){
            return false;
        }else{
            return username.matches("^\\w{6,17}$");
        }
//      return username.isEmpty()?false:username.matches("^[a-zA-Z]\\w(5,17)$");
    }
    /**
     * 判断和理性,首字母大写，任意字符长度区间为6-18
     * @param pwd
     * @return
     */
    public static boolean CheckUserPwd(String pwd){
        //先判断是否为空
        //再判断和理性
        return pwd.isEmpty()?false:pwd.matches("^[A-Z]\\w{5,18}$");
    }
}
