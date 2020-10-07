package com.web.dictionary.dto;


import lombok.*;

@Data
public class Culture {

	private int postno;
	private String culturename;
	private String content;
	private int isfavorite;
	private String imageUrl;
	private String ename;
	private String categoty;
	private String generation;
	private String constructionperiod;
	private String location;
	private double lng;
	private double lat;
}
