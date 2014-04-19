package hu.jozsef.vesza.so.model;

import com.googlecode.objectify.Ref;
import java.util.Random;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Meal
{

    private String name;
    private String description;
    private int price;
    private int rating;
    private int totalRatings;
    private String category;
    private boolean isPaid;
    private int amount;
    private String image;
    @Id
    private Long identifier;
    private Ref<User> owner;

    public Meal()
    {
    }

    public Meal(Meal aMeal)
    {
        this.name = aMeal.getName();
        this.description = aMeal.getDescription();
        this.price = aMeal.getPrice();
        this.rating = aMeal.getRating();
        this.totalRatings = aMeal.getTotalRatings();
        this.category = aMeal.getCategory();
        this.amount = aMeal.getAmount();
        Random randomGenerator = new Random();
        this.identifier = (1000000 + randomGenerator.nextInt(10000000)) + randomGenerator.nextLong();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public int getTotalRatings()
    {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings)
    {
        this.totalRatings = totalRatings;
    }

    public long getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(long identifier)
    {
        this.identifier = identifier;
    }

    public boolean isPaid()
    {
        return isPaid;
    }

    public void setPaid(boolean isPaid)
    {
        this.isPaid = isPaid;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setOwner(Ref<User> owner)
    {
        this.owner = owner;
    }

    public Ref<User> getOwner()
    {
        return owner;
    }

}
