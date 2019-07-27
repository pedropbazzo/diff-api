package io.github.mariazevedo88.diffapi.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class EncodingService {
	
	private static final String BASE64RGX = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
	
	public String encodeToBase64(byte[] bytes) {
		return Base64Utils.encodeToString(bytes);
	}
	
	public boolean isEncodedBase64(String str) {
		return str.matches(BASE64RGX);
	}
}
