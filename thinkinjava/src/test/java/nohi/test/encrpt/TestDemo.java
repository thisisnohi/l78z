//package nohi.test.encrpt;
//
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.X509EncodedKeySpec;
//
//import com.bitservice.adi.security.Base64;
//
//public class TestDemo {
//	public static void main(String[] args) {
//		// 租赁公钥
//		// String publicKey =
//		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+yLyxg2xb6LkkWnWZF+XXjkZN0Fhne1eJTA3EmOlMyD5fhOfs9Ri0qwWqRW9s4m4k4tgJX5tcGRwCd7x6PEyJXr3/YgcR81zFqMjsnCYdB24Ii3bLdGhcokwJ/cW2yTWwa7ujpjemEROv5JasFJwXKdJVz+uSJDYmOsylL1MvNwIDAQAB";
//		// 租金托管公钥
//		String publicKey = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDBsQLgSjwKH7vMG5/Lcb1mMUwUvOkWBIa0javuM1z4Jk37/dlYgjAdiYlirDe9KUr6V1vaD68XCIcXKUE13eCtdlbiHE2A29woelCbwqvqtxJ6Uan09AZ6bxSH6jQbyguTIaI3ET6b2lZ1Da5VI3vJYq4w7aZQApI2KKhAtCntQwIBAw==";
//		try {
//			sendRequest(publicKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void sendRequest(String zjtgSy) throws Exception {
//
//		/*
//		 * 用租赁公钥验签
//		 */
//		// 租金托管公钥加密数据
//		// String responseDataJson =
//		// "tt3iY9/1hSAoYT7wuF9pjTKVxzuP3MLQh1Y5MHSGB9ujmrhQP02Cj/gixyYC/Mrh4aGO/+CJkRzQY6ysgE5hD2adLFF8UOhr/SmJhhPHiaCVfdUPdVdFIEVTuhjwN6zB2nDeKlWNH5EVs1JRaS4bpr/NHCIwDnDh/O6VFZnkR2lkFNgKHG6ikINa3DQEvaZ1vjj8uUMFfGflDO5okxw34Jx36fs62rLMPw69uHQNLp3P5jaZAIHo5ZG/VKBESBeZA1Gk7Er0NFgPUl2Rd01+FbtWuSHRFxDtjPbqEi3Ca8NAAu9pvtn0uKTMxLOO0Uw7kreLTNN3TM2hkXsK/NApYg==";
//		// 租赁私钥签名字符串
//		// String responseSignStr =
//		// "l0gVMIOaW1BeDQO/VZ7s3sc3qqwSn16KQtQphq350Yl4TexOmobVqLpJsW+6+NRHfLU0eJrxe2vW83GrjCt3TRVZo51AWbsQBDOgIsZpJTiGyXhnkPS5DJ+8gnPN78/g1g4rv97l6vCK7cYUhJR7RmOMR69xKdHd8T6BwL8vIHI=";
//
//		/*
//		 * 用租金托管公钥验签
//		 */
//		// 租赁公钥加密数据
//		String responseDataJson = "V/hroDMdeDXsUYCsb7GBzudqrUtraPSz1fJY/LVQA+vIBxymA8brMt7d0LX8EWp/+3jfvX2Z7v5FwmkTbWn3aUdTQpEpuBKgeTkgnx4eAW+Wq2Y8XbAAV9SpvV/zj/hYi+JeLBLXefWEO1GMMQeUemLCDgnsmzN0k3OxL4C3I9o4hsUGn+J00aLKSEzYelJQOp7P16etTqhCo8ot3De3eUOyUCTVqYrxgAGWVENnqHMj0KpV8Fd2CddWNdTc57Z7MkuehCm1GrcJ6b4RNqiFwcb53q3kIdbMhvkQ2UL225C1jr8WaOkkq+y1tyKSTT7JQ01YwnlG21mP4qKJCqRE2g==";
//		// 租金托管私钥签名字符串
//		String responseSignStr = "MWaDykSOEGJ+CB93AO9GMBveMED7zm828mz5UymWlvKdER/FhHHxgxT7vhNlcM3+Bw5p4ZUcVSF7WAmfx05yKcSomYfJfQ63AwWvD9XkPPajWRAmpBfwEtchQ82iJPolYNo23CphinPeO86qot3Z3K9z6BxeibVxsIc7MRjNBmg=";
//
//		boolean verifySignResult = verify22(responseDataJson, responseSignStr, zjtgSy);
//
//		System.out.println("响应验证结果：{}" + verifySignResult);
//
//	}
//
//	/**
//	 * 验签
//	 *
//	 * @param srcData
//	 *            原始字符串
//	 * @param publicKey
//	 *            公钥
//	 * @param sign
//	 *            签名
//	 * @return 是否验签通过
//	 */
//	public static boolean verify22(String srcData, String sign, String zjtgSy) throws Exception {
//		Signature signature = Signature.getInstance("SHA1WithRSA");
//		signature.initVerify(getPublicKeyFromStr(zjtgSy));
//		signature.update(srcData.getBytes());
//		return signature.verify(Base64.decode(sign));
//	}
//
//	private static PublicKey getPublicKeyFromStr(String publicKeyStr) {
//		try {
//			byte[] buffer = Base64.decode(publicKeyStr);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//			return keyFactory.generatePublic(keySpec);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
