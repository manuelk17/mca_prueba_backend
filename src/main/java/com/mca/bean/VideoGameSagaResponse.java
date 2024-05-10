package com.mca.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VideoGameSagaResponse {
	
	private String id;
	
	private String title;
	
	private Boolean availability;
	
	private Double price;

}
