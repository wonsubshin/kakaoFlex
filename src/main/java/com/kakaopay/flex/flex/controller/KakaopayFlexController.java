package com.kakaopay.flex.flex.controller;

import com.kakaopay.flex.flex.dto.FlexRequestDto;
import com.kakaopay.flex.flex.dto.FlexResponseDto;
import com.kakaopay.flex.flex.service.FlexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/kakaopay", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class KakaopayFlexController {

    //헤더에 담겨올 요청자 식별값과, 대화방 id
    public static final String ROOMID="X-ROOM-ID";
    public static final String USERID="X-USER-ID";

    @Autowired
    FlexService flexService;

    /**
     * 뿌리기 정보를 생성한다.
     * @param roomId 대화방 식별값
     * @param userId 생성자 id
     * @param requestDto 생성 정보
     * @return
     */
    @PostMapping(path = "/payFlex")
    public FlexResponseDto insertPayFlex(
            @RequestHeader(ROOMID) String roomId,
            @RequestHeader(USERID) String userId,
            @RequestBody FlexRequestDto requestDto
            ){
        log.info("roomId={}, userId={}, body={}",roomId, userId, requestDto);
        FlexResponseDto responseDto  = flexService.insertFlexInfo(roomId,userId, requestDto);
        return responseDto;
    }


    /**
     * 발급된 토큰 정보를 가지고 뿌린돈을 받아간다.
     * @param token 발급된 token
     * @param roomId 대화방 식별값
     * @param userId 요청자 id
     * @return
     */
    @PostMapping(path = "/payFlex/{token}")
    public FlexResponseDto receivePayFlex(
            @PathVariable String token,
            @RequestHeader(ROOMID) String roomId,
            @RequestHeader(USERID) String userId

    ){
        log.info("roomId={},  token={}, userId={}",roomId ,token, userId);

        FlexResponseDto responseDto  = flexService.updateFlexDetailInfo(roomId, token, userId);
        return responseDto;
    }

    /**
     * token의 발급 이력을 조회한다.
     * 생성자와 조회요창자가 다를시 에러 발생.
     * @param token 조회대상 token
     * @param userId 조회 요청 id
     *
     * @return
     */
    @GetMapping(path = "/payFlex/{token}")
    public FlexResponseDto findPayFlexList(  @PathVariable String token,
                                             //swagger ui에서 해당값이 빠져서 오청되어 오류가 나고 있으므로 default로 추가
                                             @RequestHeader(value = "Content-type" ,defaultValue = MediaType.APPLICATION_JSON_VALUE) String media,
                                    @RequestHeader(USERID) String userId){

        log.info("token={}, userId={}" ,token, userId);

        FlexResponseDto responseDto  = flexService.findTokenHistory(token, userId);
        return responseDto;
    }
}
