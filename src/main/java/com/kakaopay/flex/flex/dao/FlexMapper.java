package com.kakaopay.flex.flex.dao;

import com.kakaopay.flex.flex.dto.FlexDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface FlexMapper {
    /**
     * token 정보를 기준으로 해당 정보 조회
     * @param token 조회 token
     * @return
     * @throws Exception
     */
     FlexDto selectInfo(@Param("token") String token) throws Exception;


    /**
     * token 정보를 신규로 저장한다.
     * @param dto
     * @return
     * @throws Exception
     */
     int insertFlexInfo(FlexDto dto) throws Exception;
}
