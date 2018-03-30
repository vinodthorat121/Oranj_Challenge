package com.oranj.pokemon.domain.item;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Inventory {

	private List<Item> items;
	
	private long id;
	
	public Inventory() {}
	
	public Inventory(List<Item> items) {
		super();
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonIgnore
	public boolean hasItem(long activatedItemId) {
		return items.stream().anyMatch(item -> item.getId() == activatedItemId);
	}

	@JsonIgnore
	public void remove(long activatedItemId) {
		for (int i=0; i<items.size(); i++) {
			if (items.get(i).getId() == activatedItemId) {
				items.remove(i);
				return;
			}
		}
		throw new RuntimeException("Item not found!");
	}

	@JsonIgnore
	public Item getItem(long activatedItemId) {
		for (int i=0; i<items.size(); i++) {
			if (items.get(i).getId() == activatedItemId) {
				return items.get(i);
			}
		}
		throw new RuntimeException("Item not found!");
	}

	@JsonIgnore
	public Item consumeItem(long activatedItemId) {
		for (int i=0; i<items.size(); i++) {
			Item item = items.get(i);
			if (item.getId() == activatedItemId) {
				item.decrement();
				if (item.getQuantity() == 0) {
					items.remove(i);
				}
				return item;
			}
		}
		throw new RuntimeException("User does not have this item.");
	}
	
}
