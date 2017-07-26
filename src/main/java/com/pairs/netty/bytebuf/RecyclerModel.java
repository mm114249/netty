package com.pairs.netty.bytebuf;

import io.netty.util.Recycler;

/**
 * Created on 2017年07月26日9:46
 * <p>
 * Title:[]
 * </p >
 * <p>
 * Description :[]
 * </p >
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class RecyclerModel {
    private int index;
    private Recycler.Handle handle;

    public RecyclerModel(int index, Recycler.Handle handle) {
        this.index = index;
        this.handle = handle;
    }

    public void recycle(){
        handle.recycle(this);
    }

    @Override
    public String toString() {
        return "RecyclerModel{" +
                "index=" + index +
                '}';
    }
}
