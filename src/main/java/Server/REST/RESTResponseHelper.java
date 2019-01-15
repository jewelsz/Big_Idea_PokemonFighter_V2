package Server.REST;

import Client.PokemonGame.Models.Pokemon;
import Shared.REST.PokemonLibraryResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RESTResponseHelper
{
    private static final Gson gson = new Gson();

    public static String getErrorResponseString()
    {
        PokemonLibraryResponse response = new PokemonLibraryResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSinglePokemonResponse(Pokemon pokemonFromLibrary)
    {
        PokemonLibraryResponse response = new PokemonLibraryResponse();
        response.setSuccess(true);
        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(pokemonFromLibrary);
        response.setPokemon(pokemons);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSuccessResponse(boolean success)
    {
        PokemonLibraryResponse response = new PokemonLibraryResponse();
        response.setSuccess(success);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getAllPokemonResponse(List<Pokemon> allPokemon)
    {
        PokemonLibraryResponse response = new PokemonLibraryResponse();
        response.setSuccess(true);
        response.setPokemon(allPokemon);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static List<Pokemon> getSavedPokemonList(List<Pokemon> allPokemon)
    {
        List<Pokemon> allSavedPokemon = new ArrayList<>();
        for (Pokemon p : allSavedPokemon) {
            allSavedPokemon.add(p);
        }
        return allSavedPokemon;
    }
}
