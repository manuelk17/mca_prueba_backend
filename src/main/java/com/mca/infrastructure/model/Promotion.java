package com.mca.infrastructure.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "promotion")
@SequenceGenerator(name = "sequence", sequenceName = "promotion_sequence", allocationSize = 1)
@NoArgsConstructor
@Getter
@Setter
public class Promotion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3808559810753019518L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence")
	private Long id;

	private Timestamp valid_from;

	private Double price;

	@JsonIgnore
	@JoinColumn(name = "videogame_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private VideoGame videoGame;

}
