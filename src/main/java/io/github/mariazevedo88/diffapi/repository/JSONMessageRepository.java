package io.github.mariazevedo88.diffapi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.repository.generic.IRepository;

/**
 * Class that provides simple storage in the RAM memory to JSON base64 data. It was implemented in a singleton pattern, 
 * just to emulate data storage while the service is running.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 */
public class JSONMessageRepository implements IRepository {
	
	private static IRepository instance;
	
	private Map<Long, JSONMessage> leftMessage = new HashMap<>();
    private Map<Long, JSONMessage> rightMessage = new HashMap<>();
    
    private List<JSONMessage> allMessages = new ArrayList<>();
    
    /**
     * Method that creates or return an instance of JSONMessageRepository
     * 
     * @author Mariana Azevedo
     * @since 25/07/2019
     * 
     * @return IRepository instance
     */
    public static IRepository getInstance(){
        if(instance == null){
            instance = new JSONMessageRepository();
        }
        return instance;
    }

    /**
     * @see IRepository#getLeftJSONMessage
     */
	@Override
	public JSONMessage getLeftJSONMessage(Long id) {
		return leftMessage.get(id);
	}

	/**
	 * @see IRepository#createLeftJSONMessage
	 */
	@Override
	public void createLeftJSONMessage(JSONMessage jsonMessage) {
		leftMessage.put(jsonMessage.getId(), jsonMessage);
	}

	/**
	 * @see IRepository#getRightJSONMessage
	 */
	@Override
	public JSONMessage getRightJSONMessage(Long id) {
		return rightMessage.get(id);
	}

	/**
	 * @see IRepository#createRightJSONMessage
	 */
	@Override
	public void createRightJSONMessage(JSONMessage jsonMessage) {
		rightMessage.put(jsonMessage.getId(), jsonMessage);
	}

	/**
	 * @see IRepository#getAllLeftMessages
	 */
	@Override
	public List<JSONMessage> getAllLeftMessages() {
		return leftMessage.values().stream().collect(Collectors.toList());
	}

	/**
	 * @see IRepository#getAllRightMessages
	 */
	@Override
	public List<JSONMessage> getAllRightMessages() {
		return rightMessage.values().stream().collect(Collectors.toList());
	}

	/**
	 * @see IRepository#getAllMessages
	 */
	@Override
	public List<JSONMessage> getAllMessages() {
		allMessages.addAll(getAllLeftMessages());
		allMessages.addAll(getAllRightMessages());
		return allMessages;
	}

}
