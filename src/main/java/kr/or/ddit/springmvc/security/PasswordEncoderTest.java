package kr.or.ddit.springmvc.security;

import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

public class PasswordEncoderTest {
	public static void main(String[] args) {
		EgovPasswordEncoder encoder = new EgovPasswordEncoder();
		
		encoder.setAlgorithm("SHA-256");
		
		//egovframe 문자열을 encrypt한 결과
		System.out.println("Digested Password:"+encoder.encryptPassword("dditspring"));
		
		String plainPassword = "1234";
		String encryptedPassword = encoder.encryptPassword(plainPassword);
		
		//암호화 전/후의 문자열을 이용해 둘의 값이 같은 것인지 비교
		System.out.println(encoder.checkPassword(plainPassword, encryptedPassword));
	}
}
