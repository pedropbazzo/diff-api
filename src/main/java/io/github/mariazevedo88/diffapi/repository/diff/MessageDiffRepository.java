package io.github.mariazevedo88.diffapi.repository.diff;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.diffapi.model.diff.MessageDiff;

public interface MessageDiffRepository extends JpaRepository<MessageDiff, Long> {

}
