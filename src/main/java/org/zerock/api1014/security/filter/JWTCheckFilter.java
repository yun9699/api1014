package org.zerock.api1014.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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

        log.info(request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        String token = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }else {
            //gson 라이브러리 이용해서 json데이터 만들어서 status
            //401 = 인증이 안됐따
            makeError(response, Map.of("status",401, "msg", "No Access Token"));
            // return 빼먹으면 안됨!!!!!!!!!!!!!!!!!!!!!
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void makeError(HttpServletResponse response, Map<String, Object> map) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);

        response.setContentType("application/json");
        response.setStatus((int)map.get("status"));
        try {
            PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
