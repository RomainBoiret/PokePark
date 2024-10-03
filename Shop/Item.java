package Shop;

public abstract class Item {
    
    private String name;
    private int price;
    private String description;

    public Item(String _name, int _price, String _description)
    {
        this.name = _name;
        this.price = _price;
        this.description = _description;
    }

    public String getName()
    {
        return this.name;
    }

    public int getPrice()
    {
        return this.price;
    }

    public String getDescription()
    {
        return this.description;
    }
}
