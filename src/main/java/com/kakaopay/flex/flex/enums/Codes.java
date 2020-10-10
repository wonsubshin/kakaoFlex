package com.kakaopay.flex.flex.enums;

public enum Codes {
    S001("200", "정상 처리"),
    E001("001", "중복 뽑기 요청"),
    E002("002", "유효시간 초과"),
    E003("003", "TOKEN 생성 실패"),
    E004("004", "TOKEN 정보 조회 실패"),
    E005("005", "자신이 발급한 쿠폰은 받을수 없습니다."),
    E006("006", "대화방에 소속되어있지 않습니다."),
    E007("007", "내역 조회는 발급자만 가능합니다.");


    public final String code;
    public final String desc;

    Codes(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return  desc;
    }
}
