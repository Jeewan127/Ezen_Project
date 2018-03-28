package com.lwan.rkswl.ezen_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by rkswl on 2018-02-24.
 */
@Data
@NoArgsConstructor
@ToString
public class user {
    private String ID;
    private String USER_ID;
    private String USER_PASSWORD;
    private String USER_NAME;
    private String BIRTHDAY;
    private String GENDER;
    private String P_NUMBER;
    private String OP_NUMBER;
    private String PROFILE;
    private String LOCK_PASSWORD;
    private String FIRST_DAY;
}
