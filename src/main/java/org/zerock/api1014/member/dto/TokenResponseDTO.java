package org.zerock.api1014.member.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String email;
    private String accessToken;
    private String refreshToken;

}
