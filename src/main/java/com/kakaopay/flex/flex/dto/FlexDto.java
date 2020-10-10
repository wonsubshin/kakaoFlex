package com.kakaopay.flex.flex.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FlexDto implements Serializable {


     private static final long serialVersionUID = 7712997240858230399L;
     /**
      * 발급 대상인원수
      */
     private int people;

     /**
      * 발급 건에 대한 pk
      */
     private String token;

     /**
      * 지급될 총금액
      */
     private int totalMoney;

     /**
      * token 발급시간
      */
     private LocalDateTime createTime;

     /**
      * 발급자id
      */
     private String userId;

     /**
      * 방 pk
      */
     private String roomId;


}
