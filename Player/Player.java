package Player;
import MisteryBox.*;
import Pokemon.*;
import Shop.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Player {
    
    private String name;
    private int pokeDollars;
    private ArrayList<Pokemon> park;
    private ArrayList<Item> inventory;
    private int parkCapacity;
    private int inventoryCapacity;

    public static final int MAXGAIN = 2500;
    public static final int MINGAIN = 200;
    public static final int PARK_INCREMENT_COST = 1000;
    public static final int INVENTORY_INCREMENT_COST = 500;
    public static final int PARK_INCREMENT_SIZE = 50;
    public static final int INVENTORY_INCREMENT_SIZE = 50;

    public Player(String _name) {
        this.name = _name;
        this.pokeDollars = 1000;
        this.park = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.parkCapacity = 100;
        this.inventoryCapacity = 100;
    }

    public boolean addPokemon(Pokemon pokemon) {
        if (park.size() < parkCapacity) {
            return park.add(pokemon);
        }
        return false;
    }

    public boolean removePokemon(Pokemon pokemon) {
        return park.remove(pokemon);
    }

    public int parkSize() {
        return park.size();
    }

    public boolean addItem(Item item) {
        if (inventory.size() < inventoryCapacity) {
            return inventory.add(item);
        }
        return false;
    }

    public boolean sellItem(Item item) {
        return inventory.remove(item);
    }

    public int inventorySize() {
        return inventory.size();
    }

    public boolean updatePark(int numberOfSpaces) {
        int cost = numberOfSpaces * PARK_INCREMENT_COST;
        if (getPokeDollars() >= cost) {
            int newMoney = getPokeDollars() - cost;
            setPokeDollars(newMoney);
            int newPark = getParkCapacity() + PARK_INCREMENT_SIZE * numberOfSpaces;
            setParkCapacity(newPark);
            return true;
        }
        return false;
    }

    public boolean updateInventory(int numberOfSpaces) {
        int cost = numberOfSpaces * INVENTORY_INCREMENT_COST;
        if (getPokeDollars() >= cost) {
            int newMoney = getPokeDollars() - cost;
            setPokeDollars(newMoney);
            int newInventory = getInventoryCapacity() + INVENTORY_INCREMENT_SIZE * numberOfSpaces;
            setInventoryCapacity(newInventory);
            return true;
        }
        return false;
    }

    public void gain() {
        int pokeDollarsGain = (int) (Math.random() * (MAXGAIN - MINGAIN + 1)) + MINGAIN;
        int newPokeDollars = getPokeDollars() + pokeDollarsGain;
        setPokeDollars(newPokeDollars);
    }

    public String getName() {
        return this.name;
    }

    public int getPokeDollars() {
        return this.pokeDollars;
    }

    public int getParkCapacity() {
        return this.parkCapacity;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public int getInventoryCapacity() {
        return this.inventoryCapacity;
    }

    public ArrayList<Food> getFoodsFromInventory() {
        return inventory.stream()
            .filter(item -> item instanceof Food)
            .map(item -> (Food) item)
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    
    public ArrayList<Heal> getHealsFromInventory() {
        return inventory.stream()
            .filter(item -> item instanceof Heal)
            .map(item -> (Heal) item)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setPokeDollars(int _pokeDollars) {
        this.pokeDollars = _pokeDollars;
    }

    public void setParkCapacity(int _parkCapacity) {
        this.parkCapacity = _parkCapacity;
    }

    public void setInventoryCapacity(int _inventoryCapacity) {
        this.inventoryCapacity = _inventoryCapacity;
    }
}
