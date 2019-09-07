package io.github.mariazevedo88.diffapi.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.factory.generic.JSONMessageFactory;
import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.model.ResultDiff;

/**
 * Service that implements methods related to base64 string comparison functionality.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@Service
public class ComparatorService {
	
	private static final Logger logger = Logger.getLogger(ComparatorService.class);
	
	private JSONMessageFactory factory;
	
	public void setJSONMessageFactory(JSONMessageFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Method that compares two data sequences according to an informed id. The comparison only happens if both 
	 * messages/inputs (left and right) are found for the same id.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/07/2019
	 * @param id
	 * @return <p>Null if both or at least one input (left or right) are null.
     * <p>ResultDiff with all comparison data.
	 */
	public ResultDiff compare(long id) {
		
        JSONMessage leftMessage = factory.getLeftJSONMessage(id);
        JSONMessage rightMessage = factory.getRightJSONMessage(id);
        
        logger.info(this.getClass() + ": [id = " + id + ", left = " + leftMessage.getValue() +
        		", right = " + rightMessage.getValue() + "]");
        
        ResultDiff result = new ResultDiff(id);
        if (leftMessage.getValue().equals(rightMessage.getValue())) {
            result.setResult(ResultDiffEnum.EQUAL);
        } else if (leftMessage.getValue().length() != rightMessage.getValue().length()) {
        	 result.setResult(ResultDiffEnum.DIFFERENT_SIZE);
        } else {
        	result.setResult(ResultDiffEnum.DIFFERENT);
        	result.setDiffs(checkDiffBetweenWords(leftMessage, rightMessage));
        }
        
        return result;
	}
	
	/**
	 * Method that compares two sequences of JSON base64 data and computes the offsets and 
     * lengths of the differences.
     * 
     * @author Mariana Azevedo
	 * @since 25/07/2019
	 * 
     * @param leftMessage data came from left endpoint.
     * @param rightMessage data came from right endpoint.
     * @return a list of {@code MessageDiff} with offsets and lengths of the differences.
	 */
	public List<MessageDiff> checkDiffBetweenWords(JSONMessage leftMessage, JSONMessage rightMessage) {
        
		List<MessageDiff> diffList = new LinkedList<>();
		AtomicInteger length = new AtomicInteger(0);
		AtomicInteger offset = new AtomicInteger(-1);
		AtomicInteger count = new AtomicInteger(0);

        List<Character> leftMessageCharList = leftMessage.getValue().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        leftMessageCharList.forEach(c -> {
        	if (count.intValue() < leftMessage.getValue().length() && c != rightMessage.getValue().charAt(count.intValue())) {
                length.addAndGet(1);
                if (offset.intValue() < 0) {
                	offset.set(count.intValue());
                }
            }
        	count.addAndGet(1);
        });
        
        if (offset.intValue() != -1) {
            diffList.add(new MessageDiff(offset.intValue(), length.intValue()));
        }
        
        logger.info("Diffs: " + diffList);
        
        return diffList;
    }

}
