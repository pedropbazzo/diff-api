package io.github.mariazevedo88.diffapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.dto.MessageDiffDTO;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.repository.diff.MessageDiffRepository;
import io.github.mariazevedo88.diffapi.service.MessageDiffService;

@Service
public class MessageDiffServiceImpl implements MessageDiffService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageDiffServiceImpl.class);
	
	@Autowired
	MessageDiffRepository repository;

	@Override
	public MessageDiff saveMessageDiff(MessageDiffDTO messageDiffDTO) {
		MessageDiff message = convertDTOToEntity(messageDiffDTO);
		return repository.saveAndFlush(message);
	}

	@Override
	public MessageDiff convertDTOToEntity(MessageDiffDTO dto) {

		MessageDiff message = new MessageDiff();
		message.setId(dto.getId());
		message.setOffset(dto.getOffset());
		message.setLength(dto.getLength());
		
		return message;
	}

	@Override
	public MessageDiffDTO convertEntityToDTO(MessageDiff message) {

		MessageDiffDTO messageDTO = new MessageDiffDTO();
		messageDTO.setId(message.getId());
		messageDTO.setOffset(message.getOffset());
		messageDTO.setLength(message.getLength());
		
		return messageDTO;
	}

	@Override
	public MessageDiff checkDiffBetweenWords(Message message) {

		MessageDiff diff = null;
		
		AtomicInteger length = new AtomicInteger(0);
		AtomicInteger offset = new AtomicInteger(-1);
		AtomicInteger count = new AtomicInteger(0);

        List<Character> leftMessageCharList = message.getLeftData().chars()
        		.mapToObj(c -> (char) c).collect(Collectors.toList());
        leftMessageCharList.stream().forEach(c -> {
        	if (count.intValue() < message.getLeftData().length() && c != message.getRightData().charAt(count.intValue())) {
                length.addAndGet(1);
                if (offset.intValue() < 0) {
                	offset.set(count.intValue());
                }
            }
        	count.addAndGet(1);
        });
        
        if (offset.intValue() != -1) {
            diff = new MessageDiff(offset.intValue(), length.intValue());
        }
        
        if(logger.isInfoEnabled() && diff != null){
            logger.info(diff.toString());
        }
        
        return diff;
	}

	@Override
	public Optional<MessageDiff> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public List<MessageDiff> findAll() {
		return repository.findAll();
	}

}
