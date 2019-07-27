package io.github.mariazevedo88.diffapi.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class DecodingService {
	
	public String decodeBase64ToString(String base64Encoded) {
		byte[] base64Decoded = Base64Utils.decodeFromString(base64Encoded);
        return new String(base64Decoded);
	}

}
