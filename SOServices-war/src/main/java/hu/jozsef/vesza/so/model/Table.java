package hu.jozsef.vesza.so.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Table
{
	@Id private Long identifier;
	private double xPoint;
	private double yPoint;
	private int numberOfSeats;
	private boolean free;

	public Long getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(Long identifier)
	{
		this.identifier = identifier;
	}

	public double getxPoint()
	{
		return xPoint;
	}

	public void setxPoint(double xPoint)
	{
		this.xPoint = xPoint;
	}

	public double getyPoint()
	{
		return yPoint;
	}

	public void setyPoint(double yPoint)
	{
		this.yPoint = yPoint;
	}

	public int getNumberOfSeats()
	{
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats)
	{
		this.numberOfSeats = numberOfSeats;
	}

	public boolean isFree()
	{
		return free;
	}

	public void setFree(boolean free)
	{
		this.free = free;
	}
}
