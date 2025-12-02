package server.smartcond.Application.Config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import server.smartcond.Domain.Utils.Jwtutils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {
    private Jwtutils jwtutils;
    public JwtTokenValidator(Jwtutils jwtutils) {
        this.jwtutils = jwtutils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            try {

                DecodedJWT decodedJWT = jwtutils.validateToken(jwtToken);

                String username = jwtutils.extractUsername(decodedJWT);

                List<String> rolesDelToken = decodedJWT.getClaim("roles").asList(String.class);

                Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(rolesDelToken.toArray(new String[0]));

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {

            }

        } else {

        }

        filterChain.doFilter(request, response);
    }
}
