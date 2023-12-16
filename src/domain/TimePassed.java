/**
 * 
 */
package domain;

import java.io.Serializable;

/**
 * 
 */
public class TimePassed implements Serializable {
	private int time;
	
	/**
	 * Constructor for TimePassed
	 */
	public TimePassed(int time) {
		this.time = time;
	}
	
	/**
	 * Returns the time
	 * @return is the time elapsed
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Sets a time
	 * @param timePut is the time to set
	 */
	public void setTime(int timePut) {
		this.time = timePut;
	}
	
	/**
	 * Is the time to add
	 * @param timeSum quantity of time to add
	 */
	public void sumTime(int timeSum) {
		this.time += timeSum;
	}

}
