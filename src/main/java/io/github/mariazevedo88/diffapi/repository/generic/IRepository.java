package io.github.mariazevedo88.diffapi.repository.generic;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.mariazevedo88.diffapi.model.JSONMessage;

@Repository
public interface IRepository{
	
	public JSONMessage getLeftJSONMessage(Long id);
	
	public void saveLeftJSONMessage(JSONMessage jsonMessage);
	
	public JSONMessage getRightJSONMessage(Long id);
	
	public void saveRightJSONMessage(JSONMessage jsonMessage);
	
	public List<JSONMessage> getAllLeftMessages();
	
	public List<JSONMessage> getAllRightMessages();
	
	public List<JSONMessage> getAllMessages();

}
