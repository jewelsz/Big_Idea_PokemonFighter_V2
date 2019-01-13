package Client.PokemonGame.Models;

import Client.PokemonGame.Factory.PokemonFactory;

import java.util.ArrayList;

public class Player
{
    private String name;
    private ArrayList<Pokemon> allPokemon;
    private Pokemon summonedPokemon;
    private int pokemonAlive;
    private boolean isDefeated;
    //private boolean turn;

    public Player(String name)
    {
        this.name = name;
        pokemonAlive = 6;
        isDefeated = false;
        allPokemon = new ArrayList<Pokemon>();
        createAllPokemon();
        switchPokemon(null);
    }

    public void switchPokemon(String name)
    {
        if (name == null)
        {
            summonedPokemon = getAllPokemon().get(0);
        }
        else
        {
            for (Pokemon p : allPokemon)
            {
                if (p.getName() == name)
                {
                    summonedPokemon = p;
                }
            }
        }

    }

    public void checkPokemonHealth()
    {
        if(summonedPokemon.getDead())
        {
            allPokemon.remove(summonedPokemon);
            if(allPokemon.size() <= 0)
            {
                isDefeated = true;
            }
            else
            {
                summonedPokemon = allPokemon.get(0);
            }
        }
    }

    private void createAllPokemon()
    {
        PokemonFactory pokemonFactory = new PokemonFactory();
        allPokemon = pokemonFactory.getRandomPokemon();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Pokemon> getAllPokemon() {
        return allPokemon;
    }

    public Pokemon getSummonedPokemon() {
        return summonedPokemon;
    }

    public void setSummonedPokemon(Pokemon summonedPokemon) {
        this.summonedPokemon = summonedPokemon;
    }

    public int getPokemonAlive() {
        return pokemonAlive;
    }

    public void setPokemonAlive(int pokemonAlive) {
        this.pokemonAlive = pokemonAlive;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public void setDefeated(boolean defeated) {
        isDefeated = defeated;
    }
}
