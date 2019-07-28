package io.github.mariazevedo88.diffapi.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

/**
 * Service that implements methods related to base64 string encoding functionality.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@Service
public class EncodingService {
	
	private static final String BASE64RGX = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
	
	/**
	 * Method that implements base64 encoding to a normal string.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 *  
	 * @param stringInBytes
	 * @return String
	 */
	public String encodeToBase64(byte[] stringInBytes) {
		return Base64Utils.encodeToString(stringInBytes);
	}
	
	/**
	 * Method that checks if the string is base64 encoded.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param stringToBeChecked
	 * @return boolean
	 */
	public boolean isEncodedBase64(String stringToBeChecked) {
		return stringToBeChecked.matches(BASE64RGX);
	}
}
