package com.oranj.pokemon.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.domain.Trainer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ParalyzeHeal.class, name = "ParalyzeHeal"),
    @JsonSubTypes.Type(value = Potion.class, name = "Potion")
})
public abstract class Item  {
	
	private long id;
	private int quantity;
	
	public Item() {}
	
	public Item(long id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public void decrement() {
		quantity--;
	}

	@JsonIgnore
	public abstract void apply(Battle battle, Trainer activeTrainer, Trainer idleTrainer);
	
}
