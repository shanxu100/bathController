package edu.scut.luluteam.bathcontroller.model;

/**
 * 这个做为所有显示数据类型的基类
 *
 * @author Guan
 */
public class BaseData {

    public static final int EVENT_BUS_MSG_FROM_SERIAL_PORT = 1;


    private String json;
    private int type;

    public BaseData(String json) {
        this.json = json;
    }

    public BaseData(String json, int type) {
        this.json = json;
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
