package com.cyyz.shirocas.shiro;

import javafx.beans.property.SimpleSetProperty;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by Administrator on 2016/1/28.
 */
public class ShiroSession extends SimpleSession implements Serializable {

    private boolean isChanged;

    public ShiroSession(){
        super();
        this.setIsChanged(true);
    }

    public ShiroSession (String host){
        super(host);
        this.setIsChanged(true);
    }

    @Override
    public void setId(Serializable id){
       super.setId(id);
        this.setIsChanged(true);
    }

    @Override
    public void setStopTimestamp(Date stopTimeStamp){
        super.setStopTimestamp(stopTimeStamp);
        this.setIsChanged(true);
    }

    @Override
    public void setExpired(boolean expired){
        super.setExpired(expired);
        this.setIsChanged(true);
    }

    @Override
    public void setTimeout(long timeout){
        super.setTimeout(timeout);
        this.setIsChanged(true);
    }

    @Override
    public void setHost(String host){
        super.setHost(host);
        this.setIsChanged(true);
    }

    @Override
    public Object removeAttribute(Object key) {
        this.setChanged(true);
        return super.removeAttribute(key);
    }

    /**
     * 停止
     */
    @Override
    public void stop() {
        super.stop();
        this.setChanged(true);
    }

    /**
     * 设置过期
     */
    @Override
    protected void expire() {
        this.stop();
        this.setExpired(true);
    }


    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected boolean onEquals(SimpleSession ss) {
        return super.onEquals(ss);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }
}
