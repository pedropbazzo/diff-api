package io.github.mariazevedo88.diffapi.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MessageDiffDTO {

	private Long id;
	@NotNull(message = "Offset cannot be null")
	private int offset;
	@NotNull(message = "Length cannot be null")
	private int length;
}
