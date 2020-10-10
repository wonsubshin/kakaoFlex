package com.kakaopay.flex.flex.dao;

import com.kakaopay.flex.flex.dto.FlexDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FlexDetailMapper {


    /**
     * 사용되지 않은 정보 한건을 조회한다.
     * @param token 조회대상 token
     * @return
     * @throws Exception
     */
    FlexDetailDto selectUnuseOne(@Param("token")String token) throws Exception;

    /**
     * token과 사용자 정보를 가지고 발급된 내역을 조회
     * @param token 조회대상 token
     * @param userId 조회대상 id
     * @return
     * @throws Exception
     */
    FlexDetailDto selectOneByUserId(@Param("token") String token, @Param("userId") String userId) throws Exception;

    /**
     * 발행 이력을 조회한다. 본 조회해선 발급 완료된 건만을 조회한다.
     * @param token 조회 token
     * @return
     * @throws Exception
     */
    List<FlexDetailDto> findFlexHistory(@Param("token")String token) throws Exception;

    /**
     * token에 대하여 신규 발행정보를 저장한다.
     * @param dto 발행정보
     * @return
     * @throws Exception
     */
    int insertFlexDetailInfo(FlexDetailDto dto) throws Exception;


    /**
     * 발행된 정보를 업데이트 한다.(발급완료처리)
     * @param dto 발행정
     * @return
     * @throws Exception
     */
    int updateFlexDetailInfo(FlexDetailDto dto) throws Exception;


}
