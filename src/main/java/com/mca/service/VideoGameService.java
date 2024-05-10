package com.mca.service;

import java.sql.Timestamp;
import java.util.List;

import com.mca.dto.VideoGameDto;
import com.mca.infrastructure.model.VideoGame;

public interface VideoGameService {

	/***
	 * Find a videogame given a id
	 * 
	 * @param idVideoGame
	 * @return VideoGame
	 */
	public VideoGame findById(Long idVideoGame);

	/***
	 * Find all videogames storage in database
	 * 
	 * @return List<VideoGame>
	 */
	public List<VideoGame> findAll();

	/***
	 * Save videogame given videoGameDto object
	 * 
	 * @param videoGameDto
	 * @return
	 * @throws Exception
	 */
	public VideoGame save(VideoGameDto videoGameDto) throws Exception;

	/***
	 * Change the stock of a videogame given an id and availability and send stock message using kafka previously configurated
	 * 
	 * @param idVideoGame
	 * @param availability
	 * @return VideoGame
	 */
	public VideoGame changeStock(Long idVideoGame, Boolean availability);

	/***
	 * Add promotion to a videogame given an id, price and valid from date
	 * 
	 * @param idVideoGame
	 * @param price
	 * @param validFrom
	 * @return VideoGame
	 */
	public VideoGame addPromotion(Long idVideoGame, Double price, Timestamp validFrom);

	/***
	 * Delete a videogame by a given id
	 * 
	 * @param idVideoGame
	 * @return Boolean
	 */
	public Boolean deleteById(Long idVideoGame);

}
