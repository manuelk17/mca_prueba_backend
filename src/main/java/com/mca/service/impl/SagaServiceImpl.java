package com.mca.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mca.bean.VideoGameSagaResponse;
import com.mca.infrastructure.model.Promotion;
import com.mca.infrastructure.model.VideoGame;
import com.mca.service.SagaService;
import com.mca.service.VideoGameService;
import com.mca.util.Utils;

@Service("SagaService")
@Transactional
public class SagaServiceImpl implements SagaService {

	@Autowired
	private VideoGameService videoGameService;

	@Value(value = "${gamesaga.base-url}")
	private String gameSagaBaseUrl;

	@Value(value = "${gamesaga.endpoint.related-saga}")
	private String gamesagaEndpointRelatedSaga;

	@Value(value = "${gamesaga.endpoint.game-saga}")
	private String gamesagaEndpointGameSaga;

	@Override
	public List<VideoGameSagaResponse> getSagaByIdVideoGame(Long gameId) throws IOException {

		List<VideoGameSagaResponse> videoGameSagaResponseListResult = new ArrayList<VideoGameSagaResponse>();
		VideoGame videoGameDB = videoGameService.findById(gameId);
		if (videoGameDB != null) {
			String response = this.getRelatedSagaFromGameSagaApiByIdVideoGame(gameId);
			if (!response.isBlank()) {
				List<Long> idsListRelatedGame = new ArrayList<Long>(this.convertStringToLongList(response));
				for (Long idRelatedGame : idsListRelatedGame) {
					VideoGame relatedVideoGame = videoGameService.findById(idRelatedGame);
					if (relatedVideoGame != null) {
						VideoGameSagaResponse videoGameSagaResponse = new VideoGameSagaResponse();
						videoGameSagaResponse.setId(relatedVideoGame.getId().toString());
						videoGameSagaResponse.setTitle(relatedVideoGame.getTitle());
						videoGameSagaResponse.setAvailability(relatedVideoGame.getStock().isAvailability());
						Promotion videoGamePromotion = Utils.getCurrentPromotion(new ArrayList<Promotion>(relatedVideoGame.getPromotions()));
						videoGameSagaResponse.setPrice(videoGamePromotion != null ? videoGamePromotion.getPrice() : null);
						videoGameSagaResponseListResult.add(videoGameSagaResponse);
					}
				}
			}
		}
		return videoGameSagaResponseListResult;
	}

	/***
	 * Get related saga response from game saga api by id videogame
	 * 
	 * @param gameId
	 * @return String
	 * @throws IOException
	 */
	private String getRelatedSagaFromGameSagaApiByIdVideoGame(Long gameId) throws IOException {

		String url = gameSagaBaseUrl + gamesagaEndpointGameSaga + "/" + gameId.toString() + gamesagaEndpointRelatedSaga;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String resultResponse = "";
		if (con instanceof HttpURLConnection) {
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				resultResponse = response.toString();
			}
		}
		return resultResponse;
	}

	/***
	 * Convert string into a Long type list
	 * 
	 * @param string
	 * @return List<Long>
	 */
	private List<Long> convertStringToLongList(String string) {

		string = string.replace(" ", "");
		string = string.substring(1, string.length() - 1);
		String[] elementos = string.split(",");
		List<Long> listaDeEnteros = new ArrayList<>();
		for (String elemento : elementos) {
			listaDeEnteros.add(Long.parseLong(elemento));
		}
		return listaDeEnteros;
	}

}
