package com.ronit.entities;

import java.sql.Date;


import java.util.UUID;

import com.ronit.enums.ClientType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TokenInfo {

	private String token;
	private Date creationTime;
	private ClientType clientType;
	
public static TokenInfo generate(ClientType type) {
	TokenInfo info = TokenInfo.builder().token(UUID.randomUUID().toString()).creationTime(new Date(0)).clientType(type).build();
	return info;
	
	
}

}
