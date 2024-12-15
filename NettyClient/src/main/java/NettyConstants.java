public class NettyConstants {

    public static int LOCAL_PORT = 53535;
    public static String LOCAL_IP = "127.0.0.1";

    public static final String ACCOUNT = "123456";
    public static final String PASSWORD = "123456";

    public static final String ACK_LOGIN_ALARM = "ackLoginAlarm";
    public static final String ACK_LOGIN_ALARM_RESULT_OK = Boolean.TRUE + ACK_LOGIN_ALARM + "result=succ;redDesc=null";
    public static final String ACK_LOGIN_ALARM_RESULT_FAIL = Boolean.TRUE + ACK_LOGIN_ALARM + "result=fail;redDesc=username-or-key-error";
    public static final String ACK_HEART_BEAT = "ackHeartBeat;";
    public static final String REQ_LOGIN_ALARM = "reqLoginAlarm";
    public static final String REQ_HEART_BEAT = "reqHeartBeat";

    public static final String ALARM_LOGIN_REQ_SUCCESS = Boolean.TRUE + REQ_LOGIN_ALARM + "user=" + ACCOUNT + ";key=" + PASSWORD + ";type=msg";
}
