package com.custom.utils;

import org.junit.Test;

import com.lion.utils.BeanHelper;

public class BeanHelperTest {
	
	@Test
	public void exibiMensagemErro(){
		BeanHelper.newInstance().exibiMensagem("teste");
	}

}
