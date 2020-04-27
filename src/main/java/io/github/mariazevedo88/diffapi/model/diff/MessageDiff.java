package io.github.mariazevedo88.diffapi.model.diff;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that implements a MessageDiff structure with an offset and a length
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "message_diff")
public class MessageDiff implements Serializable {

	private static final long serialVersionUID = 190102684474375198L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "dfoffset", nullable = true)
	private int offset;
	@Column(name = "dflength", nullable = true)
	private int length;
	
	public MessageDiff(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}
}
