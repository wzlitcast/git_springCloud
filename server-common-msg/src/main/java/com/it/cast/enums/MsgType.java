package com.it.cast.enums;

public enum MsgType {

    SEND_SMS("s1","SendSms"),
    SEND_BATCH_SMS("s2","SendBatchSms"),
    QUERY_SEND_DETAILS("s3","QuerySendDetails"),;

    private final String key;
    private final String value;

    MsgType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(MsgType temp: MsgType.values()){
            if(temp.getKey().equals(key)){
                return temp.getValue();
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
