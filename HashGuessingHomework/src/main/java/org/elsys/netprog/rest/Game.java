package org.elsys.netprog.rest;

import java.util.Arrays;
import java.util.Random;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.Base64;

import org.json.simple.JSONObject;

public class Game {
	private String input;
	private int length;
	private String hash;
	private byte bytesOfInput[];
	
	public Game(int length) {
		this.length = length;
		generate();
	}
	
	public void generate()
	{
		try {
			bytesOfInput = new byte[length];
			new Random().nextBytes(bytesOfInput);
			input = new String(bytesOfInput, Charset.forName("UTF-8"));
			String encoded = Base64.getEncoder().encodeToString(bytesOfInput);
			
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			hash = new String(md.digest(encoded.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkInput(String guess) {
		if(input.equals(guess)) {
			generate();
			return true;
		}
		return false;
	}
	
	public boolean checkHash(String guess) {
		return hash.equals(guess);
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getHash() {
		return hash;
	}

}
