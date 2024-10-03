package Player;
import MisteryBox.*;
import Pokemon.*;
import Shop.*;

import java.util.ArrayList;

public class Player {
    
    private String name;
    private int money;
    private ArrayList<Pokemon> park;
    private ArrayList<Item> inventory;

    public Player(String _name, int _money)
    {
        this.name = _name;
        this.money = _money;
        this.park = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public void openBox(MisteryBox box)
    {

    }

    public void remove(Pokemon pokemon)
    {

    }

    public void shop()
    {

    }

    public String getName()
    {
        return this.name;
    }

    public int getMoney()
    {
        return this.money;
    }

    public ArrayList<Item> getInventory()
    {
        return this.inventory;
    }

    public ArrayList<Food> getFoodsFromInventory() 
    {
        ArrayList<Food> foodList = new ArrayList<>();

        for (Item item : inventory) 
        {
            if (item instanceof Food) 
            {
                foodList.add((Food) item);
            }
        }

        return foodList;
    }
    
    public ArrayList<Heal> getHealsFromInventory() 
    {
        ArrayList<Heal> healList = new ArrayList<>();

        for (Item item : inventory) 
        {
            if (item instanceof Heal) 
            {
                healList.add((Heal) item);
            }
        }

        return healList;
    }
}
