package com.mca.service;


import java.io.IOException;
import java.util.List;

import com.mca.bean.VideoGameSagaResponse;

public interface SagaService {
	
	/***
	 * Get the saga videogames given an id
	 * @param gameId
	 * @return
	 * @throws IOException
	 */
	public List<VideoGameSagaResponse> getSagaByIdVideoGame (Long gameId) throws IOException;

}
