package com.jwt.exception;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.jwt.locale.MessageByLocaleService;
import com.jwt.response.handler.GenericResponseHandlers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author : Kody Technolab PVT. LTD.
 * @date : 13-Apr-2023
 */
//@ControllerAdvice(basePackages = "com.jwt")
@AllArgsConstructor
@Slf4j
public class ErrorHandlingController {

	private final MessageByLocaleService messageByLocaleService;

	/**
	 * Central exception handler and generate common custom response
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handleControllerException(final HttpServletRequest request, final Throwable exception,
			final Locale locale) {
		HttpStatus status = null;
		String message = exception.getMessage();
		if (exception instanceof BaseException) {
			status = ((BaseException) exception).getStatus();
			message = exception.getMessage();
		} else if (exception instanceof BaseRuntimeException) {
			status = ((BaseRuntimeException) exception).getStatus();
			message = exception.getMessage();
		}
//		else if (exception instanceof WebApplicationException) {
//			status = HttpStatus.valueOf(((WebApplicationException) exception).getResponse().getStatus());
//			message = exception.getMessage();
//		} 
		else if (exception instanceof AccessDeniedException || exception instanceof AuthenticationException) {
			status = HttpStatus.UNAUTHORIZED;
			message = exception.getMessage();
		} else if (exception instanceof MissingServletRequestParameterException
				|| exception instanceof MissingRequestHeaderException
				|| exception instanceof HttpMessageNotReadableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = exception.getMessage();
		} else if (exception instanceof HttpClientErrorException || exception instanceof HttpServerErrorException) {
			if (exception.getMessage().contains("401")) {
				status = HttpStatus.UNAUTHORIZED;
				message = messageByLocaleService.getMessage("invalid.username.password", null);
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				message = exception.getMessage();
			}
		} else if (exception instanceof MethodArgumentTypeMismatchException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Argument mis matched";
		} else if (exception instanceof ObjectOptimisticLockingFailureException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Something went wrong. Please try again";
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = messageByLocaleService.getMessage("common.error", null);
			StringBuffer requestedURL = request.getRequestURL();
			log.info("Requested URL:{}", requestedURL);
			log.error("exception : {}", exception);
		}
		return new GenericResponseHandlers.Builder().setStatus(status).setMessage(message).create();
	}

}
