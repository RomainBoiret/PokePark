package Pokemon;

import java.util.ArrayList;
import Player.*;
import Shop.*;

public class Pokemon {
    
    private int id;
    private String name;
    private Type primaryType;
    private Type secondaryType;
    private int pv;
    private int maxPv;
    private int attack;
    private int defense;
    private int speed;
    private String evolution;
    private int evolutionLevel;
    private int xp;
    private Status foodStatus;
    private Status staminaStatus;
    private int foodBar;
    private int staminaBar;
    private int lvl;
    private Player player;

    public static final int MAXLEVEL = 100;
    public static final int MINLEVEL = 1;
    public static final int MAXFOOD = 100;
    public static final int MINFOOD = 0;
    public static final int MAXSTAMINA = 100;
    public static final int MINSTAMINA = 0;

    public Pokemon(int _id, String _name, Type _primaryType, Type _secondaryType, int _pv, int _attack, int _defense, int _speed, String _evolution, int _evolutionLevel) {
        this.id = _id;
        this.name = _name;
        this.primaryType = _primaryType;
        this.secondaryType = _secondaryType;
        this.pv = _pv;
        this.maxPv = _pv;
        this.attack = _attack;
        this.defense = _defense;
        this.speed = _speed;
        this.evolution = _evolution;
        this.evolutionLevel = _evolutionLevel;
        this.xp = 0;
        this.foodStatus = Status.NORMAL;
        this.staminaStatus = Status.INSHAPE;
        this.foodBar = MAXFOOD;
        this.staminaBar = MAXSTAMINA;
        this.lvl = MINLEVEL;
    }

    public String feed(Food food) {
        if (getFoodBar() == MAXFOOD) {
            return getName() + " n'a pas faim pour l'instant.";
        }

        ArrayList<Food> availableFoods = player.getFoodsFromInventory();

        for (Food f : availableFoods) {
            if (f.equals(food)) {
                player.getInventory().remove(f);
                int newFoodBar = getFoodBar() + f.getFoodPoint();
                setFoodBar(newFoodBar);

                if (getFoodBar() > MAXFOOD) {
                    setFoodBar(MAXFOOD);
                }

                updateFoodStatus();

                return getName() + " a mangé " + f.getName() + ", faim : " + getFoodBar() + " / " + MAXFOOD;
            }
        }

        return "La nourriture " + food.getName() + " n'est pas dans l'inventaire.";
    }

    public String heal(Heal heal) {
        if (getPv() == getMaxPv()) {
            return getName() + " n'a pas besoin d'être soigné.";
        }

        ArrayList<Heal> availableHeals = player.getHealsFromInventory();

        for (Heal h : availableHeals) {
            if (h.equals(heal)) {
                player.getInventory().remove(h);
                int newPv = getPv() + h.getHealPoint();
                setPv(newPv);

                if (getPv() > getMaxPv()) {
                    setPv(getMaxPv());
                }

                return getName() + " a été soigné avec " + h.getName() + ", pv : " + getPv() + " / " + getMaxPv();
            }
        }

        return "Le heal " + heal.getName() + " n'est pas dans l'inventaire.";
    }

    public String train(Training train) {
        int xpGained = 0;
        String msg = "";

        if (getStaminaStatus() == Status.INSHAPE) {
            switch (train) {
                case STRENGTH:
                    if (getStaminaBar() > 50) {
                        xpGained = getRandomNumber(500, 800);
                        int attackGained = getRandomNumber(1, 5);
                        int newAttack = getAttack() + attackGained;
                        setAttack(newAttack);
                        msg = getName() + " a gagné " + attackGained + " point de force avec l'entraînement: " + train;
                        decreaseStaminaAndFoodBars(50, 20);
                    } else {
                        msg = getName() + " est trop fatigué pour pratiquer l'entraînement: " + train;
                    }
                    break;
                case DEFENSE:
                    if (getStaminaBar() > 40) {
                        xpGained = getRandomNumber(300, 600);
                        int defenseGained = getRandomNumber(1, 5);
                        int newDefense = getDefense() + defenseGained;
                        setDefense(newDefense);
                        msg = getName() + " a gagné " + defenseGained + " point de défense avec l'entraînement: " + train;
                        decreaseStaminaAndFoodBars(40, 20);
                    } else {
                        msg = getName() + " est trop fatigué pour pratiquer l'entraînement: " + train;
                    }
                    break;
                case SPEED:
                    if (getStaminaBar() > 30) {
                        xpGained = getRandomNumber(200, 500);
                        int speedGained = getRandomNumber(1, 5);
                        int newSpeed = getSpeed() + speedGained;
                        setSpeed(newSpeed);
                        msg = getName() + " a gagné " + speedGained + " point de vitesse avec l'entraînement: " + train;
                        decreaseStaminaAndFoodBars(30, 20);
                    } else {
                        msg = getName() + " est trop fatigué pour pratiquer l'entraînement: " + train;
                    }
                    break;
                default:
                    msg = "Cet entraînement n'existe pas !";
                    break;
            }

            updateStaminaStatus();
            updateFoodStatus();
            gainXp(xpGained);
        }

        return msg;
    }

    private void decreaseStaminaBar(int value) {
        int newStaminaBar = getStaminaBar() - value;
        setStaminaBar(newStaminaBar);
        if (getStaminaBar() < MINSTAMINA) {
            setStaminaBar(MINSTAMINA);
        }
    }

    private void decreaseStaminaAndFoodBars(int staminaValue, int foodValue) {
        decreaseStaminaBar(staminaValue);
        decreaseFoodBar(foodValue);
    }

    private void decreaseFoodBar(int value) {
        int newFoodBar = getFoodBar() - value;
        setFoodBar(newFoodBar);
        if (getFoodBar() < MINFOOD) {
            setFoodBar(MINFOOD);
        }
    }

    private void updateStaminaStatus() {
        int currentStamina = getStaminaBar();
        if (currentStamina <= 49) {
            setStaminaStatus(Status.TIRED);
        } else {
            setStaminaStatus(Status.INSHAPE);
        }
    }

    private void updateFoodStatus() {
        int currentFood = getFoodBar();
        if (currentFood <= 24) {
            setFoodStatus(Status.STARVING);
        } else if (currentFood <= 49) {
            setFoodStatus(Status.HUNGER);
        } else if (currentFood <= 95) {
            setFoodStatus(Status.NORMAL);
        } else {
            setFoodStatus(Status.FULL);
        }
    }

    public void gainXp(int xpGained) {
        int newXp = getXp() + xpGained;
        setXp(newXp);
        int xpNexLvl = getXpForNextLevel(getLevel());
        System.out.println("XP gagné: +" + xpGained + " !");
        System.out.println("XP actuel: " + getXp() + " / " + xpNexLvl + " pour atteindre le niveau suivant.");

        while (getXp() >= xpNexLvl) {
            int xp = getXp() - xpNexLvl;
            setXp(xp);
            levelUp();
        }
    }

    private void levelUp() {
        if (getLevel() < MAXLEVEL) {
            int increlvl = getLevel() + 1;
            setLevel(increlvl);
            System.out.println(getName() + " est monté au niveau " + getLevel() + " !");
            evolve();
        } else {
            System.out.println(getName() + " est déjà au niveau maximum !");
        }
    }

    private int getXpForNextLevel(int level) {
        if (level < MINLEVEL || level >= MAXLEVEL) {
            return 0;
        }

        return (int) (400 * Math.pow(level, 3) / 8000);
    }

    public void evolve() {
        if (getLevel() < getEvolutionLevel()) {
            return;
        }

        Pokemon evolutionPokemon = PokemonFactory.obtenirPokemon(getEvolution());
        
        if (evolutionPokemon != null) {
            setId(evolutionPokemon.id);
            setName(evolutionPokemon.name);
            setPrimaryType(evolutionPokemon.primaryType);
            setSecondaryType(evolutionPokemon.secondaryType);
            setMaxPv(evolutionPokemon.pv);
            setAttack(evolutionPokemon.attack);
            setDefense(evolutionPokemon.defense);
            setSpeed(evolutionPokemon.speed);
            setEvolution(evolutionPokemon.evolution);
            setEvolutionLevel(evolutionPokemon.evolutionLevel);
            setPv(getMaxPv());
            setXp(0);

            System.out.println(getName() + " a évolué en " + evolutionPokemon.getName() + " !");
        } else {
            System.out.println(getName() + " ne peut pas évoluer !");
        }
    }

    public String getName() {
        return this.name;
    }

    public Type getPrimaryType() {
        return this.primaryType;
    }

    public Type getSecondaryType() {
        return this.secondaryType;
    }

    public int getXp() {
        return this.xp;
    }

    public int getPv() {
        return this.pv;
    }

    public int getMaxPv() {
        return this.maxPv;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Status getFoodStatus() {
        return this.foodStatus;
    }

    public Status getStaminaStatus() {
        return this.staminaStatus;
    }

    public int getStaminaBar() {
        return this.staminaBar;
    }

    public int getFoodBar() {
        return this.foodBar;
    }

    public int getLevel() {
        return this.lvl;
    }

    public String getEvolution() {
        return this.evolution;
    }

    public int getEvolutionLevel() {
        return this.evolutionLevel;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setXp(int _xp) {
        this.xp = _xp;
    }

    public void setPv(int _pv) {
        this.pv = _pv;
    }

    public void setMaxPv(int _maxPv) {
        this.maxPv = _maxPv;
    }

    public void setAttack(int _attack) {
        this.attack = _attack;
    }

    public void setDefense(int _defense) {
        this.defense = _defense;
    }

    public void setSpeed(int _speed) {
        this.speed = _speed;
    }

    public void setEvolution(String _evolution) {
        this.evolution = _evolution;
    }

    public void setEvolutionLevel(int _evolutionLevel) {
        this.evolutionLevel = _evolutionLevel;
    }

    public void setFoodStatus(Status _foodStatus) {
        this.foodStatus = _foodStatus;
    }

    public void setStaminaStatus(Status _staminaStatus) {
        this.staminaStatus = _staminaStatus;
    }

    public void setFoodBar(int _foodBar) {
        this.foodBar = _foodBar;
    }

    public void setStaminaBar(int _staminaBar) {
        this.staminaBar = _staminaBar;
    }

    public void setLevel(int _lvl) {
        this.lvl = _lvl;
    }

    public void setPrimaryType(Type _primaryType) {
        this.primaryType = _primaryType;
    }

    public void setSecondaryType(Type _secondaryType) {
        this.secondaryType = _secondaryType;
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
