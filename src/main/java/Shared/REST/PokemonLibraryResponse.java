package Shared.REST;

import Client.PokemonGame.Models.Pokemon;

import java.util.List;

public class PokemonLibraryResponse
{
    private boolean success;

    private List<Pokemon> pokemon;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}

