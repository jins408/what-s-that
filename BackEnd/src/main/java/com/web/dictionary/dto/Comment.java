package com.web.dictionary.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

	private int userno;
	private int postno;
	private int commentno;
    private String reply;
    private String reply_date;

}
