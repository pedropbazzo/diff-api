package io.github.mariazevedo88.diffapi.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;

@Service
public class ComparatorService {
	
	private static final Logger logger = Logger.getLogger(ComparatorService.class);
	
	public ResultDiff compare(long id) {
		
        JSONMessage leftMessage = JSONMessageRepository.getInstance().getLeftJSONMessage(id);
        JSONMessage rightMessage = JSONMessageRepository.getInstance().getRightJSONMessage(id);
        
        ResultDiff result = null;

        logger.info(this.getClass() + ": [id = " + id + ", left = " + leftMessage.getValue() +
        		", right = " + rightMessage.getValue() + "]");
        
        if (leftMessage != null && rightMessage != null) {
        	result = new ResultDiff(id);
            if (leftMessage.getValue().equals(rightMessage.getValue())) {
                result.setResult(ResultDiffEnum.EQUAL);
            } else if (leftMessage.getValue().length() != rightMessage.getValue().length()) {
            	 result.setResult(ResultDiffEnum.DIFFERENT_SIZE);
            } else {
            	result.setResult(ResultDiffEnum.DIFFERENT);
            	result.setDiffs(checkDiffBetweenWords(leftMessage, rightMessage));
            }
        }
        
        return result;
	}
	
	public List<MessageDiff> checkDiffBetweenWords(JSONMessage inputLeft, JSONMessage inputRight) {
        
		List<MessageDiff> diffList = new LinkedList<>();
		AtomicInteger length = new AtomicInteger(0);
		AtomicInteger offset = new AtomicInteger(-1);
		AtomicInteger i = new AtomicInteger(0);

        List<Character> collect = inputLeft.getValue().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        collect.stream().forEach(c -> {
        	if (i.intValue() < inputLeft.getValue().length() && c != inputRight.getValue().charAt(i.intValue())) {
                length.addAndGet(1);
                if (offset.intValue() < 0) {
                	offset.set(i.intValue());
                }
            }
        	i.addAndGet(1);
        });
        
        if (offset.intValue() != -1) {
            diffList.add(new MessageDiff(offset.intValue(), length.intValue()));
        }
        return diffList;
    }

}
