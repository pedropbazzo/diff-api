package io.github.mariazevedo88.diffapi.service;

import java.util.List;
import java.util.Optional;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.model.Message;

public interface MessageService {
	
	Message save(Message message);
	
	boolean exist(long id);
	
	Message updateLeftData(Message message);
	
	Message updateRightData(Message message);

	Optional<Message> findById(Long messageId);
	
	List<Message> findAll();
	
	Message convertDTOToEntity(MessageDTO dto);
	
	MessageDTO convertEntityToDTO(Message message);
	
	List<Message> convertListDTOToListEntity(List<MessageDTO> dto);
	
	List<MessageDTO> convertListEntityToListDTO(List<Message> message);

}
