package io.github.mariazevedo88.diffapi.factory.generic;

import java.util.List;
import java.util.Map;

import io.github.mariazevedo88.diffapi.model.JSONMessage;

/**
 * Interface that provides methods for storing and accessing data (String or StringBase64).
 * 
 * @author Mariana Azevedo
 * @since 01/09/2019
 */
public interface JSONMessageFactory {

	/**
	 * Method that creates a {@code JSONMessage}
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @param id
	 * @param value
	 * 
	 * @return JSONMessage
	 */
	JSONMessage createMessage(Long id, String value);
	
	/**
	 * Method that creates the left endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @return JSONMessage
	 */
	void createLeftEndpoint();
	
	/**
	 * Method that creates the right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @return JSONMessage
	 */
	void createRightEndpoint();
	
	/**
     * Method that sets the left endpoint
     * 
     * @author Mariana Azevedo
     * @since 07/09/2019
     * 
     * @param Map<Long, JSONMessage>
     */
	void setLeftEndpoint(Map<Long, JSONMessage> leftMessages);
	
	/**
     * Method that sets the right endpoint
     * 
     * @author Mariana Azevedo
     * @since 07/09/2019
     * 
     * @param Map<Long, JSONMessage>
     */
	void setRightEndpoint(Map<Long, JSONMessage> rightMessages);
	
	/**
	 * Method that inserts a {@code JSONMessage} from the /v1/diff/{id}/left endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @param jsonMessage
	 */
	void saveJSONMessageInLeftEndpoint(JSONMessage jsonMessage);
	
	/**
	 * Method that inserts a {@code JSONMessage} from the /v1/diff/{id}/right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @param jsonMessage
	 */
	void saveJSONMessageInRightEndpoint(JSONMessage jsonMessage);
	
	/**
     * Method that returns the {@code JSONMessage} data that was saved from /v1/diff/{id}/left endpoint
     * 
     * @author Mariana Azevedo
     * @since 01/09/2019
     * 
     * @param id
     * @return JSONMessage object with the id and the message saved for this id.
     */
	JSONMessage getLeftJSONMessage(Long id);
	
	/**
	 * Method that returns the {@code JSONMessage} data that was saved from /v1/diff/{id}/right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @param id
	 * @return JSONMessage object with the id and the message saved for this id.
	 */
	JSONMessage getRightJSONMessage(Long id);
	
	/**
	 * Method that list all left {@code JSONMessage} saved using /v1/diff/left/all endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @return List
	 */
	List<JSONMessage> getAllLeftMessages();
	
	/**
	 * Method that list all right {@code JSONMessage} saved using /v1/diff/right/all endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @return List
	 */
	public List<JSONMessage> getAllRightMessages();
	
	/**
	 * Method that list all {@code JSONMessage} created using/endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 * @return List
	 */
	public List<JSONMessage> getAllMessages();
	
	/**
	 * Method that clean all {@code JSONMessage} saved in the left endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 */
	boolean cleanLeftMessages();
	
	/**
	 * Method that clean all {@code JSONMessage} saved in the right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 */
	boolean cleanRightMessages();
	
	/**
	 * Method that clean all {@code JSONMessage} saved
	 * 
	 * @author Mariana Azevedo
     * @since 01/09/2019
     * 
	 */
	boolean cleanAllMessages();
}
