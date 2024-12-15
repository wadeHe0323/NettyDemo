package com.wade.nettydemo.client;

import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<User> USER = AttributeKey.newInstance("user");
}
