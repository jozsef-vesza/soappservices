package hu.jozsef.vesza.so.model;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.util.Date;
import java.util.Random;

/**
 * Class representation of an event
 * @author József
 */
@Entity
public class Event
{

    /**
     * The title of an event
     */
    
    private String eventTitle;
    /**
     * The date of an event
     */
    
    private Date eventDate;
    /**
     * The duration of an event
     */
    private int eventDuration;
    
    /**
     * The number of tickets available for an event
     */
    private int ticketsLeft;
    
    /**
     * Description of an event
     */
    private Text eventDescription;
    
    /**
     * Price of tickets
     */
    private int ticketPrice;
    
    /**
     * Location of an event
     */
    private Location location;
    
    /**
     * Number of tickets purchased by user to an event
     */
    private int ticketsPurchased;
    
    /**
     * Order status indicator
     */
    private boolean isPaid;
    
    /**
     * Priority of an event
     */
    private int priority;
    
    /**
     * The table reserved by the user
     */
    private Table selectedTable;
    
    /**
     * Short title for easy resource access
     */
    private String shortTitle;
    
    /**
     * Datastore identifier
     */
    @Id private Long identifier;
    
    /**
     * The user who orderet the ticket
     */
    private Ref<User> owner;
    
    /**
     * The promotional image for an event
     */
    Blob image;

    /**
     * Empty default constructor
     */
    public Event()
    {
    }

    /**
     * Custom constructor for copying an event
     * @param anEvent the event to copy
     */
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

    /**
     * Set the selected item for a reserved ticket
     * @param tableId identifier of a table reserved by the user
     */
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

    /** ACCESSOR METHODS **/
    
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
