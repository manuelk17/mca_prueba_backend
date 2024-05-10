package com.mca.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mca.bean.VideoGameSagaResponse;
import com.mca.service.SagaService;

@RestController
@RequestMapping("/game")
public class SagaController {
	
	@Autowired
	private SagaService sagaService;
	
	@GetMapping(value = "/{gameId}/saga")
	@ResponseBody
	public List<VideoGameSagaResponse> getSagaByIdVideoGame (@PathVariable Long gameId) throws IOException {
		return sagaService.getSagaByIdVideoGame(gameId);
	}

 

}
