package com.mca.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mca.dto.VideoGameDto;
import com.mca.infrastructure.model.VideoGame;
import com.mca.service.VideoGameService;

@RestController
@RequestMapping("/games")
public class VideoGameController {
	
	@Autowired
	private VideoGameService videoGameService;
	
	@GetMapping(value = "/{idVideoGame}")
	@ResponseBody
	public VideoGame findById (@PathVariable Long idVideoGame) {
		return videoGameService.findById(idVideoGame);
	}
	
	@GetMapping()
	@ResponseBody
	public List<VideoGame> findAll() {
		return videoGameService.findAll();
	}

	
	@PostMapping()
	@ResponseBody
	public VideoGame save (@RequestBody VideoGameDto videoGameDto)throws Exception  {
		return videoGameService.save(videoGameDto);
	}
	
	@PutMapping(value = "/{idVideoGame}/out-of-stock")
	@ResponseBody
	public VideoGame setOutOfStock (@PathVariable Long idVideoGame)throws Exception  {
		return videoGameService.changeStock(idVideoGame, Boolean.FALSE);
	}
	
	@PutMapping(value = "/{idVideoGame}/in-stock")
	@ResponseBody
	public VideoGame setInStock (@PathVariable Long idVideoGame)throws Exception  {
		return videoGameService.changeStock(idVideoGame, Boolean.TRUE);
	}
	
	@PutMapping(value = "/{idVideoGame}/add-promotion")
	@ResponseBody
	public VideoGame addPromotion (@PathVariable Long idVideoGame, @RequestParam(name = "price", required = true) Double price,@RequestParam(name = "validFrom", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String validFrom)throws Exception  {
		return videoGameService.addPromotion(idVideoGame, price, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(validFrom).getTime()));
	}
	
	@DeleteMapping(value = "/{idVideoGame}")
	@ResponseBody
	public Boolean deleteById (@PathVariable Long idVideoGame) {
		return videoGameService.deleteById(idVideoGame);
	}
}
