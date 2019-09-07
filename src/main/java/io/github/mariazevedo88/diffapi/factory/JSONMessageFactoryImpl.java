package io.github.mariazevedo88.diffapi.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.mariazevedo88.diffapi.factory.generic.JSONMessageFactory;
import io.github.mariazevedo88.diffapi.model.JSONMessage;

/**
 * Class that provides simple storage in the RAM memory to JSON base64 data. It was implemented in a Abstract Factory pattern, 
 * just to emulate data storage while the service is running.
 * 
 * @author Mariana Azevedo
 * @since 01/09/2019
 */
public class JSONMessageFactoryImpl implements JSONMessageFactory{
	
	private Map<Long, JSONMessage> leftMessages;
    private Map<Long, JSONMessage> rightMessages;

	@Override
	public JSONMessage createMessage(Long id, String value) {
		return new JSONMessage(id, value);
	}

	@Override
	public void saveJSONMessageInLeftEndpoint(JSONMessage jsonMessage) {
		leftMessages.put(jsonMessage.getId(), jsonMessage);
	}

	@Override
	public void saveJSONMessageInRightEndpoint(JSONMessage jsonMessage) {
		rightMessages.put(jsonMessage.getId(), jsonMessage);
	}

	@Override
	public JSONMessage getLeftJSONMessage(Long id) {
		return leftMessages.get(id);
	}

	@Override
	public JSONMessage getRightJSONMessage(Long id) {
		return rightMessages.get(id);
	}

	@Override
	public List<JSONMessage> getAllLeftMessages() {
		return leftMessages != null ? 
		leftMessages.values().stream().collect(Collectors.toList()) : Stream.<JSONMessage>empty().collect(Collectors.toList());
	}

	@Override
	public List<JSONMessage> getAllRightMessages() {
		return rightMessages != null ?
		rightMessages.values().stream().collect(Collectors.toList()) : Stream.<JSONMessage>empty().collect(Collectors.toList());
	}

	@Override
	public boolean cleanLeftMessages() {
		if(leftMessages != null) {
			leftMessages.clear();
			return leftMessages.isEmpty();
		}
		return false;
	}

	@Override
	public boolean cleanRightMessages() {
		if(rightMessages != null) {
			rightMessages.clear();
			return rightMessages.isEmpty();
		}
		return false;
	}

	@Override
	public boolean cleanAllMessages() {
		return cleanLeftMessages() && cleanRightMessages();
	}

	@Override
	public void createLeftEndpoint() {
		leftMessages = new HashMap<>(); 
	}

	@Override
	public void createRightEndpoint() {
		rightMessages = new HashMap<>();
	}

	@Override
	public List<JSONMessage> getAllMessages() {
		List<JSONMessage> allMessages = new ArrayList<>();
		allMessages.addAll(getAllLeftMessages());
		allMessages.addAll(getAllRightMessages());
		return allMessages;
	}

	public Map<Long, JSONMessage> getLeftMessage() {
		return leftMessages;
	}

	public Map<Long, JSONMessage> getRightMessage() {
		return rightMessages;
	}

	@Override
	public void setLeftEndpoint(Map<Long, JSONMessage> leftMessages) {
		this.leftMessages = leftMessages;
	}

	@Override
	public void setRightEndpoint(Map<Long, JSONMessage> rightMessages) {
		this.rightMessages = rightMessages;
	}

}
