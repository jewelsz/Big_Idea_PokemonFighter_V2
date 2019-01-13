package Client.PokemonGame.Factory;

import Client.PokemonGame.Models.ElementTypeEnum;
import Client.PokemonGame.Models.Pokemon;

import java.util.ArrayList;
import java.util.Random;

public class PokemonFactory
{
    private ArrayList<Pokemon> createdPokemon;
    private Random rnd;

    public PokemonFactory()
    {
        createdPokemon = new ArrayList<Pokemon>();
        rnd = new Random();
        createPokemon();
    }

    private void createPokemon()
    {
        //Create pokemon and add to the list
        createdPokemon.add(new Pokemon("Psyduck", ElementTypeEnum.WATER));
        createdPokemon.add(new Pokemon("Squirtle", ElementTypeEnum.WATER));
        createdPokemon.add(new Pokemon("Goldeen", ElementTypeEnum.WATER));
        createdPokemon.add(new Pokemon("Charmander", ElementTypeEnum.FIRE));
        createdPokemon.add(new Pokemon("Vulpix", ElementTypeEnum.FIRE));
        createdPokemon.add(new Pokemon("Vulpix", ElementTypeEnum.FIRE));
        createdPokemon.add(new Pokemon("Tangela", ElementTypeEnum.GRASS));
        createdPokemon.add(new Pokemon("Ivysaur", ElementTypeEnum.GRASS));
        createdPokemon.add(new Pokemon("Exeggutor", ElementTypeEnum.GRASS));
        createdPokemon.add(new Pokemon("Meowth", ElementTypeEnum.NORMAL));
        createdPokemon.add(new Pokemon("Lickitung", ElementTypeEnum.NORMAL));
        createdPokemon.add(new Pokemon("Jigglypuff", ElementTypeEnum.NORMAL));
        createdPokemon.add(new Pokemon("Abra", ElementTypeEnum.PSYCHIC));
        createdPokemon.add(new Pokemon("Mr. Mime", ElementTypeEnum.PSYCHIC));
        createdPokemon.add(new Pokemon("Hypno", ElementTypeEnum.PSYCHIC));
        createdPokemon.add(new Pokemon("Gengar", ElementTypeEnum.POISON));
        createdPokemon.add(new Pokemon("Ekans", ElementTypeEnum.POISON));
        createdPokemon.add(new Pokemon("Muk", ElementTypeEnum.POISON));

    }

    //Get 6 random pokemon
    public ArrayList<Pokemon> getRandomPokemon()
    {
        ArrayList<Pokemon> chosenPokemon = new ArrayList<Pokemon>();
        for(int i = 0; i < 5; i++)
        {
            int index = rnd.nextInt(createdPokemon.size());
            Pokemon p = createdPokemon.get(index);
            chosenPokemon.add(p);
            createdPokemon.remove(p);
        }
        return chosenPokemon;
    }

}
