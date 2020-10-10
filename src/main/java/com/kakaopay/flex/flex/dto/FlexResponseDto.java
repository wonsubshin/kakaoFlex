package com.kakaopay.flex.flex.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * 요청에 대한 응답 값을 담는곳
 */
@Data
public class FlexResponseDto implements Serializable {
    private static final long serialVersionUID = -6622128545831881668L;

    //공통 사용 컬럼
    /**
     * 토큰 생성값
     */
    private String token;

    /**
     * 응답 코드
     */
    private String code;

    /**
     * 응답 내용
     */
    private String cont;

    //받기시 사용 컬럼
    /**
     * 발급된 돈
     */
    private long money;

    //발급 내역 조회시 사용 컬럼
    /**
     * 발급내역 조회 dto
     */
    private FlexHistoryDto history;




}
