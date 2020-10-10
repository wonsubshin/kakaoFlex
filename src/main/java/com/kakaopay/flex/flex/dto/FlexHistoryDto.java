package com.kakaopay.flex.flex.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlexHistoryDto implements Serializable {

    private static final long serialVersionUID = 5502273408711814288L;
    /**
     * token 발급시간
     */
    LocalDateTime createTime;

    /**
     * 지급 예정금액
     */
    long totalMoney;

    /**
     * 지급된 총금액
     */
    long takeMoney;

    /**
     * 지급된 금액에대한 상세 정보
     */
    List<FlexDetailDto> details;

}
