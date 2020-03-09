package io.github.mariazevedo88.diffapi.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.dto.ResultDiffDTO;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.ResultDiffRepository;
import io.github.mariazevedo88.diffapi.service.ResultDiffService;

@Service
public class ResultDiffServiceImpl implements ResultDiffService {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultDiffServiceImpl.class);
	
	@Autowired
	ResultDiffRepository repository;

	@Override
	public ResultDiff saveResultDiff(ResultDiffDTO resultDiffDTO) {
		ResultDiff message = convertDTOToEntity(resultDiffDTO);
		return repository.save(message);
	}

	@Override
	public ResultDiff convertDTOToEntity(ResultDiffDTO dto) {

		ResultDiff message = new ResultDiff();
		message.setId(dto.getId());
		message.setResult(dto.getResult());
		message.setMessage(dto.getMessage());
		message.setDiff(dto.getDiff());
		
		return message;
	}

	@Override
	public ResultDiffDTO convertEntityToDTO(ResultDiff message) {
		
		ResultDiffDTO messageDTO = new ResultDiffDTO();
		messageDTO.setId(message.getId());
		messageDTO.setResult(message.getResult());
		messageDTO.setMessage(message.getMessage());
		messageDTO.setDiff(message.getDiff());
		
		return messageDTO;
	}

	@Override
	public ResultDiff compare(Message message) {
		
        String leftData = message.getLeftData();
        String rightData = message.getRightData();
        
        logger.info(this.getClass() + ": [id = " + message.getId() + ", left = " + leftData + ", right = " + rightData + "]");
        
        ResultDiff result = new ResultDiff(message.getId());
        if (leftData.equals(rightData)) {
            result.setResult(ResultDiffEnum.EQUAL);
        } else if (leftData.length() != rightData.length()) {
        	 result.setResult(ResultDiffEnum.DIFFERENT_SIZE);
        } else {
        	result.setResult(ResultDiffEnum.DIFFERENT);
        }
        
        return result;
	}

	@Override
	public Optional<ResultDiff> findById(long id) {
		return repository.findById(id);
	}

}
