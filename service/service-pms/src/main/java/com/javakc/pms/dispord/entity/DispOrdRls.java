package com.javakc.pms.dispord.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pms_disp_ord_rls")
public class DispOrdRls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置数据库 ID自增长
    private int id;

    @Column(name = "order_num")
    private String orderNum;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "spec_type")
    private int specType;

    @Column(name = "priority")
    private int priority;

    @Column(name = "order_desc")
    private String orderDesc;

    @Column(name = "transfer")
    private String transfer;

    @Column(name = "transmitter")
    private String transmitter;

    @Column(name = "release_time")
    private Date releaseTime;

    @Column(name = "is_release")
    private int isRelease;

    @Column(name = "order_status")
    private int orderStatus;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_create", nullable = false, updatable = false)
    private Date gmtCreate;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_modified", nullable = false, insertable = false)
    private Date gmtModified;

    @Override
    public String toString() {
        return "DispOrdRls{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", orderName='" + orderName + '\'' +
                ", specType=" + specType +
                ", priority=" + priority +
                ", orderDesc='" + orderDesc + '\'' +
                ", transfer='" + transfer + '\'' +
                ", transmitter='" + transmitter + '\'' +
                ", releaseTime=" + releaseTime +
                ", isRelease=" + isRelease +
                ", orderStatus=" + orderStatus +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}