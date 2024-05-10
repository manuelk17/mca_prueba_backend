package com.mca.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mca.config.KafkaConfig;
import com.mca.dto.VideoGameDto;
import com.mca.exception.BadRequestException;
import com.mca.infrastructure.model.Promotion;
import com.mca.infrastructure.model.Stock;
import com.mca.infrastructure.model.VideoGame;
import com.mca.repository.VideoGameRepository;
import com.mca.service.VideoGameService;

@Service("VideoGameService")
@Transactional
public class VideoGameServiceImpl implements VideoGameService {

	@Autowired
	private VideoGameRepository videoGameRepository;

	@Autowired
	private KafkaConfig kafkaConfig;

	@Override
	public VideoGame findById(Long idVideoGame) {

		Optional<VideoGame> videoGameDB = videoGameRepository.findById(idVideoGame);
		if (videoGameDB.isPresent()) {
			return videoGameDB.get();
		} else {
			return null;
		}

	}

	@Override
	public List<VideoGame> findAll() {
		return videoGameRepository.findAll();
	}

	@Override
	public VideoGame save(VideoGameDto videoGameDto) throws Exception {

		this.checkIfObligatoryFieldsAreNull(videoGameDto);
		VideoGame videoGame = new VideoGame();
		videoGame.setTitle(videoGameDto.getTitle());
		this.setStockToVideoGame(videoGame, videoGameDto.getAvailability());
		this.addPromotionToVideoGame(videoGame, videoGameDto.getPrice(), Timestamp.from(Instant.now()));
		return videoGameRepository.save(videoGame);

	}

	@Override
	public VideoGame changeStock(Long idVideoGame, Boolean availability) {

		Optional<VideoGame> videoGameDB = videoGameRepository.findById(idVideoGame);
		if (videoGameDB.isPresent()) {
			this.setStockToVideoGame(videoGameDB.get(), availability);
			kafkaConfig.sendStockMessage(videoGameDB.get());
			return videoGameRepository.save(videoGameDB.get());
		} else {
			return null;
		}

	}

	@Override
	public VideoGame addPromotion(Long idVideoGame, Double price, Timestamp validFrom) {

		Optional<VideoGame> videoGameDB = videoGameRepository.findById(idVideoGame);
		if (videoGameDB.isPresent()) {
			this.addPromotionToVideoGame(videoGameDB.get(), price, validFrom);
			return videoGameRepository.save(videoGameDB.get());
		} else {
			return null;
		}

	}

	@Override
	public Boolean deleteById(Long idVideoGame) {

		if (videoGameRepository.findById(idVideoGame).isPresent()) {
			videoGameRepository.deleteById(idVideoGame);
			return true;
		} else {
			return false;
		}

	}

	/***
	 * Change the stock of a videogame
	 * 
	 * @param videoGame
	 * @param availability
	 */
	private void setStockToVideoGame(VideoGame videoGame, Boolean availability) {

		Stock stock = new Stock();
		stock.setAvailability(availability);
		stock.setLast_updated(Timestamp.from(Instant.now()));
		stock.setVideoGame(videoGame);
		videoGame.setStock(stock);

	}

	/**
	 * Add promotion to videogame
	 * 
	 * @param videoGame
	 * @param price
	 * @param validFrom
	 */
	private void addPromotionToVideoGame(VideoGame videoGame, Double price, Timestamp validFrom) {

		Promotion promotion = new Promotion();
		promotion.setPrice(price);
		promotion.setValid_from(validFrom);
		promotion.setVideoGame(videoGame);
		videoGame.getPromotions().add(promotion);

	}

	/***
	 * Check if obligatory fields (title, price or availability) are null
	 * 
	 * @param videoGameDto
	 * @throws Exception
	 */
	private void checkIfObligatoryFieldsAreNull(VideoGameDto videoGameDto) throws Exception {

		if (videoGameDto.getTitle() == null || videoGameDto.getTitle().isBlank() || videoGameDto.getPrice() == null || videoGameDto.getAvailability() == null) {
			throw new BadRequestException("Title, Price or Availability can´t be null");
		}

	}

}
