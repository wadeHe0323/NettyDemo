package com.wade.nettydemo.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Message
public class User{

    private String userId;

    private String account;

    private String password;

}
