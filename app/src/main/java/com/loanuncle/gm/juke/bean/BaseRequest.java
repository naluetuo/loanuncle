package com.loanuncle.gm.juke.bean;

import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.constant.VersionConstant;

/**
 * Created by GM on 2018/8/24.
 * @description 请求基本类
 */

public class BaseRequest {


    /**
     * accountId : string
     * appId : string
     * appVersion : string
     * campaignChannel : string
     * gpsAddress : string
     * machineMac : string
     * os : string
     * osVersion : string
     * packetName : string
     * pageSize : 0
     * requestPage : 0
     * terminalModel : string
     * token : string
     * uuid : string
     */

    private String accountId;
    private String appId;
    private String appVersion;
    private String campaignChannel;
    private String gpsAddress;
    private String machineMac;
    private String os;
    private String osVersion;
    private String packetName;
    private String terminalModel;
    private String token;
    private String uuid;

    public BaseRequest() {
        this.accountId = UserConstant.ACCOUNT_ID;
        this.appVersion = VersionConstant.VERSION_NAME;
        this.campaignChannel = VersionConstant.CHANNEL;
        this.machineMac = VersionConstant.IMMEI;
        this.os = VersionConstant.OS_TYPE;
        this.osVersion = VersionConstant.ANDROID_OSCODE;
        this.packetName = VersionConstant.PACKAGE_NAME;
        this.terminalModel = "";
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCampaignChannel() {
        return campaignChannel;
    }

    public void setCampaignChannel(String campaignChannel) {
        this.campaignChannel = campaignChannel;
    }

    public String getGpsAddress() {
        return gpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        this.gpsAddress = gpsAddress;
    }

    public String getMachineMac() {
        return machineMac;
    }

    public void setMachineMac(String machineMac) {
        this.machineMac = machineMac;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPacketName() {
        return packetName;
    }

    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }

    public String getTerminalModel() {
        return terminalModel;
    }

    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
