package org.zerock.api1014.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.api1014.member.dto.MemberDTO;
import org.zerock.api1014.member.dto.TokenRequestDTO;
import org.zerock.api1014.member.dto.TokenResponseDTO;
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

        String accesToken = jWTUtil.createToken(claimMap,30);
        String refreshToken = jWTUtil.createToken(claimMap,360);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accesToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setEmail(memberDTO.getEmail());

        return ResponseEntity.ok(tokenResponseDTO);

    }

}