package com.ph.matchers;

import java.util.Date;

import com.ph.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DateDifferenceDaysMatcher extends TypeSafeMatcher<Date> {

	private Integer quantityDays;
	
	public DateDifferenceDaysMatcher(Integer quantityDays) {
		this.quantityDays = quantityDays;
	}
	
	public void describeTo(Description arg0) {
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isSameDate(data, DataUtils.obtainDataInDifferenceInDays(quantityDays));
	}

}
