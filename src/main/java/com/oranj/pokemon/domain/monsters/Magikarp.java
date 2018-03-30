package com.oranj.pokemon.domain.monsters;

import com.oranj.pokemon.constant.ElementType;
import com.oranj.pokemon.constant.Move;

public class Magikarp extends Pokemon {
	
	public Magikarp() {}

	public Magikarp(long id) {
		super(id, new ElementType[] {ElementType.WATER}, 20, 20, moves);
	}
	
	public static final Move[] moves = new Move[] {Move.SPLASH};
}
