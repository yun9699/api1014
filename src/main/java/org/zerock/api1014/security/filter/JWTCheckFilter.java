package org.zerock.api1014.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("shouldNotFilter");

        // 이 경로로 들오오면 필터가 들어올 필요가 없어, 정상적이라면 "doFilterInternal" 출력이 안되는거지
        String uri = request.getRequestURI();
        log.info("--------------------------------------");

        if(uri.equals("/api/v1/member/makeToken")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal");
        filterChain.doFilter(request, response);
    }


}
