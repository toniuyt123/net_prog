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
import org.json.simple.JSONObject;


@Path("/game")
public class GameController {
	public static Game game = new Game(7);
	
	@POST
	@Path("/check/{guess}/{hash}") 
	@Produces(value={MediaType.TEXT_PLAIN})
	public Response check(@PathParam("guess") String guess, @PathParam("hash") String hash) throws URISyntaxException{
		Response r;
		if(game.checkHash(hash))
		{
			if(game.checkInput(guess)) {
				r = Response.status(200).build();
			} else {
				r = Response.status(406).build(); 
			}
		} else 
		{
			r = Response.status(406).build();
		}
		return r;
	}
	
	@GET
	@Path("/view")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response view() {
		JSONObject arr = new JSONObject();
		arr.put("length", game.getLength());
		arr.put("hash", game.getHash());
		
		return Response.status(200).entity(arr).build();
	}
}
