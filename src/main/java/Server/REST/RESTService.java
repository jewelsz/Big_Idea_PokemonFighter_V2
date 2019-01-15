package Server.REST;


import Client.PokemonGame.Models.Pokemon;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pokemonLibrary")
public class RESTService
{
    @GET
    @Path("/pokemon/{name}")
    @Produces("application/json")
    public Response getPokemon(@PathParam("name") String name) {

        System.out.println("[Server getPokemon]");

        Pokemon pokemon = PokemonLibrary.getInstance().getPokemon(name);

        if (pokemon == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RESTResponseHelper.getErrorResponseString()).build();
        }

        // Define response
        return Response.status(200).entity(RESTResponseHelper.getSinglePokemonResponse(pokemon)).build();
    }


    @GET
    @Path("/pokemon/all")
    @Produces("application/json")
    public Response getAllSavedPokemon() {

        System.out.println("[Server getAllSavedPokemon]");

        List<Pokemon> getAllSavedPokemon = PokemonLibrary.getInstance().getAllPokemon();

        // Define response
        return Response.status(200).entity(RESTResponseHelper.getAllPokemonResponse(RESTResponseHelper.getSavedPokemonList(getAllSavedPokemon))).build();
    }



    @POST
    @Path("/pokemon")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPokemon(Pokemon pokemonRequest) {

        System.out.println("[Server addPokemon]");

        // Check request
        if (pokemonRequest == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RESTResponseHelper.getErrorResponseString()).build();
        }

        Pokemon newPokemon = PokemonLibrary.getInstance().addPokemon(pokemonRequest);

        // Define response
        return Response.status(200).entity(RESTResponseHelper.getSinglePokemonResponse(newPokemon)).build();
    }


    @PUT
    @Path("/pokemon/changeHealth")
    @Consumes("application/json")
    @Produces("application/json")
    public Response changeHealth(Pokemon pokemonRequest) {

        System.out.println("[Server changeHealth]");

        // Check request
        if (pokemonRequest == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RESTResponseHelper.getErrorResponseString()).build();
        }

        boolean success = PokemonLibrary.getInstance().changeHealth(pokemonRequest.getName(), pokemonRequest.getHealth());

        // Define response
        return Response.status(200).entity(RESTResponseHelper.getSuccessResponse(success)).build();
    }

    @DELETE
    @Path("/pokemon/{name}")
    @Produces("application/json")
    public Response removePokemon(@PathParam("name") String name) {

        System.out.println("[Server removePokemon]");

        Pokemon pokemon = PokemonLibrary.getInstance().getPokemon(name);


        if (pokemon == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RESTResponseHelper.getErrorResponseString()).build();
        }


        boolean success = PokemonLibrary.getInstance().removePokemon(name);

        // Return response
        return Response.status(200).entity(RESTResponseHelper.getSuccessResponse(success)).build();
    }
}
