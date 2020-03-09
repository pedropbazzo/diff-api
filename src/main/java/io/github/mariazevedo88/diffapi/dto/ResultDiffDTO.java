package io.github.mariazevedo88.diffapi.dto;

import javax.validation.constraints.NotNull;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultDiffDTO {

	private Long id;
	@NotNull(message = "Result cannot be null")
	private ResultDiffEnum result;
	@NotNull(message = "Message cannot be null")
	private Message message;
	private MessageDiff diff;
}
