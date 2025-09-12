package com.secBackend.cab_backend.filter;

import com.secBackend.cab_backend.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            //Get Token From Request
            final String authHeader = request.getHeader("Authorization");
            String email=null;
            String jwtToken=null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                email=jwtUtil.getSubject(jwtToken);
            }
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String role=jwtUtil.getRole(jwtToken);
                List<SimpleGrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));
                //Validate Jwt Token
                if(jwtUtil.validateToken(jwtToken,email)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email,null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            filterChain.doFilter(request,response);

        }
        //Signature Exception :JWT signature does not match locally computed signature
        catch (SignatureException  ex){
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{ \"error\": \"Invalid JWT signature\", \"message\": \"" + ex.getMessage() + "\" }");


        }
        //Fallback
        catch (Exception ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{ \"error\": \"Authentication error\", \"message\": \"" + ex.getMessage() + "\" }"
            );
        }


    }
}
