package ch.epfl.cs107.play.game.actor;

public interface Timer {
	
	/**
	 * 
	 * @param startTime: Unix time at timer Begin
	 * @param timerDuration: duration of the timer in milliseconds. 
	 * @return: false until time has run out, then returns true.
	 */
	default boolean timeElapsed(long startTime, long timerDuration) {
		if(startTime < 0 || timerDuration < 0)
			throw new IllegalArgumentException("Negative time.");
		if(System.currentTimeMillis() < startTime + timerDuration) {
			return false;
		}
		else {
			return true;
		}
	}
}
