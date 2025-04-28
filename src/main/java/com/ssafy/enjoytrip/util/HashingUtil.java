package com.ssafy.enjoytrip.util;

import java.security.MessageDigest;

public class HashingUtil {

	private static HashingUtil instance = new HashingUtil();

	private HashingUtil() {
	};

	public static HashingUtil getInstance() {
		return instance;
	}

	public String sha256(String input) {
		try {
			// SHA-256 해시 함수 객체 생성
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// 입력값을 바이트 배열로 변환 후 해싱
			byte[] hash = digest.digest(input.getBytes("UTF-8"));

			String firstHash = byteArrayToString(hash);

			// salt 값 추가
			firstHash = firstHash.concat(input);
			// 이중 해싱 + salt 값
			byte[] doubleHash = digest.digest(firstHash.getBytes());

			return byteArrayToString(doubleHash);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String customHashing(String input) {
		// 64byte hashing
		int[] hash = new int[8];

		for (int i = 0; i < hash.length; i++) {
			// 초기값 설정 0x9e3779b9 는 매직 넘버 (황금비의 역수)

			hash[i] = 0x12345678 ^ (i * 0x9e3779b9);
		}

		byte[] bytes = input.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			int index = i % 8;
			hash[index] ^= ((bytes[i] & 0xFF) + i) * 31;
			hash[index] = Integer.rotateLeft(hash[index], (i % 11) + 5);
		}

		StringBuilder result = new StringBuilder();
		for (int val : hash) {
			result.append(String.format("%08x", val));
		}

		return result.toString(); // 항상 64자
	}

	private String byteArrayToString(byte[] list) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : list) {
			String hex = Integer.toHexString(0xff & b); // 1바이트씩 16진수 처리
			if (hex.length() == 1)
				hexString.append('0'); // 자릿수 맞추기
			hexString.append(hex);
		}

		return hexString.toString();
	}
}
