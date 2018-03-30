package com.oranj.pokemon.domain.monsters;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.ElementType;

public class Mewtwo extends Pokemon {
	
	public Mewtwo() {}

	public Mewtwo(long id) {
		super(id, new ElementType[] {ElementType.PSYCHIC}, 106, 106, moves);
	}
	
	public static final Move[] moves = new Move[] {Move.PSYCHIC, Move.BLIZZARD};

}
