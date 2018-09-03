package com.loanuncle.gm.juke.util;

import com.loanuncle.gm.juke.bean.BaseRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GM on 2018/8/25.
 * @description 把对象转换成map的工具类
 */

public class ObjectUtil {

    /**
     * 将类转化成map
     * */
    public static Map<String,Object> agreementObjectToMap(BaseRequest baseRequest){
        Map<String,Object> map = new HashMap<>();
        map.put("accountId",baseRequest.getAccountId());
        map.put("appVersion",baseRequest.getAppVersion());
        map.put("campaignChannel",baseRequest.getCampaignChannel());
        map.put("machineMac",baseRequest.getMachineMac());
        map.put("os",baseRequest.getOs());
        map.put("osVersion",baseRequest.getOsVersion());
        map.put("packetName",baseRequest.getPacketName());
        map.put("terminalModel",baseRequest.getTerminalModel());
        return map;
    }

    /**
     * 将类转化成map
     * （版本信息）
     * */
    public static Map<String,Object> baseInfoObjectToMap(BaseRequest baseRequest){
        Map<String,Object> map = new HashMap<>();
        map.put("appVersion",baseRequest.getAppVersion());
        map.put("campaignChannel",baseRequest.getCampaignChannel());
        map.put("os",baseRequest.getOs());
        map.put("packetName",baseRequest.getPacketName());
        return map;
    }

    /**
     * 判断字符串是否是数字
     * */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
