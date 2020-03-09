package io.github.mariazevedo88.diffapi.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements a ResultDiff structure with an id, a diff result and the list of the diffs found.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "result_diff")
public class ResultDiff implements Serializable {

	private static final long serialVersionUID = 6077656822481760215L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "dfresult", nullable = false)
	@Enumerated(EnumType.STRING)
	private ResultDiffEnum result;
	@JoinColumn(name = "message", referencedColumnName = "id")
	@OneToOne(fetch = FetchType.LAZY)
	private Message message;
	@JoinColumn(name = "diff", referencedColumnName = "id")
	@OneToOne(cascade = {CascadeType.ALL})
	private MessageDiff diff;
	
	public ResultDiff(Long id) {
		this.id = id;
	}

}
