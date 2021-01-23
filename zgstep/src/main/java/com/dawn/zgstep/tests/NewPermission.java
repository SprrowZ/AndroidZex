package com.dawn.zgstep.tests;

/**
 * Create by rye
 * at 2021/1/20
 *
 * @description: 位运算 控制权限示例 可以作为通用类！
 */
public class NewPermission {
    //通过二进制位来判断权限，比如1111，说明所有权限都有；1110，只有除了查询之外的权限；
    //1010,只有删除和插入权限，so on...

    public static final int ALLOW_SELECT = 1 << 0; // 0001
    public static final int ALLOW_INSERT = 1 << 1; // 0010
    public static final int ALLOW_UPDATE = 1 << 2; // 0100
    public static final int ALLOW_DELETE = 1 << 3; // 1000
    //存储目前权限状态
    private int flag;

    public int getCurrentPermission(){
        return flag;
    }

    public void resetPermission(int permission) {
        flag = permission;
    }

    //添加一项或者多项权限
    public void enable(int permission) {
        flag |= permission;
    }

    //移除一项或者多项权限
    public void disable(int permission) {
        flag &= ~permission;
    }

    //是否有用某项权限
    public boolean hasPermission(int permission) {
        return (flag & permission) == permission;
    }

    //是否禁用了某些权限
    public boolean hasNotPermission(int permission) {
        return (flag & permission) == 0;
    }

    //是否禁拥有某些权限
    public boolean isOnlyAllow(int permission) {
        return flag == permission;
    }


}
