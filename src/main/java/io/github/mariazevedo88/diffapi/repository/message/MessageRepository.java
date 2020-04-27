package io.github.mariazevedo88.diffapi.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.github.mariazevedo88.diffapi.model.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Modifying
	@Query("update Message m set m.leftData = :leftData where m.id = :messageId")
	@Transactional(rollbackFor=Exception.class)
	void updateLeftData(@Param("leftData") String leftData, @Param("messageId") Long messageId);
	
	@Modifying
	@Query("update Message m set m.rightData = :rightData where m.id = :messageId")
	@Transactional(rollbackFor=Exception.class)
	void updateRightData(@Param("rightData") String rightData, @Param("messageId") Long messageId);
}
