package io.github.mariazevedo88.diffapi.repository.diff;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.diffapi.model.ResultDiff;

public interface ResultDiffRepository extends JpaRepository<ResultDiff, Long> {

}
