package a12047732;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Concoctions can affect health and mana of the target at the same time and additionally
 * cast any number of spells
 * <p>
 * It is not allowed for health and mana to be both 0 and spells to be empty.
 * The concoction must at least have one effect
 */
public class Concoction extends Potion {
	/**
	 * change of health on target; may be any int value
	 */
	private int health;
	/**
	 * change of mana on target; may be any int value
	 */
	private int mana;
	/**
	 * spells cast when concoction is consumed; must not be null but may be empty;
	 * Use ArrayList as concrete type
	 */
	private List<Spell> spells;

	/**
	 * @param name name
	 * @param usages number of usages still left
	 * @param price price
	 * @param weight weight
	 * @param health change of health on target
	 * @param mana change of mana on target
	 * @param spells list of spells that are cast when consuming the concoction 
	 */
	public Concoction(String name,	int usages, int price, int weight, int health, int mana, List<Spell> spells) {
		super(name, usages, price, weight);

		if (spells == null) {
			throw new IllegalArgumentException("spells can not be null.");
		}
		
		this.spells = new ArrayList<Spell>(spells);
		
		boolean healthExsits = health != 0;
		boolean manaExsits = mana != 0;
		boolean spellsExsits = spells.size() != 0;

		if ( !healthExsits && !manaExsits && !spellsExsits ) {
			throw new IllegalArgumentException("Concoction must have an efect");
		}
		
		this.health = health;
		this.mana = mana;
		
	}
	
	/**
	 * Returns "; '+/-''health' HP; '+/-''mana' MP; cast 'spells' ";
	 * here '+/-' denotes the appropriate sign, spells will be a bracketed list
	 * of spells (Java default toString method for lists)
	 * e.g. (total result of toString) "[My Brew; 2 g; 2 Knuts; 4 gulps; -5 HP; +10 MP; cast [[Confringo -20 HP], [Diffindo -15 HP]]]"
	 * If health or mana is 0 or spells is empty, then the respective part(s) are suppressed
	 * e. g. "[Your Brew; 2 g; 1 Knut; 1 gulp; +5 MP]
	 * @return "; '+/-''health' HP; '+/-''mana' MP; cast 'spells' "
	 */

	@Override
	public String additionalOutputString() {
	    StringBuilder res = new StringBuilder();

	    // Append health if it's not 0
	    if (this.health != 0) {
	        res.append("; ");
	        if (this.health > 0) {
	            res.append("+"); // Add plus sign for positive health
	        }
	        if (this.health < 0) {
	            res.append("-"); // Add plus sign for positive health
	        }
	        
	        res.append(Math.abs(this.health)).append(" HP");
	    }

	    // Append mana if it's not 0
	    if (this.mana != 0) {
	        res.append("; ");
	        if (this.mana > 0) {
	            res.append("+"); // Add plus sign for positive mana
	        }
	        if (this.mana < 0) {
	            res.append("-"); // Add plus sign for positive mana
	        }
	        res.append(Math.abs(this.mana)).append(" MP");
	    }

	    // Append spells if the list is not empty
	    if (!spells.isEmpty()) {
	        res.append("; cast ").append(spells.toString());
	    }

	    return res.toString();
	}





	/**
	 * If usages is greater than 0 reduce usages by 1 (tryUsage method) and
	 * change HP of target by health (call method heal(health) or takeDamage(health)
	 * depending on sign of health) and
	 * change MP of target by mana (call method enforceMagic(magic) or weakenMagic(magic)
	 * depending on sign of mana) and
	 * call cast Method for every spell in spells.
	 * @param target target that takes the magic effects
	 */
	@Override  
	public void useOn(MagicEffectRealization target) {
	    if (this.getUsages() > 0) {
	        this.tryUsage();

	        if (this.health > 0) {
	            target.heal(this.health);
	        } else if (this.health < 0) {
	            target.takeDamage(-this.health);
	        }

	        if (this.mana > 0) {
	            target.enforceMagic(this.mana);
	        } else if (this.mana < 0) {
	            target.weakenMagic(-this.mana);
	        }
	        
	        // Iterate over each spell in the spells list and call its cast method
	        for (Spell spell : this.spells) {
	        	spell.cast(this, target);
	        }
	    }
	}

	
	
	
	
	
	
	
}