package io.github.mariazevedo88.diffapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.repository.MessageRepository;
import io.github.mariazevedo88.diffapi.service.MessageService;

/**
 * Service that implements methods related to base64 string comparison functionality.
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageRepository repository;
	
	@Override
	public Message convertDTOToEntity(MessageDTO dto) {
		
		Message message = new Message();
		message.setId(dto.getId());
		message.setLeftData(dto.getLeftData());
		message.setRightData(dto.getRightData());
		
		return message;
	}
	
	@Override
	public MessageDTO convertEntityToDTO(Message message) {
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setId(message.getId());
		messageDTO.setLeftData(message.getLeftData());
		messageDTO.setRightData(message.getRightData());
		
		return messageDTO;
	}
	
	@Override
	public Message save(Message message) {
		return repository.save(message);
	}
	
	@Override
	public Message updateLeftData(Message message) {
		repository.updateLeftData(message.getLeftData(), message.getId());
		return message;
	}

	@Override
	public Message updateRightData(Message message) {
		repository.updateRightData(message.getRightData(), message.getId());
		return message;
	}
	
	@Override
	public Optional<Message> findById(Long messageId) {
		return repository.findById(messageId);
	}
	
	@Override
	public List<Message> convertListDTOToListEntity(List<MessageDTO> dto) {
		List<Message> messages = new ArrayList<>();
		messages = dto.stream()
		        .map(m -> new Message(m.getId(), m.getLeftData(), m.getRightData()))
		        .collect(Collectors.toList());
		return messages;
	}

	@Override
	public List<MessageDTO> convertListEntityToListDTO(List<Message> message) {
		List<MessageDTO> messages = new ArrayList<>();
		messages = message.stream()
		        .map(m -> new MessageDTO(m.getId(), m.getLeftData(), m.getRightData()))
		        .collect(Collectors.toList());
		return messages;
	}
	
	@Override
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	@Override
	public List<Message> findAll() {
		return repository.findAll();
		
	}
}
