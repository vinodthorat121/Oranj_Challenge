package com.oranj.pokemon.domain.item;

import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.domain.Trainer;
import com.oranj.pokemon.domain.monsters.Pokemon;

public class Potion extends Item {
	
	public Potion() {}

	public Potion(long id, int quantity) {
		super(id, quantity);
	}

	@Override 
	public void apply(Battle battle, Trainer activeTrainer, Trainer idleTrainer) {
		// YOUR CODE HERE (don't delete these comments):
		// be sure:
		// 1.) active pokemon is healed by 20 hp
		// 2.) pokemon is not overhealed past max hp
		long pokemonBattleId = battle.getActivePokemonId();
		Pokemon activePokemonBattle = activeTrainer.getPokemon(pokemonBattleId);
		int newHp = Math.min(activePokemonBattle.getHp()+20, activePokemonBattle.getMaxHp());
		activePokemonBattle.setHp(newHp);
		return;
	}
	

}
