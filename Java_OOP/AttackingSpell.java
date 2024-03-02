package a12047732;

import java.util.HashSet;
import java.util.Set;

/**
 * Magic spells that do some sort of damage to a target
 */
public class AttackingSpell extends Spell {
	/**
	 * Defines, if HP or MP is affected.
	 * <p>
	 * type == true: affects HP
	 * <p>
	 * type == false: affects MP
	 */
	private boolean type;
	/**
	 * Defines, if amount is interpreted as an absolute value or as a percentage.
	 * <p>
	 * percentage == true: deduct value to subtract as 'amount' percentage of basic value
	 * <p>
	 * percentage == false: subtract amount directly 
	 */
	private boolean percentage;
	/**
	 * Has to be non negative.
	 * <p>
	 * if percentage==true, amount must be in the interval [0,100]
	 */
	private int amount;
	
	/**
	 * @param name name
	 * @param manaCost manaCost
	 * @param levelNeeded levelNeeded
	 * @param type defines if health or mana is affected
	 * @param percentage defines if amount is an absolute or a percentage value
	 * @param amount amount
	 */
	public AttackingSpell(String name,	int manaCost, MagicLevel levelNeeded, boolean type, boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		this.type = type;
		this.percentage = percentage;
		 if(amount < 0 ) {
			 throw new IllegalArgumentException("amount can not be negativ.");
		 }
		 if(percentage ) {
			 if (amount > 100) {
				 throw new IllegalArgumentException("percentage amount not between [0,100].");
			 }
		 }
		 this.amount = amount;
	}


	/**
	 * If the target is protected against this spell (isProtected), then protection against
	 * exactly this spell is removed (removeProtection).
	 * Otherwise use one of the functions takeDamage, takeDamagePercent, weakenMagic or
	 * weakenMagicPercent on target according to the flags type and percentage.
	 * @param target target that takes the damage
	*/
	@Override
	public void doEffect(MagicEffectRealization target) {
		if(target.isProtected(this)){
			Set<AttackingSpell> spells = new HashSet<>();
	        spells.add(this); 
	        target.removeProtection(spells);
		}else {
			if(type) {
				if(percentage) {
					target.takeDamagePercent(amount);
				}else {
					target.takeDamage(amount);
				}
			}else {
				if(percentage) {
					target.weakenMagicPercent(amount);
				}else {
					target.weakenMagic(amount);
				}
			}
		}
	}

	
	
	@Override
	public String additionalOutputString() {
	    String percentageString = percentage ? " % " : " "; // Add space around % if true
	    String hpOrMpString = type ? "HP" : "MP";

	    return "; -" + amount + percentageString + hpOrMpString;
	}

	
	
	
	
	
	
	
	
	
}