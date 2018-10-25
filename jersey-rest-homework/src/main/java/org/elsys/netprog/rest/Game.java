package org.elsys.netprog.rest;

import java.util.Arrays;
import java.util.UUID;

import org.json.simple.JSONObject;

public class Game {
	private UUID gameId;
	private int secret;
	private int cowsNumber;
	private int bullsNumber;
	private int turns;
	private boolean success;
	
	public Game() {
		this.gameId = UUID.randomUUID();
		do{
			this.secret = 1000 + (int)(Math.random() * 8999);
		}while(checkForDuplicates(this.secret) != 0);
		this.turns = 0;
		this.success = false;
	}
	
	public int guess(String number) {
		try {
			int guess = Integer.parseInt(number);
			
			if(guess < 1000 || guess > 9999) return -1;
			if(checkForDuplicates(guess) != 0) return -1;
			
			cowsNumber = 0;
			bullsNumber = 0;
			String secretNums = Integer.toString(getSecret());
			String guessNums = Integer.toString(guess);
			
			for(int i = 0;i < 4;i++) {
				int charIndex = secretNums.indexOf(guessNums.charAt(i));
				if(charIndex >= 0) {
					if(charIndex == i) {
						bullsNumber += 1;
						if(bullsNumber == 4) {
							success = true;
							break;
						}
						continue;
					}
					cowsNumber += 1;
				}
			}
		} catch(NumberFormatException e) {
			return -1;
		}
		
		turns++;
		return 0;
	}
	
	public UUID getId() {
		return gameId;
	}
	
	public int getCows() {
		return cowsNumber;
	}
	
	public int getBulls() {
		return bullsNumber;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public boolean isSolved() {
		return success;
	}
	
	public int getSecret() {
		return secret;
	}
	
	private int checkForDuplicates(int guess) {
		String number = Integer.toString(guess);
		for(int i = 0;i < 3;i++) {
			if(number.substring(i + 1,  4).contains(number.substring(i, i + 1))) {
				return -1;
			}
		}
		
		return 0;
	}
	
	public JSONObject toJSON(int withSecret) {
		JSONObject obj = new JSONObject();
		obj.put("gameId", getId());
		if(withSecret == 0) {
			obj.put("cowsNumber", getCows());
			obj.put("bullsNumber", getBulls());
		}
		obj.put("turnsCount", getTurns());
		if(withSecret == 1) {
			if(success) {
				obj.put("secret", Integer.toString(getSecret()));
			} else {
				obj.put("secret", "****");
			}
			
		}
		obj.put("success", isSolved());
		
		return obj;
	}
}
