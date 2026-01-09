using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class Order 
{
    public string customerName;
    public string dough;
    public List<string> toppings;
    public string sauce;
    public int price;
    public int pizzaCount;
    public Sprite customerSprite = null;

    public Order(string customerName,Sprite customerSprite, string dough, string sauce,List<string> toppingList, int price, int pizzaCount = 1)
    {
        this.customerName = customerName;
        this.customerSprite = customerSprite;
        this.dough = dough;
        this.sauce = sauce;
        toppings = toppingList;
        this.price = price;
        this.pizzaCount = pizzaCount;
    }
}
