package a12047732;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * Wizard class objects are the primary actors in the game. They can use and trade items, provide
 * magic energy, cast spells and also are affected be various magical effects.
 */
public class Wizard implements MagicSource, Trader, MagicEffectRealization {
	/**
	 * Not null not empty
	 */
	private String name; 
	/**
	 * Not null
	 * */
	private MagicLevel level;
	/**
	 * Not negative
	 */
	private int basicHP;
	/**
	 * Not negative; defaults to basicHP
	 */
	private int HP;
	/**
	 * Not less than the manapoints associated with the magic level
	 */
	private int basicMP;
	/**
	 * Not negative; defaults to basicMP
	 */
	private int MP;
	/**
	 * Not negative
	 */
	private int money;
	/**
	 * Not null, may be empty; use HashSet for instantiation
	 */
	private Set<Spell> knownSpells;
	/**
	 * Not null, may be empty; use HashSet for instantiation
	 */
	private Set<AttackingSpell> protectedFrom;
	/**
	 * Not negative
	 */
	private int carryingCapacity;
	/**
	 * Not null, may be empty, use HashSet for instantiation, total weight of inventory
	 * may never exceed carryingCapacity
	 */
	private Set<Tradeable> inventory;

	/**
	 * @param name name
	 * @param level the magic level (proficiency needed to cast spells)
	 * @param basicHP base for percentage health calculations
	 * @param HP current health 
	 * @param basicMP base for percentage mana calculations
	 * @param MP current mana
	 * @param money current money
	 * @param knownSpells set of known spells
	 * @param protectedFrom set of spells the object is protected against
	 * @param carryingCapacity maximum carrying capacity
	 * @param inventory set of items the object is currently carrying
	 */
	public Wizard(String name, MagicLevel level, int basicHP, int HP, int basicMP, int MP, int money,
		Set<Spell> knownSpells, Set<AttackingSpell> protectedFrom, int carryingCapacity,
		Set<Tradeable> inventory) {
		

		
		
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name can not be empty.");
		}
		this.name = name;
		
		if(level == null) {
			throw new IllegalArgumentException("magicLevel  can not be NULL.");
		}
		this.level = level;
		
		if ( basicHP < 0 ) {
			throw new IllegalArgumentException("basicHP can not be negativ.");
		}
		this.basicHP = basicHP;
		
		if ( HP < 0 ) {
			throw new IllegalArgumentException("HP can not be negativ.");
		}

		this.HP = HP;
		
		if (basicMP < level.toMana()) {
			throw new IllegalArgumentException("MP smaler than manapoints of level.");
		}
		this.basicMP = basicMP;
		
		if ( MP < 0 ) {
			throw new IllegalArgumentException("HP can not be negativ.");
		}

		this.MP = MP;
	
		if ( money < 0 ) {
			throw new IllegalArgumentException("money can not be negativ.");
		}
		this.money = money;
		
		if (knownSpells == null ) {
			throw new IllegalArgumentException("knownSpells can not be empty.");
		}
		this.knownSpells = new HashSet<Spell>(knownSpells); 
		
		
		
		if (protectedFrom == null ) {
			throw new IllegalArgumentException("protectedFrom can not be empty.");
		}
		this.protectedFrom = new HashSet<AttackingSpell>(protectedFrom);
		 
		
		if ( carryingCapacity < 0 ) {
			throw new IllegalArgumentException("carryingCapacity can not be negativ.");
		}
		this.carryingCapacity = carryingCapacity;
		
		
		if (inventory == null) {
			throw new IllegalArgumentException("inventory can not be null.");
		}
		this.inventory = new HashSet<Tradeable>(inventory); 
		

 
		
		
	    if ( carryingCapacity < inventoryTotalWeight() ) {
			throw new IllegalArgumentException("Total weight can not be hiegher can carrycapacity");
		}
		
		
	}
	
	



	/**
	 * Return true, if HP is 0, false otherwise
	 * @return true, if HP is 0, false otherwise
	 */
	public boolean isDead() {
		if (this.HP == 0) {
			return true;
		}
		return false;
	}  
	  
	/**
	 * Calculates and returns the total weight of all the items in the inventory
	 * @return total weight of all items in inventory
	 */
	private int inventoryTotalWeight() {
		int totalWeight = 0;
	    for (Tradeable item : this.inventory) {
	        totalWeight += item.getWeight(); 
	    }
	    return totalWeight;
	}
	  
	/**
	 * If spell is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * add spell to the set of knownSpells;
	 * returns true, if insertion was successful, false otherwise.
	 * @param s spell to be learned
	 * @return true, if insertion was successful, false otherwise.
	 */
	public boolean learn(Spell s) {
		if (s == null) {
			throw new IllegalArgumentException("Can not learn from a null spell");
		}
		if(this.isDead()) {
			return false;
		}
		if(knownSpells.add(s)) {
			return true;
		}
		return false;
		
		
	}
	  
	/**
	 * If spell is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * remove spell from the set of knownSpells;
	 * returns true if removal was successful, false otherwise.
	 * @param s spell that the object is about to learn
	 * @return true, if removal was successful, false otherwise.
	 */
	public boolean forget(Spell s) {
		if (s == null) {
			throw new IllegalArgumentException("Can not forget  a null spell");
		}
		if(this.isDead()) {
			return false;
		}
		if(knownSpells.remove(s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * If s or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * if wizard does not know the spell, false is returned;
	 * call cast on s with this as source and parameter target as target
	 * return true, if cast was called;
	 * @param s spell to be cast
	 * @param target target of the spell to cast
	 * @return true, if cast was called, false otherwise;
	 */
	public boolean castSpell(Spell s, MagicEffectRealization target) {
		if (s == null) {
			throw new IllegalArgumentException("Can not cast  a null spell");
		}
		if (target == null) {
			throw new IllegalArgumentException("Can not cast to a null target");
		}
		if(this.isDead()) {
			return false;
		}
		if(!this.knownSpells.contains(s)) {
			return false;
		}

		
		s.cast(this, target);
		return true;
	}
	  
	/**
	 * If this object's knownSpells is empty, return false
	 * otherwise choose a random spell from knownSpells and delegate to
	 * castSpell(Spell, MagicEffectRealization)
	 * @param target target of the spell to cast
	 * @return false, if the object does not know a spell, otherwise the
	 * result of the delegation to castSpell
	 */
	public boolean castRandomSpell(MagicEffectRealization target) {
		if (this.knownSpells.isEmpty()) {
			return false;
		}
		Random random = new Random();
		Spell resSpell = null;
		if (!knownSpells.isEmpty()) {
			int index = random.nextInt(knownSpells.size());
			resSpell = (Spell) knownSpells.toArray()[index];
        }
		return this.castSpell(resSpell, target);
        
		
	}
	  
	/**
	 * If item or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * if wizard does not possess the item, false is returned;
	 * call useOn on the item with parameter target as target;
	 * return true, if useOn was called.
	 * @param item item to be used
	 * @param target target on which item is to be used on
	 * @return true, if useOn was called, false otherwise
	 */
	public boolean useItem(Tradeable item, MagicEffectRealization target) {
		if (item == null) {
			throw new IllegalArgumentException("Can not use  a null spitemell");
		}
		if (target == null) {
			throw new IllegalArgumentException("Can not use  a null target");
		}
		if(this.isDead()) {
			return false;
		}
		if (!this.possesses(item)) {
			return false;
		}
		
		//TODO: Thest if the change check actually works!!
		MagicEffectRealization bevorUSE = target;
		item.useOn(target);
		return bevorUSE.equals(target);
	}

	/**
	 * If this object's inventory is empty, return false;
	 * otherwise choose a random item from inventory and delegate to
	 * useItem(Tradeable, MagicEffectRealization).
	 * @param target target on which item is to be used on
	 * @return false, if the object does not possess any item, otherwise the
	 * result of the delegation to useItem
	 */
	public boolean useRandomItem(MagicEffectRealization target) {
		if (this.inventory.isEmpty()) {
			return false;
		}
		Random random = new Random();
		Tradeable resItem = null;

		int index = random.nextInt(inventory.size());
		resItem = (Tradeable) inventory.toArray()[index];
        
		return this.useItem(resItem, target);
	}
	  
	/**
	 * If item or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead), no action can be taken and false is returned;
	 * call purchase on the item with this as seller and target as buyer;
	 * return true, if purchase was called successfully (returned true), false
	 * otherwise.
	 * @param item item to be sold
	 * @param target object the item is sold to (buyer)
	 * @return true, if purchase was called successfully (returned true), false
	 * otherwise.
	 */ 
	public boolean sellItem(Tradeable item, Trader target) {
		if (item == null) {
			throw new IllegalArgumentException("Can not use  a null spitemell");
		}
		if (target == null) {
			throw new IllegalArgumentException("Can not use  a null target");
		}
		if(this.isDead()) {
			return false;
		}
		return item.purchase(this, target);
		
		
		
	}

	/**
	 * If this object's inventory is empty, return false,
	 * otherwise choose a random item from inventory and delegate to
	 * sellItem(Tradeable, MagicEffectRealization).
	 * @param target object the item is sold to (buyer)
	 * @return false, if the object does not possess any item, otherwise the
	 * result of the delegation to sellItem
	 */
	public boolean sellRandomItem(Trader target) {
		if (this.inventory.isEmpty()) {
			return false;
		}
		Random random = new Random();
		Tradeable resItem = null;

		int index = random.nextInt(inventory.size());
		resItem = (Tradeable) inventory.toArray()[index];
        
		return this.sellItem(resItem, target);

	}

	/**
	 * Returns a string in the format
	 * "['name'('level'): 'HP'/'basicHP' 'MP'/'basicMP'; 'money' 'KnutOrKnuts'; knows 'knownSpells'; carries 'inventory']";
	 * where 'level' is the asterisks representation of the level
	 * (see MagicLevel.toString) and 'knownSpells' and 'inventory' use
	 * the default toString method of Java Set; 'KnutOrKnuts' is Knut
	 * if 'money' is 1, Knuts otherwise.
	 * E.g. [Ignatius(**): 70/100 100/150; 72 Knuts; knows [[Episkey(*): 5 mana; +20 HP], [Confringo: 10 mana; -20 HP]]; carries []]
	 * @return "['name'('level'): 'HP'/'basicHP' 'MP'/'basicMP'; 'money' 'KnutOrKnuts'; knows 'knownSpells'; carries 'inventory']"
	 */ 
	@Override
	public String toString() {
	    String levelString = level.toString(); 

	    String moneyString = (money == 1) ? "Knut" : "Knuts"; 

	    String knownSpellsString = knownSpells.toString(); 
	    String inventoryString = inventory.toString(); 

	    return "[" + name + "(" + levelString + "): " + HP + "/" + basicHP + " " + MP + "/" + basicMP +
	            "; " + money + " " + moneyString + "; knows " + knownSpellsString +
	            "; carries " + inventoryString + "]";
	}


 
	
	//MagicSource Interface
	
	/**
	 * If wizard is dead (isDead) no action can be taken and false is returned:
	 * check if level is at least levelNeeded, return false otherwise;
	 * if MP is less than manaAmount return false;
	 * subtract manaAmount from MP and return true.
	 * @param levelNeeded minimum magic level needed for the action 
	 * @param manaAmount amount of mana needed for the action 
	 * @return true, if mana can be successfully provided, false otherwise
	 */
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
		if(this.isDead()) {
			return false;
		}

		
		if (this.level == null || levelNeeded == null) {
	        throw new IllegalArgumentException("Magic level cannot be null");
	    }
		if (manaAmount < 0)  {
	        throw new IllegalArgumentException("manaAmount cannot be negativ");
	    }
	    
		
		if(this.level.compareTo(levelNeeded) < 0 ) {
			return false;			
		}
		if (this.MP < manaAmount) {
			return false;
		}
		this.MP -= manaAmount;
		return true;
	}

	//Trader Interface

	/**
	 * Return true, if the item is in the inventory, false otherwise
	 * @param item object is tested, if it possesses this item
	 * @return true, if the item is in the inventory, false otherwise
	 */
	@Override
	public boolean possesses(Tradeable item) {
		if (item == null ) {
	        throw new IllegalArgumentException("item cannot be null");
	    }

		return this.inventory.contains(item);
		//System.out.println(this.inventory);
	}

	/**
	 * Return true, if money is greater than or equal to amount, false otherwise
	 * @param amount object is tested, if it owns enough money to pay this amount
	 * @return true, if money is greater than or equal to amount, false otherwise
	 */
	@Override
	public boolean canAfford(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		return this.money >= amount;
	}

	/**
	 * Return true, if inventoryTotalWeight+weight is less than or equal to carryingCapacity, false otherwise
	 * @param weight test, if this weight can be added to object's inventory, without exceeding the
	 * carryingCapacity
	 * @return true, if inventoryTotalWeight+weight is less than or equal to carryingCapacity, false otherwise
	 */
	@Override
	public boolean hasCapacity(int weight) {
		if (weight < 0)  {
	        throw new IllegalArgumentException("weight cannot be negativ");
	    }
		
		return this.inventoryTotalWeight()+weight <= this.carryingCapacity;
	}

	/**
	 * If wizard is dead (isDead) no action can be taken and false is returned;
	 * if this owns enough money deduct amount from money and return true,
	 * return false otherwise
	 * @param amount to be payed
	 * @return true, if payment succeeds, false otherwise
	 */
	@Override
	public boolean pay(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		if(this.isDead()) {
			return false;
		}
		if (this.money >= amount) {
			this.money -= amount;
			return true;
		}
		return false;
	}
	    
	/**
	 * If wizard is dead (isDead), no action can be taken and false is returned;
	 * add amount to this object's money and return true.
	 * @param amount amount to be received
	 * @return true, if reception succeeds, false otherwise 
	 */
	@Override
	public boolean earn(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		if(this.isDead()) {
			return false;
		}
		this.money += amount;
		return true;
	}
	    
	/**
	 * Edd item to inventory if carryingCapacity is sufficient.
	 * returns true, if item is successfully added, false otherwise
	 * (carrying capacity exceeded or item is already in the inventory)
	 * @param item item to be added to object's inventory
	 * @return true. if item is successfully added, false otherwise
	 */
	@Override
	public boolean addToInventory(Tradeable item) {
		if (item == null ) {
	        throw new IllegalArgumentException("item cannot be null");
	    }
		
		if(this.inventoryTotalWeight()+item.getWeight() <= this.carryingCapacity && !this.inventory.contains(item)) {
			this.inventory.add(item);
			return true;
		}
		return false;
	}

	/**
	 * Remove item from inventory.
	 * returns true, if item is successfully removed, false otherwise
	 * (item not in the inventory).
	 * @param item item to be removed from object's inventory
	 * @return true, if item is successfully removed, false otherwise
	 */
	@Override
	public boolean removeFromInventory(Tradeable item) {
		if (item == null ) {
	        throw new IllegalArgumentException("item cannot be null");
	    }
		
		if (this.inventory.contains(item)){
			this.inventory.remove(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true, if this object's HP are not 0 (alive wizard).
	 * @return true, if the object is alive
	 */
	@Override
	public boolean canSteal() {
		if(this.isDead()) {
			return false;
		}
		return true;
	}
	
	/**
	 * If thief is null, IllegalArgumentException has to be thrown;
	 * if thief cannot steal (canSteal returns false) no action can be taken
	 * and false is returned;
	 * returns false if, the object's inventory is empty;
	 * otherwise transfers a random item from the this object's inventory into
	 * the thief's inventory;
	 * if the thief's inventory has not enough capacity, the object just vanishes
	 * and false is returned;
	 * returns true, if theft was successful.
	 * @param thief object that is stealing the item from the this-object.
	 * @return true, if theft was successful
	 */
	@Override
	public boolean steal(Trader thief) {
		if (thief == null) {
			throw new IllegalArgumentException("Can not steal withou exsistenc");
		}
		if (!thief.canSteal()) {
			return false;
		}
		if ( this.inventory.isEmpty()) {
			return false;
		}
		

		Random random = new Random();
		Tradeable resItem = null;

		int index = random.nextInt(inventory.size());
		resItem = (Tradeable) inventory.toArray()[index];
        
		if (!thief.addToInventory(resItem)) {
			this.inventory.remove(resItem);
			return false;
		}
		this.inventory.remove(resItem);
		return true;
		
		
	}

	/**
	 * Returns true if, this object's HP are 0 (dead wizard)
	 * @return true if the object is dead
	 */
	@Override
	public boolean isLootable() {
		return this.isDead();
	}

	/**
	 * Returns true if this object's HP are not 0 (alive wizard)
	 * @return true, if the object is alive
	 */
	@Override
	public boolean canLoot() {
		return !this.isDead();
	}
	
	/**
	 * Of looter is null, IllegalArgumentException has to be thrown;
	 * if looter cannot loot (canLoot returns false), no action can be taken
	 * and false is returned;
	 * if the this object can be looted (isLootable), transfer all the items
	 * in the object's inventory into the looter's inventory;
	 * items that don't fit in the looter's inventory because auf the weight
	 * limitation just vanish.
	 * returns true, if at least one item was successfully transferred, false
	 * otherwise.
	 * @param looter object that is looting this-object.
	 * @return true, if looting was successful, false otherwise
	 */
	@Override
	public boolean loot(Trader looter) {
		if (looter == null) {
			throw new IllegalArgumentException("Can not loot if u are dead!");
		}
		if(!looter.canLoot()) {
			return false;
		}
		
		if (isLootable()) {
            boolean itemsTransferred = false;
            for (Tradeable item : this.inventory) {
            	if (looter.addToInventory(item)) {
            		itemsTransferred = true;
            	}
                    
               
                
            }


            this.inventory.clear();

            return itemsTransferred;
        } else {
            return false;
        }
		
	}
	  
	//MagicEffectRealization Interface
	
	/**
	 * Reduce the object's HP by amount ensuring however that HP does not
	 * become negative.
	 * @param amount amount to be deducted from health 
	 */
	@Override
	public void takeDamage(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		this.HP -= amount;
		if (this.HP < 0 ) {
			this.HP = 0;
		}
	}
	    
	/**
	 * Reduce the object's HP by the percentage given of the object's basic
	 * HP value ensuring however, that HP does not become negative.
	 * Do calculations in double truncating to int only for the assignment
	 * @param percentage percentage of damage done
	 */
	@Override
    public void takeDamagePercent(int percentage) {
 
        if (percentage < 0) {
        	throw new IllegalArgumentException("percentage out of span");
        } else if (percentage > 100) {
        	throw new IllegalArgumentException("percentage out of span");
        }

        double damage = (percentage / 100.0) * this.basicHP;
        this.HP -= (int) damage;
        if (this.HP < 0) {
        	this.HP = 0;
        }
    }
	    
	/**
	 * Reduce the object's MP by amount ensuring however that MP does not
	 * become negative.
	 * @param amount amount to be deducted from mana 
	 */
	@Override
	public void weakenMagic(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		this.MP -= amount;
		if (this.MP < 0 ) {
			this.MP = 0;
		}
	}
	  
	/**
	 * Reduce the object's MP by the percentage given of the object's basic
	 * MP value ensuring however, that MP does not become negative.
	 * Do calculations in double truncating to int only for the assignment
	 * @param percentage percentage of damage done
	 */
	@Override
	public void weakenMagicPercent(int percentage) {
        if (percentage < 0) {
        	throw new IllegalArgumentException("percentage out of span");
        } else if (percentage > 100) {
        	throw new IllegalArgumentException("percentage out of span");
        }

        double damage = (percentage / 100.0) * this.basicMP;
        this.MP -= (int) damage;
        if (this.MP < 0) {
        	this.MP = 0;
        }
	}
	  
	/**
	 * Increase the object's HP by the amount given.
	 * @param amount amount to increase health
	 */
	@Override
	public void heal(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		this.HP += amount;
	}
	    
	/**
	 * Increase the object's HP by the percentage given of the object's
	 * basic HP. Do calculations in double truncating to int only for
	 * the assignment
	 * @param percentage percentage of healing done
	 */
	@Override
	public void healPercent(int percentage) {
        if (percentage < 0) {
        	throw new IllegalArgumentException("percentage out of span");
        } else if (percentage > 100) {
        	throw new IllegalArgumentException("percentage out of span");
        }

        double healing = (percentage / 100.0) * this.basicHP;
        this.HP += (int) healing;
        if (this.HP < 0) {
        	this.HP = 0;
        }
	}

	/**
	 * Increase the object's MP by the amount given.
	 * @param amount amount to increase mana
	 */
	@Override
	public void enforceMagic(int amount) {
		if (amount < 0)  {
	        throw new IllegalArgumentException("amount cannot be negativ");
	    }
		
		this.MP += amount;
	}
	  
	/**
	 * Increase the object's MP by the percentage given of the object's
	 * basic MP. Do calculations in double truncating to int only for
	 * the assignment
	 * @param percentage percentage of mana increase
	 */
	@Override
	public void enforceMagicPercent(int percentage) {
        if (percentage < 0) {
        	throw new IllegalArgumentException("percentage out of span");
        } else if (percentage > 100) {
        	throw new IllegalArgumentException("percentage out of span");
        }

        double healing = (percentage / 100.0) * this.basicMP;
        this.MP += (int) healing;
        if (this.MP < 0) {
        	this.MP = 0;
        }
	}
	    
	@Override
	public boolean isProtected(Spell s) {
		if (s == null) {
			throw new IllegalArgumentException("Can spell null");
		}
		
	    return protectedFrom.contains(s);
	}

	    
	/**
	 * Add all spells from attacks to instance variable protectedFrom
	 * @param attacks spells against which protection is provided
	 */
	@Override
	public void setProtection(Set<AttackingSpell> attacks) {
		if (attacks == null) {
			throw new IllegalArgumentException("Can spell null");
		}
		
		this.protectedFrom.addAll(attacks);
	}

	/**
	 * Remove all spells from attacks from instance variable protectedFrom
	 * @param attacks spells against which protection is removed 
	 */
	@Override
	public void removeProtection(Set<AttackingSpell> attacks) {
		if (attacks == null) {
			throw new IllegalArgumentException("Can spell null");
		}
		this.protectedFrom.removeAll(attacks);
	}
}