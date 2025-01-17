import io.netty.channel.Channel;

public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

    public static void logOut(Channel channel) {
        channel.attr(Attributes.LOGIN).set(null);
    }
}
