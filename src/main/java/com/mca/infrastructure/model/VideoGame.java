package com.mca.infrastructure.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "videogame")
@SequenceGenerator(name = "sequence", sequenceName = "videogame_sequence", allocationSize = 1)
@NoArgsConstructor
@Getter
@Setter
public class VideoGame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8966595364104477617L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence")
	private Long id;

	private String title;

	@OneToOne(mappedBy = "videoGame", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Stock stock;

	@OneToMany(mappedBy = "videoGame", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("valid_from DESC")
	private Set<Promotion> promotions = new HashSet<>();

}
