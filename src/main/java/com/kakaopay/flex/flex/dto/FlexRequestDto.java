package com.kakaopay.flex.flex.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlexRequestDto implements Serializable {
    private static final long serialVersionUID = -8178499457781845304L;

    //뿌리기 api용
    /**
     * 지급할 돈
     */
    Integer money;

    /**
     * 지급 대상 인원
     */
    Integer people;

    //받기 api용
    /**
     * 발생 token
     */
    String token;

    /**
     * 지급요청 id
     */
    String userId;

}
