package org.zerock.api1014.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.api1014.common.exception.CommonExceptions;
import org.zerock.api1014.common.exception.TaskException;
import org.zerock.api1014.member.domain.MemberEntity;
import org.zerock.api1014.member.dto.MemberDTO;
import org.zerock.api1014.member.exception.MemberExceptions;
import org.zerock.api1014.member.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO authenticate(String email, String password) {

        Optional<MemberEntity> result = memberRepository.findById(email);

        MemberEntity member = result.orElseThrow(() -> MemberExceptions.BAD_AUTH.get());

        String enPw = member.getPw();

        boolean match = passwordEncoder.matches(password, enPw);

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(email);
        memberDTO.setPw(enPw);
        memberDTO.setRole(member.getRole().toString());

        return memberDTO;
    }

    public MemberDTO authKakao(String accessToken) {

        log.info("----------authKakao-------");

        String email = getEmailFromKakaoAccessToken(accessToken);

        log.info("email : " + email);

        return null;
    }

    private String getEmailFromKakaoAccessToken(String accessToken){


        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";


        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);


        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        ResponseEntity<LinkedHashMap> response
                = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity, LinkedHashMap.class);


        log.info(response);


        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();


        log.info("------------------------------------");


        log.info(bodyMap);


        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");


        log.info("kakaoAccount: " + kakaoAccount);
        return kakaoAccount.get("email");
    }


}