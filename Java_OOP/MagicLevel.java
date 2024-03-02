package a12047732;

/**
 * Defines the various magic levels.
 * wizards have a magic level.
 * in order to be able to use specific spells a minimum magic level is necessary.
 * Note: the compiler generated default constructor may not be sufficient for your implementation
 */
public enum MagicLevel {
	
	NOOB(50),
	ADEPT(100),
	STUDENT(200),
	EXPERT(500),
	MASTER(1000);
	
	private final int value;

	 private MagicLevel(int value) {
	    this.value = value;
	 }

    
    public int  toMana() {
        return value;
    }
    
    // Override the toString() method
    @Override
    public String toString() {
    	if (value == 50) {
    		return "*";
    	}
    	if (value == 100) {
    		return "**";
    	}
    	if (value == 200) {
    		return "***";
    	}
    	if (value == 500) {
    		return "****";
    	}
    	if (value == 1000) {
    		return "*****";
    	}
        return "";
    }
	
}