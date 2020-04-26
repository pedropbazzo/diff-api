package io.github.mariazevedo88.diffapi.dto;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ResultDiffDTO extends RepresentationModel<ResultDiffDTO> {

	private Long id;
	
	@NotNull(message = "Result cannot be null")
	private ResultDiffEnum result;
	
	@NotNull(message = "Message cannot be null")
	private Message message;
	
	private MessageDiff diff;
}
