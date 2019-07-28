package io.github.mariazevedo88.diffapi.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

/**
 * Service that implements methods related to base64 string decoding functionality.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@Service
public class DecodingService {
	
	/**
	 * Method that implements base64 decoding string to a normal string.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019 
	 * 
	 * @param stringBase64Encoded
	 * @return String
	 */
	public String decodeBase64ToString(String stringBase64Encoded) {
		byte[] stringBase64Decoded = Base64Utils.decodeFromString(stringBase64Encoded);
        return new String(stringBase64Decoded);
	}

}
