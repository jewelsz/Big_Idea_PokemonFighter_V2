package Client.Messages;

import Client.PokemonGame.Models.Pokemon;

public class MessageContentCreator
{
    private Pokemon pokemon;
    private int dmg;

    private CommunicatorWebSocketType messageType;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public CommunicatorWebSocketType getMessageType() {
        return messageType;
    }

    public void setMessageType(CommunicatorWebSocketType messageType) {
        this.messageType = messageType;
    }
}
