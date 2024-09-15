package com.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jwt.entities.User;
import com.jwt.services.JwtService;
import com.jwt.services.UserService;
import com.jwt.util.CommonUtility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserService userService;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		Authentication authentication = null;

		try {
			final String authorizationHeader = request.getHeader("Authorization");

			if (CommonUtility.NOT_NULL_NOT_EMPTY_NOT_BLANK_STRING.test(authorizationHeader)
					&& authorizationHeader.startsWith("Bearer ")) {

				final String token = authorizationHeader.split("Bearer ")[1];
				Long userIdFromToken = jwtService.getUserIdFromToken(token);

				if (userIdFromToken != null) {
					User user = userService.getUserById(userIdFromToken);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							user, null, user.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					authentication = usernamePasswordAuthenticationToken;
				} else {
					log.error("Invalid token");
				}
			} else {
				log.error("Token does not start with Bearer");
			}
		} catch (Exception ex) {
			handlerExceptionResolver.resolveException(request, response, null, ex);
		} finally {
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		}
	}

}
