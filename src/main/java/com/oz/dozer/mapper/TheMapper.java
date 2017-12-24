package com.oz.dozer.mapper;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import com.oz.dozer.destination.SomeDestinationBean;
import com.oz.dozer.source.AnotherSrcBean;
import com.oz.dozer.source.SomeSrcBean;

@Component("theMapper")
public class TheMapper {
	private final static Logger logger = Logger.getLogger(TheMapper.class);
	
	@Resource(name="mapper1")
	private Mapper dozerMapper;

	@PostConstruct
	public void init() {
		if (null == dozerMapper) {
			logger.error("Mapper not initialized!!");
		}
	}

	public SomeDestinationBean map(final SomeSrcBean ssb, final AnotherSrcBean asb, final String destinationType) {
		final SomeDestinationBean sdb = new SomeDestinationBean();
		
		//Do mapping. Note that map id has to be unique
		dozerMapper.map(ssb, sdb, destinationType+"_ss2sd");
		dozerMapper.map(asb, sdb, destinationType+"_as2sd");
		
		//Run validation
		final Set<ConstraintViolation<SomeDestinationBean>> cvs = 
				Validation.buildDefaultValidatorFactory().getValidator().validate(sdb);
		for (ConstraintViolation<?> cv : cvs) {
			logger.error(cv.getPropertyPath() + " => " + cv.getMessage());
			return null;
		}
		
		return sdb;
	}
}
