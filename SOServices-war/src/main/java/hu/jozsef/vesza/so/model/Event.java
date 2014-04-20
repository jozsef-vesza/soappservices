package hu.jozsef.vesza.so.model;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.util.Date;
import java.util.Random;

@Entity
public class Event
{

    private String eventTitle;
    private Date eventDate;
    private int eventDuration;
    private int ticketsLeft;
    private Text eventDescription;
    private int ticketPrice;
    private Location location;
    private int ticketsPurchased;
    private boolean isPaid;
    private int priority;
    private Table selectedTable;
    private String shortTitle;
    @Id private Long identifier;
    private Ref<User> owner;
    Blob image;

    public Event()
    {
    }

    public Event(Event anEvent)
    {
        this.eventTitle = anEvent.eventTitle;
        this.eventDate = anEvent.eventDate;
        this.eventDuration = anEvent.eventDuration;
        this.ticketsLeft = anEvent.ticketsLeft;
        this.eventDescription = anEvent.eventDescription;
        this.ticketPrice = anEvent.ticketPrice;
        this.location = anEvent.location;
        this.ticketsPurchased = anEvent.ticketsPurchased;
        this.isPaid = anEvent.isPaid;
        this.priority = anEvent.priority;
        Random randomGenerator = new Random();
        this.identifier = (1000000 + randomGenerator.nextInt(10000000)) + randomGenerator.nextLong();
    }

    public void setSelectedTable(Long tableId)
    {
        for (Table table : this.location.getTables())
        {
            if (table.getIdentifier().equals(tableId))
            {
                this.selectedTable = table;
            }
        }
    }

    public String getEventTitle()
    {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle)
    {
        this.eventTitle = eventTitle;
    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }

    public Integer getEventDuration()
    {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration)
    {
        this.eventDuration = eventDuration;
    }

    public Integer getTicketsLeft()
    {
        return ticketsLeft;
    }

    public void setTicketsLeft(Integer ticketsLeft)
    {
        this.ticketsLeft = ticketsLeft;
    }

    public Text getEventDescription()
    {
        return eventDescription;
    }

    public void setEventDescription(Text eventDescription)
    {
        this.eventDescription = eventDescription;
    }

    public Integer getTicketPrice()
    {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice)
    {
        this.ticketPrice = ticketPrice;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public Integer getTicketsPurchased()
    {
        return ticketsPurchased;
    }

    public void setTicketsPurchased(Integer ticketsPurchased)
    {
        this.ticketsPurchased = ticketsPurchased;
    }

    public boolean isPaid()
    {
        return isPaid;
    }

    public void setPaid(boolean isPaid)
    {
        this.isPaid = isPaid;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public long getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(long identifier)
    {
        this.identifier = identifier;
    }

    public void setOwner(Ref<User> owner)
    {
        this.owner = owner;
    }

    public Ref<User> getOwner()
    {
        return owner;
    }

    public void setImage(Blob image)
    {
        this.image = image;
    }

    public Blob getImage()
    {
        return image;
    }

    public void setShortTitle(String shortTitle)
    {
        this.shortTitle = shortTitle;
    }

    public String getShortTitle()
    {
        return shortTitle;
    }
    
    
    
    

}
