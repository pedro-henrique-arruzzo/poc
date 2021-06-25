package com.ph.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ph.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class WeekDayMatcher extends TypeSafeMatcher<Date> {

	private Integer weekDay;
	
	public WeekDayMatcher(Integer weekDay) {
		this.weekDay = weekDay;
	}
	
	public void describeTo(Description desc) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, weekDay);
		String longDate = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		desc.appendText(longDate);
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.checkWeekDay(data, weekDay);
	}

}
