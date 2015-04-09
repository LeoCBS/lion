package com.custom.service;

import javax.persistence.TypedQuery;

import com.lion.entity.AbstractEntity;
import com.lion.service.AbstractService;

public class TestService  extends AbstractService<AbstractEntity>{

	@Override
	protected Class<AbstractEntity> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TypedQuery<AbstractEntity> getQuerySearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TypedQuery<Long> getQueryCount() {
		// TODO Auto-generated method stub
		return null;
	}

}
