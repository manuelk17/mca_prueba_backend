package com.mca.util;

import java.sql.Timestamp;
import java.util.List;

import com.mca.infrastructure.model.Promotion;

public class Utils {

	/***
	 * Get the current promotion from array promotions of a videogame
	 * 
	 * @param videoGamePromotions
	 * @return Promotion
	 */
	public static Promotion getCurrentPromotion(List<Promotion> videoGamePromotions) {

		Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
		if (videoGamePromotions != null) {
			for (Promotion promotion : videoGamePromotions) {
				if (fechaActual.after(promotion.getValid_from())) {
					return promotion;
				}
			}
		}
		return null;
	}

}
