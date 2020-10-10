package com.kakaopay.flex.flex.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 현재 프로젝트에서 사용될 유틸성 함수들이 모여있는곳
 */
public class FlexUtils {

    /**
     * 입력된 자리수만큼 토큰을 발금한다.
     * @param length
     * @return
     */
     public String createToken(int length){
         return RandomStringUtils.randomAlphabetic(length);
     }
}
