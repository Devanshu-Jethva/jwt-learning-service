package com.jwt.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommonUtility {

	public static final Predicate<String> NOT_NULL_NOT_EMPTY_STRING = s -> s != null && !s.isEmpty();

	public static final Predicate<String> NOT_NULL_NOT_EMPTY_NOT_BLANK_STRING = s -> s != null && !s.isEmpty()
			&& !s.isBlank();

	public static final Predicate<List<?>> NOT_NULL_NOT_EMPTY_LIST = s -> s != null && !s.isEmpty();

	public static final Predicate<Map<?, ?>> NOT_NULL_NOT_EMPTY_MAP = s -> s != null && !s.isEmpty();

	/**
	 * Encoding String in BCryptPasswordEncoder Used for Oauth
	 *
	 * @param string
	 * @return
	 */
	public static String generateBcrypt(final String string) {
		return new BCryptPasswordEncoder().encode(string);
	}

	/**
	 * Generate random number for OTP
	 *
	 * @return
	 */
	public static int getRandomNumber() {
		final SecureRandom number = new SecureRandom();
		return 100000 + number.nextInt(899999);
	}

	public static String getRandomAlphaNumericNumber() {
		final SecureRandom number = new SecureRandom();
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(20);
		for (int i = 0; i < 20; i++) {
			sb.append(alphaNumeric.charAt(number.nextInt(alphaNumeric.length())));
		}
		return sb.toString();
	}

	public static String getRandomAlphaNumericNumber(final int length) {
		final SecureRandom number = new SecureRandom();
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(alphaNumeric.charAt(number.nextInt(alphaNumeric.length())));
		}
		return sb.toString();
	}

	public static String getRandom8DigitNumericNumber() {
		final SecureRandom number = new SecureRandom();
		String numeric = "1234567890";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			sb.append(numeric.charAt(number.nextInt(numeric.length())));
		}
		return sb.toString();
	}

	public static LocalDate convetUtilDatetoLocalDate(final Date date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime convetUtilDatetoLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
	}

	public static Date getDateWithoutTime(final Date date) {
		return Date.from(convetUtilDatetoLocalDate(date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date getTomorrowDateWithoutTime(final Date date) {
		return Date.from(
				convetUtilDatetoLocalDate(date).plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date convertLocalDateToUtilDate(final LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date convertLocalDateTimeToUtilDate(final LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Long countMinuteForBetweenTwoDate(final Date startTime, final Date endTime) {
		if (startTime != null && endTime != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			int startHour = calendar.get(Calendar.HOUR_OF_DAY);
			int startMinute = calendar.get(Calendar.MINUTE);
			calendar.setTime(endTime);
			int endHour = calendar.get(Calendar.HOUR_OF_DAY);
			int endMinutue = calendar.get(Calendar.MINUTE);
			int startTotalMinutes = startHour * 60 + startMinute;
			int endTotalMinutes = endHour * 60 + endMinutue;
			return (long) (endTotalMinutes - startTotalMinutes);
		}
		return null;
	}

}
