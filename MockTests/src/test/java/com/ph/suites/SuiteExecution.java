package com.ph.suites;

import com.ph.services.CalculatorTest;
import com.ph.services.CalculeRentValueTest;
import com.ph.services.RentServiceTest;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
@SuiteClasses({
	CalculatorTest.class,
	CalculeRentValueTest.class,
	RentServiceTest.class
})
public class SuiteExecution {
	//Remove if possible!
}
