package Client.PokemonGame.Models;

import java.util.Random;

public class Attack
{
    private String name;
    private  int averageDamage;
    private  int damage;

    public Attack(String name, int averageDamage)
    {
        this.name = name;
        this.averageDamage = averageDamage;
    }

    //      Creates a random for the damage
//      Use every time the player attacks
    public int calculateDamage()
    {
//
        return averageDamage;
    }

    public String toString()
    {
        return name;
    }
}
