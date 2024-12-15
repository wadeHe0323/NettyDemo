import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {

    REALTIMEALRAM("realTimeAlarm", 0),
    REQLOGINALARM("reqLoginAlram", 1),
    ACKLOGINALARM("ackLoginAlarm", 2),
    REQSYNCALARMMSG("reqSyncAlarmMsg", 3),
    ACKSYNCALARMMSG("ackSyncAlarmMsg", 4),
    REQHEARTBEAT("reqHeartBeat", 8),
    ACKHEARTBEAT("ackHeartBeat", 9),
    CLOSECONNALARM("closeConnAlarm", 10);



    private String msgType;

    private int code;
}
