package com.oz.dozer.mapper;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.oz.dozer.destination.SomeDestinationBean;
import com.oz.dozer.source.AnotherSrcBean;
import com.oz.dozer.source.SomeSrcBean;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class TestTheMapper {

	@Mock
	private Mapper mockDozerMapper;
	@InjectMocks
	private TheMapper theMapper;

	@Before
	public void setUp() throws Exception {
		theMapper.init();
	}

	@Test
	public final void testMap() {
		final SomeSrcBean ssb = new SomeSrcBean();
		final AnotherSrcBean asb = new AnotherSrcBean();
		final String dest = "TESTING";
		final SomeDestinationBean sdbNull = theMapper.map(ssb, asb, dest);
		
		Assert.assertNull(sdbNull); //should fail with validation, see log errors
		
		//pass validation
		doAnswer(new Answer<Void>() {
			@Override public Void answer(final InvocationOnMock invocation) throws Throwable {
				final SomeDestinationBean mockSdb = invocation.getArgumentAt(1, SomeDestinationBean.class);
				mockSdb.setSomeDestinationStr("blah");
				return null;
			}
		})
		.when(mockDozerMapper)
		.map(any(), any(), anyString());
		
		final SomeDestinationBean sdb = theMapper.map(ssb, asb, dest);
		
		verify(mockDozerMapper).map(ssb, sdb, dest+"_ss2sd");
		verify(mockDozerMapper).map(asb, sdb, dest+"_as2sd");
	}
}
