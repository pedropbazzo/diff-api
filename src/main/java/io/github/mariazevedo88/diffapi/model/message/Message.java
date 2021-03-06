package io.github.mariazevedo88.diffapi.model.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that implements a message structure with an value
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message implements Serializable {
	
	private static final long serialVersionUID = 6198714202345911094L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private String leftData;
	@Column(nullable = true)
	private String rightData;
	
	public Message(String leftData, String rightData) {
		this.leftData = leftData;
		this.rightData = rightData;
	}
	
}
