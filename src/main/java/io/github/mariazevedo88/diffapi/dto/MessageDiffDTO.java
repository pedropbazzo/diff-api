package io.github.mariazevedo88.diffapi.dto;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MessageDiffDTO extends RepresentationModel<MessageDiffDTO>{

	private Long id;
	
	@NotNull(message = "Offset cannot be null")
	private int offset;
	
	@NotNull(message = "Length cannot be null")
	private int length;
}
