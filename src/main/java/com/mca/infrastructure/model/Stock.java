package com.mca.infrastructure.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock")
@SequenceGenerator(name = "sequence", sequenceName = "stock_sequence", allocationSize = 1)
@NoArgsConstructor
@Getter
@Setter
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373760605085198644L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence")
	private Long id;

	private boolean availability;

	private Timestamp last_updated;

	@JsonIgnore
	@JoinColumn(name = "videogame_id")
	@OneToOne
	private VideoGame videoGame;

}
