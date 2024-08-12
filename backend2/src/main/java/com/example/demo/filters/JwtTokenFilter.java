package com.example.demo.filters;

import com.example.demo.components.JwtTokenUtil;
import com.example.demo.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // chi la kiem tra api co token(user+admin), api k co token
    // kiem tra api user, api admin la WebSecurityConfig
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    // tat ca request deu phai doFilter, nhung co 2 dang: k can token va co token
    // co token thi phai kiem tra token, dang ky voi spring (nghia la authenticate)
    // khi authenticated thi moi qua duoc ham .anyRequest().authenticated();
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // request khong yeu cau token
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            //request yeu cau token
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // ngan k cho truy cap thi gui loi sendError
                return;                                                                     // thuc hien yeu cau loi thi throw new error
            }
            final String token = authHeader.substring(7);
            final String phone = jwtTokenUtil.extractPhone(token);
            if (phone != null           // tai sao co token roi ma lai kiem tra login chua??? login moi co token chu
                    && SecurityContextHolder.getContext().getAuthentication() == null) { // chua authenticate (chua login)
//                UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
                User userDetails = (User) userDetailsService.loadUserByUsername(phone);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =            // tao doi tuong de authenticate
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,                                      // dinh danh
                                    userDetails.getAuthorities()                         // quyen cua user
                            );
                    // them chi tiet cho token
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // authenticate voi Spring
                }
            }
            filterChain.doFilter(request, response);                                    //doFilter la thuc hien WebSecurityConfig
        } catch (Exception e) {
//            throw new ServletException("Unauthorized");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // SC: status code
        }
    }

    private boolean isBypassToken(@NotNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST")
        );
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
//                filterChain.doFilter(request,response);
                return true;
            }
        }
        return false;
    }
}
//24:19