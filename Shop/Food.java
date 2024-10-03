package Shop;

public class Food extends Item {

    private int foodPoint;

    public Food(String _name, int _price, String _description, int _foodPoint)
    {
        super(_name, _price, _description);
        this.foodPoint = _foodPoint;
    }

    public int getFoodPoint()
    {
        return this.foodPoint;
    }
}
