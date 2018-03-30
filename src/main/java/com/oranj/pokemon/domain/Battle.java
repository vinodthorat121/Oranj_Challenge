package com.oranj.pokemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Battle {

	private long id;
	private long trainer1Id;
	private long trainer1SelectedPokemonId;
	private long trainer2Id;
	private long trainer2SelectedPokemonId;
	private Boolean isTrainer1Turn;
	private Boolean isTrainer1Victor;
	
	public Battle() {}
	
	public Battle(long id, long trainer1Id, long trainer2Id, Boolean isTrainer1Victor) {
		this.id = id;
		this.trainer1Id = trainer1Id;
		this.trainer2Id = trainer2Id;
		this.isTrainer1Victor = isTrainer1Victor;
	}
	
	public Battle(long id, long trainer1Id, long trainer1SelectedPokemonId, long trainer2Id,
			long trainer2SelectedPokemonId, Boolean isTrainer1Turn) {
		super();
		this.id = id;
		this.trainer1Id = trainer1Id;
		this.trainer1SelectedPokemonId = trainer1SelectedPokemonId;
		this.trainer2Id = trainer2Id;
		this.trainer2SelectedPokemonId = trainer2SelectedPokemonId;
		this.isTrainer1Turn = isTrainer1Turn;
	}

	public Battle(long trainer1Id, long trainer2Id) {
		super();
		this.trainer1Id = trainer1Id;
		this.trainer2Id = trainer2Id;
	}
	
	public long getTrainer1Id() {
		return trainer1Id;
	}

	public void setTrainer1Id(long trainer1Id) {
		this.trainer1Id = trainer1Id;
	}

	public long getTrainer1SelectedPokemonId() {
		return trainer1SelectedPokemonId;
	}

	public void setTrainer1SelectedPokemonId(long trainer1SelectedPokemonId) {
		this.trainer1SelectedPokemonId = trainer1SelectedPokemonId;
	}

	public long getTrainer2Id() {
		return trainer2Id;
	}

	public void setTrainer2Id(long trainer2Id) {
		this.trainer2Id = trainer2Id;
	}

	public long getTrainer2SelectedPokemonId() {
		return trainer2SelectedPokemonId;
	}

	public void setTrainer2SelectedPokemonId(long trainer2SelectedPokemonId) {
		this.trainer2SelectedPokemonId = trainer2SelectedPokemonId;
	}

	public Boolean getIsTrainer1Turn() {
		return isTrainer1Turn;
	}

	public void setIsTrainer1Turn(Boolean isTrainer1Turn) {
		this.isTrainer1Turn = isTrainer1Turn;
	}

	public Boolean getIsTrainer1Victor() {
		return isTrainer1Victor;
	}

	public void setIsTrainer1Victor(Boolean isTrainer1Victor) {
		this.isTrainer1Victor = isTrainer1Victor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonIgnore
	public void toggleTurn() {
		isTrainer1Turn = !isTrainer1Turn;
	}

	@JsonIgnore
	public void switchInPokemon(long swappedInPokemonId) {
		if (isTrainer1Turn) {
			trainer1SelectedPokemonId = swappedInPokemonId;
		} else {
			trainer2SelectedPokemonId = swappedInPokemonId;
		}
	}
	
	@JsonIgnore
	public boolean isInProgress() {
		return isTrainer1Victor == null;
	}
	
	@JsonIgnore
	public long getActivePokemonId() {
		if (isTrainer1Turn) {
			return trainer1SelectedPokemonId;
		}
		return trainer2SelectedPokemonId;
	}

}
