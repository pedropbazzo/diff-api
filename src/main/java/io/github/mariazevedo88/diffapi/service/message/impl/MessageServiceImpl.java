package io.github.mariazevedo88.diffapi.service.message.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.dto.model.message.MessageDTO;
import io.github.mariazevedo88.diffapi.model.message.Message;
import io.github.mariazevedo88.diffapi.repository.message.MessageRepository;
import io.github.mariazevedo88.diffapi.service.message.MessageService;

/**
 * Service that implements methods for manipulating Message objects in the API. 
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageRepository repository;
	
	/**
	 * @see MessageService#convertDTOToEntity(MessageDTO)
	 */
	@Override
	public Message convertDTOToEntity(MessageDTO dto) {
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Message.class);
	}
	
	/**
	 * @see MessageService#convertEntityToDTO(Message)
	 */
	@Override
	public MessageDTO convertEntityToDTO(Message message) {
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(message, MessageDTO.class);
	}
	
	/**
	 * @see MessageService#save(Message)
	 */
	@Override
	public Message save(Message message) {
		return repository.save(message);
	}
	
	/**
	 * @see MessageService#updateLeftData(Message)
	 */
	@Override
	public Message updateLeftData(Message message) {
		repository.updateLeftData(message.getLeftData(), message.getId());
		return message;
	}

	/**
	 * @see MessageService#updateRightData(Message)
	 */
	@Override
	public Message updateRightData(Message message) {
		repository.updateRightData(message.getRightData(), message.getId());
		return message;
	}
	
	/**
	 * @see MessageService#findById(Long)
	 */
	@Override
	public Optional<Message> findById(Long messageId) {
		return repository.findById(messageId);
	}
	
	/**
	 * @see MessageService#exist(long)
	 */
	@Override
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	/**
	 * @see MessageService#findAll()
	 */
	@Override
	public List<Message> findAll() {
		return repository.findAll();
	}
}
