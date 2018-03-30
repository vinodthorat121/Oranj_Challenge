package com.oranj.pokemon.domain.monsters;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.ElementType;

public class Charizard extends Pokemon {
	
	public Charizard() {}

	public Charizard(long id) {
		super(id, new ElementType[] {ElementType.FIRE}, 78, 78, moves);
	}
	
	public static final Move[] moves = new Move[] {Move.FLAMETHROWER, Move.DRAGON_RAGE};

}
