package hu.jozsef.vesza.so.model;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Class representation of an event location
 * @author József
 */
@Entity
public class Location
{
    /**
     * Datastore idenfifier of a location
     */
    @Id private Long identifier;
    
    /**
     * Name of a location
     */
    private String name;
    
    /**
     * Short name for easy resource access
     */
    private String shortName;
    private double stageCenterX;
    private double stageCenterY;
    private double stageWidth;
    private double stageHeight;
    
    /**
     * List of tables at a given location
     */
    private List<Table> tables;

    /** ACCESSOR METHODS **/
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Table> getTables()
    {
        return tables;
    }

    public void setTables(List<Table> tables)
    {
        this.tables = tables;
    }

    public Long getIdentifier()
    {
        return identifier;
    }

    public double getStageCenterX()
    {
        return stageCenterX;
    }

    public void setStageCenterX(double stageCenter)
    {
        this.stageCenterX = stageCenter;
    }

    public double getStageCenterY()
    {
        return stageCenterY;
    }

    public void setStageCenterY(double stageCenterY)
    {
        this.stageCenterY = stageCenterY;
    }

    public double getStageWidth()
    {
        return stageWidth;
    }

    public void setStageWidth(double stageWidth)
    {
        this.stageWidth = stageWidth;
    }

    public double getStageHeight()
    {
        return stageHeight;
    }

    public void setStageHeight(double stageHeight)
    {
        this.stageHeight = stageHeight;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

}
