package com.oz.dozer.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.oz.dozer.destination.SomeDestinationBean;
import com.oz.dozer.source.AnotherSrcBean;
import com.oz.dozer.source.SomeSrcBean;

@ContextConfiguration("classpath:spring-beans.xml")
public class TheMapperTestNg extends AbstractTestNGSpringContextTests {

	@Autowired
	private TheMapper theMapper;
	
	@Test
	public void mappingTest() {
		final SomeSrcBean ssb = new SomeSrcBean();
		final AnotherSrcBean asb = new AnotherSrcBean();
		
		final SomeDestinationBean sdbNull = theMapper.map(ssb, asb, "destA");
		
		AssertJUnit.assertNull(sdbNull); //should fail with validation, see log errors
		
		final String someSrcStr = "TESTING!!";
		final String someSrcStr2 = "TESTING2!!";
		ssb.setSomeSrcStr(someSrcStr);
		ssb.setSomeSrcStr2(someSrcStr2);
		
		final double anotherSrcDbl = 666.66d;
		asb.setAnotherSrcDbl(anotherSrcDbl);
		
		final SomeDestinationBean sdb1 = theMapper.map(ssb, asb, "destA");
		
		AssertJUnit.assertEquals("Some str", sdb1.getSomeDestinationStr(), someSrcStr);
		AssertJUnit.assertEquals("Another dbl", sdb1.getSomeDestinationDbl(), anotherSrcDbl);

		final SomeDestinationBean sdb2 = theMapper.map(ssb, asb, "destB");
		
		AssertJUnit.assertEquals("Some str 2", sdb2.getSomeDestinationStr(), someSrcStr2);
		AssertJUnit.assertEquals("Another dbl", sdb2.getSomeDestinationDbl(), anotherSrcDbl);
	}

}
