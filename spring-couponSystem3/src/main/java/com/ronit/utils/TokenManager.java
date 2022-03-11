package com.ronit.utils;

import java.sql.Date;



import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronit.entities.TokenInfo;
//import com.ronit.entities.TokenInfo;
import com.ronit.enums.ClientType;


@Service
//@AllArgsConstructor
public class TokenManager {
	
	
// do i need another constructor?
	public TokenManager(Map<String, TokenInfo> tokens) {
		super();
		this.tokens = tokens;
	}
	
	// do i need getters and setters?
	public Map<String, TokenInfo> getTokens() {
		return tokens;
	}

	public void setTokens(Map<String, TokenInfo> tokens) {
		this.tokens = tokens;
	}


	@Autowired
	private Map<String, TokenInfo> tokens;
	
	public void TokenManager1() {
		tokens = new HashMap<>();
	}
	
	public boolean isTokenExists(String token) {
		return tokens.get(token) !=null;
	}

	
	public String generateToken(ClientType type) {
		TokenInfo info = TokenInfo.generate(type);
		tokens.put(info.getToken(), info);
		return info.getToken();			
	}
	
	public void removeToken(String token) {
		tokens.remove(token);
	}
	
	//?
	public void returnExpired() {
		tokens.entrySet().removeIf((entry) -> isTokenExpired(entry.getValue().getCreationTime()));
		
	}
			
	public void removeExpired() {
		tokens.entrySet().removeIf((entry)-> 
		 new Date(0).after(entry.getValue().getCreationTime()));
		//getCreationDate
		
	}
	
	public boolean isTokenExpired(Date time) {
		return new Date(0).after(DateUtils.addMinutes(time, 30));
//		
	}
	
	// -------------------------

	
	}



