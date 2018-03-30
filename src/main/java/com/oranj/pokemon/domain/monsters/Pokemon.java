package com.oranj.pokemon.domain.monsters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.oranj.pokemon.constant.ElementType;
import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Pikachu.class, name = "Pikachu"),
    @JsonSubTypes.Type(value = Magikarp.class, name = "Magikarp"),
    @JsonSubTypes.Type(value = Mewtwo.class, name = "Mewtwo"),
    @JsonSubTypes.Type(value = Charizard.class, name = "Charizard") 
})
public abstract class Pokemon  {

	private long id;
	private ElementType[] types;
	private int hp;
	private int maxHp;
	private Move[] moves;
	private Status[] statuses = new Status[]{};
	
	public Pokemon() {}
	
	public Pokemon(long id, ElementType[] types, int healthPoints, int maxHp, Move[] moves) {
		super();
		this.id = id;
		this.types = types;
		this.hp = healthPoints;
		this.maxHp = maxHp;
		this.moves = moves;
	}

	public void peformMove(Move move) {
		// todo implememt
	}

	public ElementType[] getTypes() {
		return types;
	}

	public void setTypes(ElementType[] types) {
		this.types = types;
	}

	public int getHp() {
		return hp;
	}

	// TODO unit test
	public void setHp(int hp) {
		this.hp = hp;
	}

	public Move[] getMoves() {
		return moves;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHealthPoints) {
		this.maxHp = maxHealthPoints;
	}

	public Status[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Status[] statuses) {
		this.statuses = statuses;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// TODO: remove unit test?
	public void removeStatus(Status paralyzed) {
		Set<Status> statusesSet = new HashSet<Status>(Arrays.asList(statuses));
		statusesSet.remove(paralyzed);
		statuses = (statusesSet.toArray(new Status[statusesSet.size()]));
	}

	public boolean knowsMove(Move move) {
		for (Move _move : moves) {
			if (_move.equals(move))
				return true;
		}
		return false;
	}
	
}
