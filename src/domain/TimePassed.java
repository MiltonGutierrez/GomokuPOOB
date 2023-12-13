/**
 * 
 */
package domain;

/**
 * 
 */
public class TimePassed {
	private int time;
	/**
	 * 
	 */
	public TimePassed(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int timePut) {
		this.time = timePut;
	}
	
	public void sumTime(int timeSum) {
		this.time += timeSum;
	}

}
