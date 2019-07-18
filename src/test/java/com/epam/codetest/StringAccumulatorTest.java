package com.epam.codetest;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringAccumulatorTest {
	

	@Test
	public void emptyString() {
		assertThat(StringAccumulator.add(""), is(0));
		assertThat(StringAccumulator.add(" "), is(0));	
		assertThat(StringAccumulator.add(null), is(0));
	}

	@Test
	public void addSingleNum() {
		assertThat(StringAccumulator.add("99"), is(99));
	}

	@Test
	public void Add2Numbers() {
		assertThat(StringAccumulator.add("100,1"), is(101));
	}

	@Test
	public void Add3Numbers() {
		assertThat(StringAccumulator.add("100,900,3"), is(1003));
	}

	@Test
	public void AddNumbersWithNewline() {
		assertThat(StringAccumulator.add("2\n2"), is(4));
		assertThat(StringAccumulator.add("1\n2,3"), is(6));
		assertThat(StringAccumulator.add("10\n"), is(10));
	}

	@Test
	public void sumsNumbersDelimitedByCommaOrNewline() {
		assertThat(StringAccumulator.add("1,2\n3"), is(6));
	}

	@Test
	public void unknownAmtNumbers() {
		try
		{
			StringAccumulator.add("1,2,3,4");
			fail();
		}catch(IllegalArgumentException e)
		{
			assertTrue(e.getMessage().contains("unknown amount of numbers"));
		}
		
	}
	
	@Test
	public void negativeNum() {
		try
		{
			StringAccumulator.add("-1");
			fail();
		}catch(IllegalArgumentException e)
		{
			assertTrue(e.getMessage().contains("-1"));
		}
		
	}

	@Test
	public void multipleNegativeNum() {

		try
		{
			StringAccumulator.add("-1,-3,-5");
			fail();
		}catch(IllegalArgumentException e)
		{
			assertTrue(e.getMessage().contains("-1,-3,-5"));
		}
	}

	@Test
	public void addOver1000() {
		assertThat(StringAccumulator.add("1002"), is(0));
		assertThat(StringAccumulator.add("1000"), is(1000));
		assertThat(StringAccumulator.add("2,1001"), is(2));
	}

	@Test
	public void customDelimiters() {
		assertThat(StringAccumulator.add("//[!!]\n1!!2!!3"), is(6));
	}

	@Test
	public void customDelimitersWithDiffChar() {
		assertThat(StringAccumulator.add("//[#][k]\n1k2#3"), is(6));
		assertThat(StringAccumulator.add("//[bb][sss]\n1sss2bb3"), is(6));
	}
}
