package com.ph.matchers;

import com.ph.matchers.WeekDayMatcher;

import java.util.Calendar;

public class SelfMatcher {

	public static WeekDayMatcher fallsIn(Integer weekDay) {
		return new WeekDayMatcher(weekDay);
	}
	
	public static WeekDayMatcher fallsInAMonday(){
		return new WeekDayMatcher(Calendar.MONDAY);
	}
	
	public static DateDifferenceDaysMatcher itsTodayWithDifferenceInDays(Integer qttDays) {
		return new DateDifferenceDaysMatcher(qttDays);
	}

	public static DateDifferenceDaysMatcher itsToday() {
		return new DateDifferenceDaysMatcher(0);
	}
}
