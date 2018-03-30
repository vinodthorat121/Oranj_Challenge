package com.oranj.pokemon.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum ElementType {
	// http://pokemon.wikia.com/wiki/Pok%C3%A9mon_types
	BUG,
	DRAGON,
	ICE,
	FIGHTING,
	FIRE,
	FLYING,
	GRASS,
	GHOST,
	GROUND,
	ELECTRIC,
	NORMAL,
	POISON,
	PSYCHIC,
	ROCK,
	WATER;
 	
	public ElementType[] getSuperEffectiveAgainst() {
		switch (this) {
		case ELECTRIC:
			return new ElementType[] {ElementType.FLYING, ElementType.WATER};
		case FIGHTING:
			return new ElementType[] {ElementType.ICE, ElementType.NORMAL, ElementType.WATER};
		case FIRE:
			return new ElementType[] {ElementType.BUG, ElementType.GRASS, ElementType.ICE};
		case FLYING:
			return new ElementType[] {ElementType.BUG, ElementType.FIGHTING, ElementType.GRASS};
		default:
			// ...laziness
			return new ElementType[] {};
		}
	}
	
	public boolean isSuperEffectiveAgainst(ElementType[] targetTypes) {
		Set<ElementType> statusesSet = new HashSet<ElementType>(Arrays.asList(getSuperEffectiveAgainst()));
		Set<ElementType> targetStatusesSet = new HashSet<ElementType>(Arrays.asList(targetTypes));
		return !Collections.disjoint(statusesSet, targetStatusesSet);
	}
	
}
