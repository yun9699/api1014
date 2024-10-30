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
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Value("${org.zerock.accessTime}")
    private int accessTime;

    @Value("${org.zerock.refreshTime}")
    private int refreshTime;

    @Value("${org.zerock.alwaysNew}")
    private boolean alwaysNew;

    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("makeToken")
    public ResponseEntity<TokenResponseDTO> makeToken(
            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("===============================");
        log.info("Make Token");

        MemberDTO memberDTO =
                memberService.authenticate(tokenRequestDTO.getEmail(), tokenRequestDTO.getPw());

        log.info(memberDTO);

        Map<String, Object> claimMap =
                Map.of("email", memberDTO.getEmail(), "role", memberDTO.getRole());

        String accessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setEmail(memberDTO.getEmail());

        return ResponseEntity.ok(tokenResponseDTO);
    }

    @PostMapping(value ="refreshToken",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String accessToken, String refreshToken) {

        //만약 accessToken 이 없다면 혹은 refreshToken 이 없다면
        if(accessToken == null || refreshToken == null) {
            throw MemberExceptions.TOKEN_NOT_ENOUGH.get();
        }

        //accessToken 에서 Bearer(공백포함 7) 잘라낼 때 문제가 발생한다면
        if(!accessToken.startsWith("Bearer ")) {

            throw MemberExceptions.ACCESSTOKEN_TOO_SHORT.get();
        }

        String accessTokenStr = accessToken.substring("Bearer ".length());

        //AccessToken 의 만료 여부 체크
        try{

            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);

            String email = payload.get("email").toString();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setEmail(email);
            tokenResponseDTO.setRefreshToken(refreshToken);

            return ResponseEntity.ok(tokenResponseDTO);
        }catch(ExpiredJwtException ex){

            //정상적으로 만료된 경우

            //만약 RefreshToken 까지 만료되었다면
            try {

                Map<String, Object> payload = jwtUtil.validateToken(refreshToken);

                String email = payload.get("email").toString();
                String role = payload.get("role").toString();
                String newAccessToken = null;
                String newRefreshToken = null;

                if(alwaysNew){

                    Map<String, Object> claimMap = Map.of("email", email, "role", role);
                    newAccessToken = jwtUtil.createToken(claimMap, accessTime);
                    newRefreshToken = jwtUtil.createToken(claimMap, refreshTime);
                }

                TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(newRefreshToken);
                tokenResponseDTO.setEmail(email);

                return ResponseEntity.ok(tokenResponseDTO);
            }catch(ExpiredJwtException ex2) {

                throw MemberExceptions.REQUIRE_SIGN_IN.get();
            }
        }
    }

    @RequestMapping("kakao")
    public ResponseEntity<TokenResponseDTO> kakaoToken(String accessToken) {

        log.info("kakao access token: " + accessToken);

        MemberDTO memberDTO = memberService.authKakao(accessToken);

        log.info(memberDTO);

        Map<String, Object> claimMap =
                Map.of("email", memberDTO.getEmail(), "role", memberDTO.getRole());

        String access_Token = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(access_Token);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setEmail(memberDTO.getEmail());

        return ResponseEntity.ok(tokenResponseDTO);
    }
}