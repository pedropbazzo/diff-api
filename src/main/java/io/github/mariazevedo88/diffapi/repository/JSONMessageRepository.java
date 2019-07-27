package io.github.mariazevedo88.diffapi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.repository.generic.IRepository;

public class JSONMessageRepository implements IRepository {
	
	private static IRepository instance;
	
	private Map<Long, JSONMessage> leftMessage = new HashMap<>();
    private Map<Long, JSONMessage> rightMessage = new HashMap<>();
    
    public static IRepository getInstance(){
        if(instance == null){
            instance = new JSONMessageRepository();
        }
        return instance;
    }

	@Override
	public JSONMessage getLeftJSONMessage(Long id) {
		return leftMessage.get(id);
	}

	@Override
	public void saveLeftJSONMessage(JSONMessage jsonMessage) {
		leftMessage.put(jsonMessage.getId(), jsonMessage);
	}

	@Override
	public JSONMessage getRightJSONMessage(Long id) {
		return rightMessage.get(id);
	}

	@Override
	public void saveRightJSONMessage(JSONMessage jsonMessage) {
		rightMessage.put(jsonMessage.getId(), jsonMessage);
	}

	@Override
	public List<JSONMessage> getAllLeftMessages() {
		return leftMessage.values().stream().collect(Collectors.toList());
	}

	@Override
	public List<JSONMessage> getAllRightMessages() {
		return rightMessage.values().stream().collect(Collectors.toList());
	}

	@Override
	public List<JSONMessage> getAllMessages() {
		List<JSONMessage> allMessages = new ArrayList<>();
		allMessages.addAll(getAllLeftMessages());
		allMessages.addAll(getAllRightMessages());
		return allMessages;
	}

}
