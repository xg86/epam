package com.epam.codetest;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.base.Strings;

import static com.epam.codetest.Const.*;

public class StringAccumulator {

	private String delimiter;
	private String numbers;
	

	private StringAccumulator(String delimiter, String numbers) {
		this.delimiter = delimiter;
		this.numbers = numbers;
	}
	
	public static int add(String numbers) {
		return (Strings.isNullOrEmpty(numbers)) ? ZERO : parseInput(numbers.trim()).calculate(Operation.ADD);
	}
	
	private static StringAccumulator parseInput(String numbers) {
		if (numbers.startsWith(DOUBLE_SLASH)) {
			String[] prefixAndOperands = numbers.split(NEW_LINE, TWO);
			String delimiter = parseDelimiter(prefixAndOperands[ZERO]);
			return new StringAccumulator(delimiter, prefixAndOperands[ONE]);
		} else {
			return new StringAccumulator(COMMA+BAR+NEW_LINE, numbers);
		}
	}

	private static String parseDelimiter(String prefix) {
		String delimiter = prefix.substring(TWO);
		if (delimiter.startsWith(LEFT_SQUARE_BRACKET)) {
			delimiter = delimiter.substring(ONE, delimiter.length() - ONE);
		}
		return Stream.of(delimiter.split(RIGHT_SQUARE_BRACKET+DOUBLE_BACK_SLASH + LEFT_SQUARE_BRACKET ))
				.map(Pattern::quote)
				.collect(Collectors.joining(BAR));
	}
	
	private int calculate(Operation opera) {
		checkNegative();
		if(getNumbers().count() > THREE)
			throw new IllegalArgumentException( UNKNOWN_AMOUNT_OF_NUMBERS + getNumbers().count());
		switch(opera)
		{
			case ADD: return getNumbers().sum();
			default:
				throw new IllegalArgumentException(OPERATION_NOT_SUPPORT_YET + opera.toString());
		}	
	}

	private void checkNegative() {
		String negativeNumbers = getNumbers().filter(n -> n < ZERO)
				.mapToObj(Integer::toString)
				.collect(Collectors.joining(COMMA));
		if (!negativeNumbers.isEmpty()) {
			throw new IllegalArgumentException(NEGATIVE_NUMBER + negativeNumbers);
		}
	}

	private IntStream getNumbers() {
		if (numbers.isEmpty()) {
			return IntStream.empty();
		} else {
			return Stream.of(numbers.split(delimiter))
					.mapToInt(Integer::parseInt).filter(n -> n <= ONE_THOUSAND);
		}
	}
}
