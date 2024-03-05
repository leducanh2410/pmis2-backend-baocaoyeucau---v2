package com.pmis.security.jwt;

import com.pmis.security.services.UserDetailsServiceImpl;
import com.pmis.util.Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${app.jwt.header}")
    private String tokenRequestHeader;
    
    @Value("${app.jwt.authType}")
    private String tokenRequestauthType;

    @Value("${app.jwt.header.prefix}")
    private String tokenRequestHeaderPrefix;

    @Value("${app.jwt.orgcode}")
    private String orgcodeRequestHeader;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            String orgcode=getOrgcodeFromRequest(request);

            if (orgcode == null || orgcode.isEmpty()) {
                orgcode="EVN";
            }

            if (StringUtils.hasText(jwt)) {
                String userId = jwtUtils.validateToken(jwt, orgcode);
                // Check orgcode (không cho phép mạo danh sinh token ngoài phạm vi)
//                if (!orgcode.equals("SYS")) {
//                    S_EVNID_CFG cfg = userServiceClt.getEvniIDConfig(userId);
//                    if (cfg != null && !cfg.getOrgCode().equals(orgcode)) //Invalid
//                        throw new ServletException("OrgCode thiếu hoặc không hợp lệ");
//                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, jwt, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenRequestHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            // log.info("Extracted Token: " + bearerToken);
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }

    private String getOrgcodeFromRequest(HttpServletRequest request) {
        return request.getHeader(orgcodeRequestHeader);
    }
}
