package com.mca.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mca.dto.VideoGameDto;
import com.mca.infrastructure.model.Promotion;
import com.mca.infrastructure.model.VideoGame;
import com.mca.service.VideoGameService;
import com.mca.util.Utils;

@SpringBootTest
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VideoGameControllerTest {

	@Autowired
	private VideoGameService videoGameService;

	@Test
	@Order(1)
	void testSave() throws Exception {
		VideoGame videoGame = videoGameService.save(new VideoGameDto("Aether Chronicles: Rise of the Phoenix", true, 23.99));
		Assertions.assertEquals("Aether Chronicles: Rise of the Phoenix", videoGame.getTitle());
		Assertions.assertEquals(Boolean.TRUE, videoGame.getStock().isAvailability());
		Assertions.assertEquals(23.99, Utils.getCurrentPromotion(new ArrayList<>(videoGame.getPromotions())).getPrice());
	}

	@Test
	@Order(2)
	void testfindById() throws Exception {
		VideoGame videoGameDB = videoGameService.findById(21L);
		Assertions.assertEquals("Aether Chronicles: Rise of the Phoenix", videoGameDB.getTitle());
		Assertions.assertEquals(Boolean.TRUE, videoGameDB.getStock().isAvailability());
		Assertions.assertEquals(23.99, Utils.getCurrentPromotion(new ArrayList<>(videoGameDB.getPromotions())).getPrice());

	}

	@Test
	@Order(3)
	void testfindAll() throws Exception {
		Assertions.assertEquals(21, videoGameService.findAll().size());
	}

	@Test
	@Order(4)
	void testChangeStock() throws Exception {
		Assertions.assertEquals(Boolean.FALSE, videoGameService.changeStock(21L, Boolean.FALSE).getStock().isAvailability());
	}

	@Test
	@Order(5)
	void testAddPromotion() throws Exception {
		Timestamp timestamp = Timestamp.from(Instant.now());
		videoGameService.addPromotion(21L, 35.99, timestamp);
		VideoGame videoGameDB = videoGameService.findById(21L);
		Promotion currentPromotion = Utils.getCurrentPromotion(new ArrayList<>(videoGameDB.getPromotions()));
		Assertions.assertEquals(35.99, currentPromotion.getPrice());
	}

	@Test
	@Order(6)
	void testDeleteById() throws Exception {
		videoGameService.deleteById(21L);
		Assertions.assertNull(videoGameService.findById(21L));
	}

}
