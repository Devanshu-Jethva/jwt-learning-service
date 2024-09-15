package com.jwt.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Nov 6, 2023
 */
@Component
@RequiredArgsConstructor
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

	private final MessageSource messageSource;

	/**
	 * If no locale specified take English as local and display messages.
	 */
	@Override
	public String getMessage(final String id, final Object[] arg) {
		final Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, arg, locale);
	}
}
