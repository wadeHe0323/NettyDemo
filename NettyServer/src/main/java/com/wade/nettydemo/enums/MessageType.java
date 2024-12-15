package com.wade.nettydemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessageType {

    REALTIMEALARM("realTimeAlarm", 0),
    REQLOGINALARM("reqLoginAlarm", 1),
    ACKLOGINALARM("ackLoginAlarm", 2),
    REQSYNCALARMMsg("reqSyncAlarmMsg", 3),
    ACKSYNCALARMMsg("ackSyncAlarmMsg", 4),
    REQHEARTBEAT("reqHeartBeat", 8),
    ACKHEARTBEAT("ackHeartBeat", 9),
    CLOSECONNALARM("closeConnAlarm", 10);

    private String msgType;

    private int code;

}
