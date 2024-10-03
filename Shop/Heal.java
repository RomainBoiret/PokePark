package Shop;

public class Heal extends Item {

    private int healPoint;
    
    public Heal(String _name, int _price, String _description, int _healPoint)
    {
        super(_name, _price, _description);
        this.healPoint = _healPoint;
    }

    public int getHealPoint()
    {
        return this.healPoint;
    }
}
