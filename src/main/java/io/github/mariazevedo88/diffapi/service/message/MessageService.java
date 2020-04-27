package io.github.mariazevedo88.diffapi.service.message;

import java.util.List;
import java.util.Optional;

import io.github.mariazevedo88.diffapi.dto.model.message.MessageDTO;
import io.github.mariazevedo88.diffapi.model.message.Message;

/**
 * Service interface that provides methods for manipulating Message objects.
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
public interface MessageService {
	
	/**
	 * Method to save a message
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param message
	 * @return Message object
	 */
	Message save(Message message);
	
	/**
	 * Method to verify if the message exist in database
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param id
	 * @return boolean
	 */
	boolean exist(long id);
	
	/**
	 * Method to update the left data of a message
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param message
	 * @return Message object
	 */
	Message updateLeftData(Message message);
	
	/**
	 * Method to update the right data of a message
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param message
	 * @return Message object
	 */
	Message updateRightData(Message message);

	/**
	 * Method to find a message by id
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param messageId
	 * @return Optional<Message>
	 */
	Optional<Message> findById(Long messageId);
	
	/**
	 * Method to list all messages available
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @return List<Message>
	 */
	List<Message> findAll();
	
	/**
	 * Method to convert a DTO object into an Entity object
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param dto
	 * @return Message object
	 */
	Message convertDTOToEntity(MessageDTO dto);
	
	/**
	 * Method to convert a Entity object into an DTO object
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @param message
	 * @return MessageDTO object
	 */
	MessageDTO convertEntityToDTO(Message message);
	
}
