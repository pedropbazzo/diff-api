package io.github.mariazevedo88.diffapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.diffapi.model.MessageDiff;

public interface MessageDiffRepository extends JpaRepository<MessageDiff, Long> {

}
