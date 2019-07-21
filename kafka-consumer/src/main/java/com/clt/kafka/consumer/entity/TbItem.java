package com.clt.kafka.consumer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_item")
public class TbItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sell_point")
    private String sellPoint;

    @Column(name = "price")
    private Long price;

    @Column(name = "num")
    private Integer num;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "image")
    private String image;

    @Column(name = "cid")
    private Long cid;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

}
