package cn.xz.property.util;


import cn.xz.property.entity.Admin;

public class UserHolder {
    private static final ThreadLocal<Long> tl = new ThreadLocal<>();

    public static void saveUser(Long user){
        tl.set(user);
    }
    public static Long getUser(){
        return tl.get();
    }
    public static void removeUser(){
        tl.remove();
    }
}
