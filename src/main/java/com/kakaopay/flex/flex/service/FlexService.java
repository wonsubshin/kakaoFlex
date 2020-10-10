package com.kakaopay.flex.flex.service;

import com.kakaopay.flex.flex.dao.FlexDetailMapper;
import com.kakaopay.flex.flex.dao.FlexMapper;
import com.kakaopay.flex.flex.dto.*;
import com.kakaopay.flex.flex.enums.Codes;
import com.kakaopay.flex.flex.util.FlexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FlexService {

    @Autowired
    FlexMapper flexMapper;

    @Autowired
    FlexDetailMapper flexDetailMapper;

    FlexUtils utils = new FlexUtils();

    /**
     * 최초 뿌리기를 호출했을때 사용되는 함수 이시점에 뿌릴금액을 나누어서 먼저 각정보를 테이블에 저장한후
     * 이후 받기시 각각의 정보를 챙겨 넣는다.
     * @param roomId
     * @param userId
     * @param requestDto
     * @return
     */
    @Transactional
    public FlexResponseDto insertFlexInfo(String roomId, String userId, FlexRequestDto requestDto){

        FlexResponseDto result = new FlexResponseDto();

        String token = utils.createToken(3);

        try {

            FlexDto insertInfo = new FlexDto();

            //flex master 분배자의 정보 총금액 정보를 저장한다.
            insertInfo.setCreateTime(LocalDateTime.now());
            insertInfo.setUserId(userId);
            insertInfo.setPeople(requestDto.getPeople());
            insertInfo.setRoomId(roomId);
            insertInfo.setTotalMoney(requestDto.getMoney());
            insertInfo.setToken(token);

              flexMapper.insertFlexInfo(insertInfo);

             //저장된후 하위 테이블에 분배할 사람수대로 나뉘어진 금액을 저장한다.
          long[] dividedMoneny = divideMoney(requestDto.getMoney(), requestDto.getPeople());

          for(long divided : dividedMoneny){
              FlexDetailDto detailDto = new FlexDetailDto();

              detailDto.setMoney(divided);
              detailDto.setToken(token);

              flexDetailMapper.insertFlexDetailInfo(detailDto);
          }
        }catch (Exception e){
        e.printStackTrace();
            result.setCode(Codes.E003.getCode());
            result.setCont(Codes.E003.getDesc());
            return result;
        }
        result.setToken(token);
        result.setCode(Codes.S001.getCode());
        result.setCont(Codes.S001.getDesc());
     return  result;
    }

    /**
     * 발급받은 token에서 미 사용된 금액을 할당받는다.
     * 1. token 발급자는 자신이 대상이 될수 없다.
     * 2. 이미한번 발급 받은 사람은 중복발급 되지 않는다.
     * 3. roomId, token은 페어로 일치해야하며 하나라도 틀릴시 오류응답
     *
     * @param roomId 방의 id
     * @param token 발급된 토큰값
     * @param userId 발급요청 id
     * @return
     */
    @Transactional
    public FlexResponseDto updateFlexDetailInfo(String roomId, String token, String userId){
        FlexResponseDto result = new FlexResponseDto();

        FlexDetailDto detailDto;
        FlexDto flexDto;


        try {
            flexDto = flexMapper.selectInfo(token);

            //유효하지 않은 토큰인 경우 예외처리
            if(flexDto == null){
                result.setCode(Codes.E004.getCode());
                result.setCont(Codes.E004.getDesc());

                return result;
            }

            //소속된 방인원이 아니라면 발급대상이 아니므로 예외처리한다.
            if(!flexDto.getRoomId().equals(roomId)){
                result.setCode(Codes.E006.getCode());
                result.setCont(Codes.E006.getDesc());

                return result;
            }

            //쿠폰발급자인경우 자신이 발급한 쿠폰을 발급 받을수 없으므로 예외처리한다.

            if(flexDto.getUserId().equals(userId)){
                result.setCode(Codes.E005.getCode());
                result.setCont(Codes.E005.getDesc());

                return result;
            }

             //발급된지 10분 이후의 토큰이라면 유효하지 않은 토큰이므로 이곳에서 에러코드로 리턴한다.
             if(flexDto.getCreateTime().isBefore(LocalDateTime.now().minusMinutes(10))){
                result.setCode(Codes.E002.getCode());
                result.setCont(Codes.E002.getDesc());

                return result;
             }

            detailDto = flexDetailMapper.selectUnuseOne(token);


        }catch (Exception e){
            e.printStackTrace();
            result.setCode(Codes.E004.getCode());
            result.setCont(Codes.E004.getDesc());

            return result;
        }

        try {

            if(Strings.isNotEmpty(detailDto.getToken())){



                //이미 발급 받은 경우 쿠폰을 발급 받을수 없다
                FlexDetailDto infoDto = flexDetailMapper.selectOneByUserId(token, userId);

                if(infoDto != null){
                    result.setCode(Codes.E001.getCode());
                    result.setCont(Codes.E001.getDesc());

                    return result;
                }

                detailDto.setUpdateTime(LocalDateTime.now());
                detailDto.setUserId(userId);

                flexDetailMapper.updateFlexDetailInfo(detailDto);
            }



        }catch (Exception e){
            e.printStackTrace();
            result.setCode(Codes.E003.getCode());
            result.setCont(Codes.E003.getDesc());
            return result;
        }

        result.setCode(Codes.S001.getCode());
        result.setCont(Codes.S001.getDesc());
        result.setMoney(detailDto.getMoney());
        result.setToken(detailDto.getToken());
        return result;

    }


    public FlexResponseDto findTokenHistory(String token, String userId){
        FlexResponseDto result = new FlexResponseDto();

        List<FlexDetailDto> detailDtos;
        FlexDto flexDto;
        FlexHistoryDto historyDto = new FlexHistoryDto();


        try {
            flexDto = flexMapper.selectInfo(token);

            //유효하지 않은 토큰인 경우 예외처리
            if(flexDto == null){
                result.setCode(Codes.E004.getCode());
                result.setCont(Codes.E004.getDesc());

                return result;
            }

            //쿠폰발급자가 아닌 경우 조회 불가하므로 예외처리한다.

            if(!flexDto.getUserId().equals(userId)){
                result.setCode(Codes.E007.getCode());
                result.setCont(Codes.E007.getDesc());

                return result;
            }

            //발급된지 7일 이후의 토큰이라면 유효하지 않은 토큰이므로 이곳에서 에러코드로 리턴한다.
            if(flexDto.getCreateTime().isBefore(LocalDateTime.now().minusDays(7))){
                result.setCode(Codes.E002.getCode());
                result.setCont(Codes.E002.getDesc());

                return result;
            }


            historyDto.setTotalMoney(flexDto.getTotalMoney());
            historyDto.setCreateTime(flexDto.getCreateTime());

            detailDtos = flexDetailMapper.findFlexHistory(token);


        }catch (Exception e){
            e.printStackTrace();
            result.setCode(Codes.E004.getCode());
            result.setCont(Codes.E004.getDesc());

            return result;
        }


        try{

            long takeMoney =0l;

            for(FlexDetailDto detailDto : detailDtos){
                takeMoney += detailDto.getMoney();
            }


            historyDto.setTakeMoney(takeMoney);
            historyDto.setDetails(detailDtos);
        }catch (Exception e){

        }

        result.setCode(Codes.S001.getCode());
        result.setCont(Codes.S001.getDesc());
        result.setHistory(historyDto);
        return result;
    }
    private long[] divideMoney(long money, int people){
        long[] dividedMon = new long[people];

        long max = RandomUtils.nextLong(money/people, money/people*2);

        for(int i=0; i<people-1; i++){
            dividedMon[i] = RandomUtils.nextLong(1, Math.min(max, money));
            money -= dividedMon[i];
        }

        dividedMon[people-1] = money;

        return dividedMon;
    }

}
