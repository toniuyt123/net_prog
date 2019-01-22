package org.elsys.netprog.rest;

import static io.restassured.RestAssured.given;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import io.restassured.response.Response;

public class Main {
	public static void main(String [] args)
	{
		final String serverBaseAddress = "jersey-rest-homework/game";
		/*RestAssured.port = 8081;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI = "http://localhost";*/
		
		System.out.println("Starting new game");
		while(true) {
			Response get = given().basePath(serverBaseAddress).get("view");
			get.then().statusCode(200);
			int length = get.body().jsonPath().getInt("length");
			String hash = get.body().jsonPath().getString("hash");
			String hashM, guess;
			boolean guessed = false;
			
			System.out.println("Trying to guess hash " + hash + "with length " + length);
			while (!guessed)
			{
				try {
					byte[] bytesOfInput = new byte[length];
					new Random().nextBytes(bytesOfInput);
					String input = new String(bytesOfInput, Charset.forName("UTF-8"));
					String encoded = Base64.getEncoder().encodeToString(bytesOfInput);
					
					MessageDigest md;
					md = MessageDigest.getInstance("MD5");
					hashM = new String(md.digest(encoded.getBytes()), "UTF8");
					if(given().basePath(serverBaseAddress).post("check/" + input + "/" + hashM).getStatusCode() == 200)
					{
						guessed = true;
						System.out.println("Hash " + hashM + " and input " + input + "were the right answer!! :)");
					} else {
						System.out.println("Hash " + hashM + " and input " + input + "were unsuccessfull. :(");
					}
				}catch( Exception e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
}
