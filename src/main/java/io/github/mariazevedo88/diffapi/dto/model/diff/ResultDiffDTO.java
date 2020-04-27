package io.github.mariazevedo88.diffapi.dto.model.diff;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.diffapi.model.diff.MessageDiff;
import io.github.mariazevedo88.diffapi.model.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.message.Message;
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
