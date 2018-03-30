package com.oranj.pokemon.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oranj.pokemon.domain.item.Inventory;
import com.oranj.pokemon.domain.monsters.Pokemon;

public class Trainer {

	private long id;
	private List<Pokemon> pokemon;
	private Inventory inventory;
	private long victories;
	private long losses;
	
	public Trainer() {}
	
	public Trainer(long id, List<Pokemon> pokemon, Inventory inventory, long victories, long losses) {
		super();
		this.id = id;
		this.pokemon = pokemon;
		this.inventory = inventory;
		this.victories = victories;
		this.losses = losses;
	}
	
	public Trainer(List<Pokemon> pokemon, Inventory inventory) {
		this.pokemon = pokemon;
		this.inventory = inventory;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public List<? extends Pokemon> getPokemon() {
		return pokemon;
	}
	public void setPokemon(List<Pokemon> pokemon) {
		this.pokemon = pokemon;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public long getVictories() {
		return victories;
	}
	public void setVictories(long victories) {
		this.victories = victories;
	}
	public long getLosses() {
		return losses;
	}
	public void setLosses(long losses) {
		this.losses = losses;
	}
	
	@JsonIgnore
	public boolean hasPokemon(long swappedInPokemonId) {
		return pokemon.stream().anyMatch(pokemon -> pokemon.getId() == swappedInPokemonId);
	}
	
	@JsonIgnore
	public Pokemon getPokemon(long activePokemonId) {
		for (Pokemon _pokemon : pokemon) {
			if (_pokemon.getId() == activePokemonId) {
				return _pokemon;
			}
		}
		throw new RuntimeException("Could not find pokemon.");
	}
	
	@JsonIgnore
	public void markVictory() {
		victories++;
	}
	
	@JsonIgnore
	public void markLoss() {
		losses++;
	}
	
}
