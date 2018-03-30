package com.oranj.pokemon.domain.monsters;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.ElementType;

public class Pikachu extends Pokemon {
	
	public Pikachu() {}

	public Pikachu(long id) {
		super(id, new ElementType[] {ElementType.ELECTRIC}, 35, 35, moves);
	}
	
	public static final Move[] moves = new Move[] {Move.QUICK_ATTACK, Move.THUNDERBOLT};

}
