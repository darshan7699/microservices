package com.te.bookservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response{


	private boolean error;

	private String messege;

	private Object data;

}
