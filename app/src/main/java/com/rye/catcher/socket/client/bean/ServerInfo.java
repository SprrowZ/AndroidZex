package com.rye.catcher.socket.client.bean;

/**
 * Created by 18041at 2019/4/6
 * Function: 服务端信息
 */
public class ServerInfo {
    private String sn;
    private int port;
    private String address;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "sn='" + sn + '\'' +
                ", port=" + port +
                ", address='" + address + '\'' +
                '}';
    }

    public ServerInfo(String sn, int port, String address) {
        this.sn = sn;
        this.port = port;
        this.address = address;
    }
}
