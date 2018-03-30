package com.oranj.pokemon.constant;

public enum Move {
	THUNDERBOLT(ElementType.ELECTRIC, 30, Status.PARALYZED), 
	QUICK_ATTACK(ElementType.NORMAL, 20, null),
	SPLASH(ElementType.WATER, 0, null), 
	FLAMETHROWER(ElementType.FIRE, 95, Status.BURNED),
	DRAGON_RAGE(ElementType.DRAGON, 40, null), 
	PSYCHIC(ElementType.PSYCHIC, 90, null),
	BLIZZARD(ElementType.ICE, 120, Status.FROZEN);
	
	private ElementType type;
	
	private int damage;
	
	private Status statusInduced;

	private Move(ElementType type, int damage, Status statusInduced) {
		this.type = type;
		this.damage = damage;
		this.statusInduced = statusInduced;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Status getStatusInduced() {
		return statusInduced;
	}

	public void setStatusInduced(Status statusInduced) {
		this.statusInduced = statusInduced;
	}
	
}
