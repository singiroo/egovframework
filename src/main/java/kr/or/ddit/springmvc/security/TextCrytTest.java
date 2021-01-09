package kr.or.ddit.springmvc.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.rte.fdl.cryptography.EgovCryptoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/egovframework/spring/context-aspect.xml"
		, "/egovframework/spring/context-common.xml"
		, "/egovframework/spring/context-datasource.xml"
		, "/egovframework/spring/context-properties.xml"
		, "/egovframework/spring/context-mapper.xml"
		, "/egovframework/spring/context-transaction.xml"
		, "/egovframework/spring/context-security.xml"
})
public class TextCrytTest{
	private static final Logger LOGGER = LoggerFactory.getLogger(TextCrytTest.class);
	
	@Resource(name="ARIACryptoService")
	EgovCryptoService cryptoService;
	
	String password = "egovframe";		
	@Test
	public void testString(){
//		String testString = "This is ...";
		
		String testString = "link_kim4219@gmail.com";
		
		try {
			byte[] encrypted = cryptoService.encrypt(testString.getBytes("UTF-8"), password);
			
			byte[] decrypted = cryptoService.decrypt(encrypted, password);
			
			LOGGER.info(new String(encrypted)+"    "+new String(decrypted));

			String base64Encrypted = Base64.getEncoder().encodeToString(encrypted);
			
			byte[] base64Decrypted = cryptoService.decrypt(Base64.getDecoder().decode(base64Encrypted), password);
			
			LOGGER.info(base64Encrypted +"    "+new String(base64Decrypted));
			
			Assert.assertEquals(testString, new String(decrypted, "UTF-8").toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
