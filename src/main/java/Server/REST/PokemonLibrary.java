package Server.REST;

import Client.PokemonGame.Models.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonLibrary
{
    private final Map<String, Pokemon> savedPokemon;
    // Instance of pet store (singleton pattern)
    private static PokemonLibrary instance;

    public PokemonLibrary()
    {
        savedPokemon = new HashMap<>();
    }

    public static PokemonLibrary getInstance() {
        if (instance == null) {
            instance = new PokemonLibrary();
        }
        return instance;
    }

    public Pokemon getPokemon(String name)
    {
        return savedPokemon.get(name);
    }

    public List<Pokemon> getAllPokemon()
    {
        return new ArrayList<>(savedPokemon.values());
    }

    public Pokemon addPokemon(Pokemon pokemon) {

        savedPokemon.put(pokemon.getName(),pokemon);

        // Return the new pet
        return pokemon;
    }

    public boolean changeHealth(String name, int health) {

        Pokemon pokemon = savedPokemon.get(name);
        if (pokemon != null) {
            // Pet exists, change the name of the owner
            pokemon.setHealth(health);
            return true;
        }
        // Pet not found
        return false;
    }

    public boolean removePokemon(String name) {
        Pokemon pRemoved = savedPokemon.remove(name);
        if (pRemoved != null) {
            // Successfully removed
            return true;
        }
        // Not found
        return false;
    }
}
