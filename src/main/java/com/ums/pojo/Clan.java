package com.ums.pojo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Clan {
    private Integer id;
    private String name;
    private Integer parentId;
    private String spouse;
    private String spouseGrave;
    private String address;
    private String briefIntroduction;
    private String fullInfo;
    private Integer gender;
    private Integer generation;
    private String graveAddress;
    private Integer isDeceased;
    private Integer isSpouseDeceased;
    private BigInteger createTime;
    private BigInteger updateTime;
}
