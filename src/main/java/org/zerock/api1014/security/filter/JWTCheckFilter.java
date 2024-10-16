package org.zerock.api1014.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.api1014.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

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

        //JWT validate
        try{

            Map<String, Object> claims = jwtUtil.validateToken(token);
            log.info(claims);

            filterChain.doFilter(request, response);

        }catch (JwtException e){

            log.info(e.getClass().getName());
            log.info(e.getMessage());
            log.info("----------------------------------");

            String classFullName = e.getClass().getName();

            String shortClassName = classFullName.substring(classFullName.lastIndexOf(".") + 1);

            makeError(response, Map.of("status",401, "msg", shortClassName));

            e.printStackTrace();
        }



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
