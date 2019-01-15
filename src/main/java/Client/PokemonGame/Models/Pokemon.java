package Client.PokemonGame.Models;

import Client.PokemonGame.Factory.AttackFactory;

import java.util.ArrayList;

public class Pokemon
{
    private String name;
    private ElementTypeEnum type;
    private int health;
    private ArrayList<Attack> attacks;
    private Boolean isDead;

    public Pokemon(String name, ElementTypeEnum type)
    {
        isDead = false;
        this.name = name;
        this.type = type;
        health = 50;
        setAttacks();
    }

    public void gotHit(int damage)
    {
        health = health - damage;
        if(health <= 0)
        {
            isDead = true;
        }
    }

    //Create attacks for pokemon
    private void setAttacks()
    {
        AttackFactory factory = new AttackFactory(type);
        attacks = factory.getAttacks();
    }



    public String toString()
    {
        return name;
    }

    public ArrayList<Attack> getAttacks()
    {
        return attacks;
    }

    public String getName() {
        return name;
    }

    public ElementTypeEnum getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public Boolean getDead() {
        return isDead;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
