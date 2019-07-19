package com.clt.kafka.consumer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "su_user_info", indexes = {@Index(name = "idx_game_id_user_id_chnl_id", columnList = "game_id,user_id,chnl_id", unique = true)})
public class SuUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chnl_id")
    private String chnlId;

    @Column(name = "chnl_user_id")
    private String chnlUserId;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "imei")
    private String imei;

    @Column(name = "mac")
    private String mac;

    @Column(name = "model")
    private String model;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "act_time")
    private Date actTime;

    @Column(name = "reg_time")
    private Date regTime;

    @Column(name = "reg_ip")
    private String regIp;

    @Column(name = "first_login_time")
    private Date firstLoginTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "login_count")
    private Integer loginCount;

    @Column(name = "first_charge_time")
    private Date firstChargeTime;

    @Column(name = "act_2")
    private Boolean act2;

    @Column(name = "act_3")
    private Boolean act3;

    @Column(name = "act_4")
    private Boolean act4;

    @Column(name = "act_5")
    private Boolean act5;

    @Column(name = "act_6")
    private Boolean act6;

    @Column(name = "act_7")
    private Boolean act7;

    @Column(name = "act_15")
    private Boolean act15;

    @Column(name = "act_30")
    private Boolean act30;

    @Column(name = "mtime")
    private Date mtime;

}