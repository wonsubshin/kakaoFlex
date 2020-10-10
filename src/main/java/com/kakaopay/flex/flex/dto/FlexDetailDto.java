package com.kakaopay.flex.flex.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FlexDetailDto  implements Serializable {


    private static final long serialVersionUID = -4871301344758706040L;
    /**
     * detail pk
     */
    private int id;

    /**
     * 발금된 token값 fk
     */
    private String token;

    /**
     * 발급 시간, 최초 생성시 null 이후 사용자가 발급받아가면 해달 날짜 입력
     */
    private LocalDateTime updateTime;

    /**
     * 지급될 돈
     */
    private long money;

    /**
     * 지급받아갈 userId
     */
    private String userId;
}
