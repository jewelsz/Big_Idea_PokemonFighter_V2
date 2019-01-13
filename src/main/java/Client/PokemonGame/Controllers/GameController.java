package Client.PokemonGame.Controllers;

import Client.PokemonGame.Models.Attack;
import Client.PokemonGame.Models.Player;
import Client.PokemonGame.Models.Pokemon;

//------------------------------//
//----------Main game-----------//
//------------------------------//
public class GameController
{
    private Player thisPlayer;

    boolean playerTurn = true;

    public Pokemon createPlayer(String name)
    {
        thisPlayer = new Player(name);
        return thisPlayer.getSummonedPokemon();
    }
    public void gotAttacked(Attack attack)
    {
        thisPlayer.getSummonedPokemon().gotHit(attack.calculateDamage());
        thisPlayer.checkPokemonHealth();
    }

    public Player getThisPlayer() {
        return thisPlayer;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
