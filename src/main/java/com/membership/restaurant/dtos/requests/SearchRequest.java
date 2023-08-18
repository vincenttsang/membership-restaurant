package com.membership.restaurant.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchRequest {
    /**
     * 地区
     */
    @NotEmpty
    private String area;
    /**
     * 离店日期（大于入住日期）
     */
    @NotNull
    private String endDate;
    /**
     * 查找酒店信息中有的关键字
     */
    private String keywords;
    /**
     * 入住日期
     */
    @NotNull
    private String startDate;
}