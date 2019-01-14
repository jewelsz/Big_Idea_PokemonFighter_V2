package Client.PokemonGame.Models;

import java.util.Random;

public class Attack
{
    private String name;
    private  int damage;

    public Attack(String name, int damage)
    {
        this.name = name;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public String toString()
    {
        return name;
    }
}
