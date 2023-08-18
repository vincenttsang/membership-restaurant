package com.membership.restaurant.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    /**
     * 身份证号
     */
    @NotNull
    private String bookerId;
    /**
     * 姓名
     */
    @NotNull
    private String bookerName;
    /**
     * 电话号码
     */
    @NotNull
    private String bookerTel;
    /**
     * 退房时间
     */
    @NotNull
    private String endDate;
    /**
     * 酒店编号（从酒店列表中获取）
     */
    private int hotelId;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 房间名
     */
    @NotNull
    private String roomName;
    /**
     * 订房数量
     */
    private int roomNum;
    /**
     * 房型
     */
    @NotNull
    private String roomType;
    /**
     * 用户session，登录获得的session_id
     */
    @NotNull
    private String sessionId;
    /**
     * 开始时间
     */
    @NotNull
    private String startDate;
    /**
     * 入住数量
     */
    private int userNum;
}

