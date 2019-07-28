package io.github.mariazevedo88.diffapi.repository.generic;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.mariazevedo88.diffapi.model.JSONMessage;

/**
 * Interface that provides methods for storing and accessing data (String or StringBase64).
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@Repository
public interface IRepository{
	
	/**
     * Method that returns the {@code JSONMessage} data that was created from /v1/diff/{id}/left endpoint
     * 
     * @author Mariana Azevedo
     * @since 23/07/2019
     * 
     * @param id
     * @return JSONMessage object with the id and the message created for this id.
     */
	public JSONMessage getLeftJSONMessage(Long id);
	
	/**
	 * Method that creates a {@code JSONMessage} from the /v1/diff/{id}/left endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @param jsonMessage
	 */
	public void createLeftJSONMessage(JSONMessage jsonMessage);
	
	/**
	 * Method that returns the {@code JSONMessage} data that was created from /v1/diff/{id}/right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @param id
	 * @return JSONMessage object with the id and the message created for this id.
	 */
	public JSONMessage getRightJSONMessage(Long id);
	
	/**
	 * Method that creates a {@code JSONMessage} from the /v1/diff/{id}/right endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @param jsonMessage
	 */
	public void createRightJSONMessage(JSONMessage jsonMessage);
	
	/**
	 * Method that list all left {@code JSONMessage} created using /v1/diff/left/all endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @return List
	 */
	public List<JSONMessage> getAllLeftMessages();
	
	/**
	 * Method that list all right {@code JSONMessage} created using /v1/diff/right/all endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @return List
	 */
	public List<JSONMessage> getAllRightMessages();
	
	/**
	 * Method that list all {@code JSONMessage} created using / endpoint
	 * 
	 * @author Mariana Azevedo
     * @since 23/07/2019
     * 
	 * @return List
	 */
	public List<JSONMessage> getAllMessages();

}
