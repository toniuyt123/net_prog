package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;


@Path("/game")
public class GameController {
	public static ArrayList<Game> games = new ArrayList<Game>();
	
	@POST
	@Path("/startGame") 
	@Produces(value={MediaType.TEXT_PLAIN})
	public Response startGame() throws URISyntaxException{
		Game game = new Game();
		games.add(game);
		
		return Response.created(new URI("/games")).status(201).entity(game.getId().toString()).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		Game game = new Game();
		boolean found = false;
		for(Game g: games) {
			if(g.getId().toString().equals(gameId)) {
				game = g;
				found = true;
				break;
			}
		}
		if(!found) {
			return Response.status(404).build();
		}
		
		if(game.guess(guess) != 0) {
			return Response.status(400).build();
		}
		
		return Response.status(200).entity(game.toJSON(0)).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		JSONArray arr = new JSONArray();
		for(Game game : games) {
			arr.add(game.toJSON(1));
		}
		
		return Response.status(200).entity(arr).build();
	}
}
