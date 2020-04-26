package io.github.mariazevedo88.diffapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.repository.message.MessageRepository;
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
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Message.class);
	}
	
	@Override
	public MessageDTO convertEntityToDTO(Message message) {
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(message, MessageDTO.class);
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
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	@Override
	public List<Message> findAll() {
		return repository.findAll();
		
	}
}
