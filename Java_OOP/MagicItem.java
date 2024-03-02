package a12047732;

/**
 * MagicItems are items that can cause magic effects on other objects. So they are a source
 * of magic. As items they can be traded and they also can be the target of magic effects  
 */
public abstract class MagicItem implements Tradeable, MagicEffectRealization, MagicSource {
	/**
	 * Must not be null or empty
	 * */
	private String name;
	/**
	 * Number of usages remaining; must not be negative
	 */
	private int usages;
	/**
	 * Must not be negative
	 */
	private int price;
	/**
	 * must not be negative
	 */
	private int weight;



	/**
	 * @param name name
	 * @param usages number of usages still left
	 * @param price price
	 * @param weight weight
	 * @throws Exception 
	 */
	//no constructor bc abstract class
	
	public MagicItem(String name, int usages, int price, int weight)  {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name can not be empty.");
		}
		this.name = name;
		
		if ( usages < 0 ) {
			throw new IllegalArgumentException("Usages can not be negativ.");
		}
		this.usages = usages;
		
		if ( price < 0 ) {
			throw new IllegalArgumentException("price can not be negativ.");
		}
		this.price = price;
		
		if ( weight < 0 ) {
			throw new IllegalArgumentException("weight can not be negativ.");
		}
		this.weight = weight;
		
	}


    /**
     * Returns value of usages (for access from deriving classes)
     * @return value of instance variable usages
     */
	public int getUsages() { //TC
		return this.usages;
	}

    /**
     * If usages > 0 reduce usage by 1 and return true, otherwise return false
     * @return returns true if usage is still possible
     */
	public boolean tryUsage() {  //TC
		if (this.usages > 0) {
			this.usages -= 1;
			return true;
		}
		return false;
	}

    /**
     * Returns "use" if usages is equal to 1, "uses" otherwise
     * @return "use" or "uses" depending on the value of usages
     */
	public String usageString() {  
		if(this.usages == 1 ) {
			return "use";
		}
		return "uses";
	}

    /**
     * returns empty string. Is overridden in deriving classes as needed
     * @return ""
     */
	public String additionalOutputString() {return "";
	}

    /**
     * Formats this object according to
     * "['name'; 'weight' g; 'price' 'currencyString'; 'usages' 'usageString''additionalOutputString']"
     * 'currencyString' is "Knut" if price is 1, "Knuts" otherwise
     * e.g. (when additionalOutput() returns an empty string)
     * "[Accio Scroll; 1 g; 1 Knut; 5 uses]" or "[Alohomora Scroll; 1 g; 10 Knuts; 1 use]"
     * @return "['name'; 'weight' g; 'price' 'currencyString'; 'usages' 'usageString''additionalOutputString']"
     */
	@Override
	public String toString() {
	    String currencyString = (price == 1) ? "Knut" : "Knuts"; 

	    String usageString = usageString();

	    String additionalOutputString = additionalOutputString();

	    String res = "[" + name + "; " + weight + " g; " + price + " " + currencyString + "; " + usages + " " + usageString;

	    if (!additionalOutputString.isEmpty()) {
	        res += additionalOutputString;
	    }

	    res += "]";

	    return res;
	}

	
	//Tradeable Interface:

	/**
	 * Returns price of the object
	 * @return value of instance variable price
	 */
	@Override
	public int getPrice() {return this.price;  //TC
	}

    /**
     * Returns weight of the object
     * @return value of instance variable weight
     */
	@Override    
	public int getWeight() { return this.weight;  //TC
	}
	  
	//MagicSource Interface:

    /**
     * Always returns true; no Exceptions needed
     */
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int amount) { 
		if (levelNeeded == null) {
			throw new IllegalArgumentException("levelNeeded null");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("levelNeeded null");
		}
		
		return true;
	}

	//MagicEffectRealization Interface:
	
	/**
	 * Reduce usages to usages*(1-percentage/100.)
	 */
	@Override
	public void takeDamagePercent(int percentage) {
		if (percentage < 0) {
        	throw new IllegalArgumentException("percentage out of span");
        } else if (percentage > 100) {
        	throw new IllegalArgumentException("percentage out of span");
        }
		this.usages = (int) (this.usages *(1 - percentage /100.));
	}
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}