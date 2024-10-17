package org.zerock.api1014.member.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerock.api1014.member.dto.MemberDTO;
import org.zerock.api1014.member.dto.TokenRequestDTO;
import org.zerock.api1014.member.dto.TokenResponseDTO;
import org.zerock.api1014.member.exception.MemberExceptions;
import org.zerock.api1014.member.service.MemberService;
import org.zerock.api1014.security.util.JWTUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JWTUtil jWTUtil;

    @Value("${org.zerock.accessTime}")
    private int accessTime;

    @Value("${org.zerock.accessTime}")
    private int refreshTime;

    @PostMapping("makeToken")
    // JSON사용하니까 RequestBody
    // ResponseEnity 응답메시지 이용 위해
    public ResponseEntity<TokenResponseDTO> makeToken(@RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("Making token");
        log.info("------------------------");

        MemberDTO memberDTO = memberService.authenticate(
                tokenRequestDTO.getEmail(),
                tokenRequestDTO.getPw());

        log.info(memberDTO);
        Map<String, Object> claimMap = Map.of(
                "email",memberDTO.getEmail(),
                "role", memberDTO.getRole() );

        String accessToken = jWTUtil.createToken(claimMap,accessTime);
        String refreshToken = jWTUtil.createToken(claimMap,refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setEmail(memberDTO.getEmail());

        return ResponseEntity.ok(tokenResponseDTO);

    }

    //MediaType.~~~~~value 일반적인 메소트 타입만 선언할 수있어 뜻
    //produces 한 줄 추가 한 이유 JSON으로 나온다고 보여줄려고
    @PostMapping(value="refreshToken",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String accessToken,
            String refreshToken) {

        //** 만일 accessToken이 없다면 혹은 RefreshToken이 없다면
        if(accessToken == null || refreshToken == null) {
            throw MemberExceptions.TOKEN_NOT_ENOUGH.get();
        }

        //** accessToken  Bearer (7) 잘라낼때 문제가 발생한다면
        if(accessToken.startsWith("Bearer ")) {
            throw MemberExceptions.ACCESSTOKEN_TOO_SHORT.get();
        }
        String accessTokenStr = accessToken.substring("Bearer ".length());
        //**AccessToken의 만료 여부 체크
        try{
            Map<String, Object> payload = jWTUtil.validateToken(accessTokenStr);

            // 바로 위의 코드가 예외가 발생한 경우 아래 코드는 실행되지 않음
            String email = payload.get("email").toString();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setEmail(email);
            tokenResponseDTO.setRefreshToken(refreshToken);

            return ResponseEntity.ok((tokenResponseDTO));

        }catch(ExpiredJwtException ex){
            //** 정상적으로 만료된 경우

            //** 만일 Refresh Toekn이 마저 만료되었다면?
            //구글같은 곳에서 200에러로 출력되기도 함, 만료되도 예외라고 생각은 안해서
            //하지만 200으로 던지면 리엑트상에서 보면 햇갈리기 쉽다 -> 여기선 401에러로 표시
        }

        return null;
    }

}