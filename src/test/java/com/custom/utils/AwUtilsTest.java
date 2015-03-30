package com.custom.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

import com.lion.utils.AWUtils;

public class AwUtilsTest {
	
	@Test
	public void MD5NotNull() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Assert.assertNotNull(AWUtils.MD5("teste"));
	}
	
	@Test
	public void MD5Logic() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Assert.assertEquals("698dc19d489c4e4db73e28a713eab07b",AWUtils.MD5("teste"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void MD5Null() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		AWUtils.MD5(null);
	}
	
	@Test
	public void SHANotNull() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Assert.assertNotNull(AWUtils.getSHA("teste"));
	}
	
	@Test
	public void SHALogic() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Assert.assertEquals("46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5",AWUtils.getSHA("teste"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void SHANull() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		AWUtils.getSHA(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void capitalizeNull(){
		AWUtils.capitalizeString(null);
	}
	
	@Test
	public void capitalizeLogic(){
		Assert.assertEquals("Test Capitalize", AWUtils.capitalizeString("test capitalize"));
	}

}
