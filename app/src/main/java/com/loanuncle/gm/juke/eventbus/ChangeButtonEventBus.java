package com.loanuncle.gm.juke.eventbus;

/**
 * Created by GM on 2018/9/18.
 * @description 改变底部导航栏的eventbus
 */

public class ChangeButtonEventBus {

    /** 样式 */
    public int type = 1;

    public ChangeButtonEventBus(int type) {
        setType(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
