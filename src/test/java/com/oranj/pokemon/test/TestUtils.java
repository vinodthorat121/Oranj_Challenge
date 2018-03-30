package com.oranj.pokemon.test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.oranj.pokemon.domain.Trainer;
import com.oranj.pokemon.domain.item.Inventory;
import com.oranj.pokemon.domain.item.Item;
import com.oranj.pokemon.domain.item.ParalyzeHeal;
import com.oranj.pokemon.domain.item.Potion;
import com.oranj.pokemon.domain.monsters.Charizard;
import com.oranj.pokemon.domain.monsters.Magikarp;
import com.oranj.pokemon.domain.monsters.Mewtwo;
import com.oranj.pokemon.domain.monsters.Pikachu;
import com.oranj.pokemon.domain.monsters.Pokemon;

public class TestUtils {

	public static File loadFromResources(String file) throws URISyntaxException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL resource = classloader.getResource(file);
		return new File(resource.toURI());
	}
	
	public static Path pathOfResource(String file) throws URISyntaxException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL resource = classloader.getResource(file);
		return Paths.get(resource.toURI());
	}
	
	 static List<Trainer> trainerSet2() {
		Trainer trainer1 = trainer1();
		Trainer trainer2 = trainer2();
		Trainer trainer3 = trainer3();
		
		List<Trainer> allTrainers = new ArrayList<Trainer>();
		allTrainers.add(trainer1);
		allTrainers.add(trainer2);
		allTrainers.add(trainer3);
		return allTrainers;
	}

	static Trainer trainer1() {
		List<Pokemon> trainer1Pokemon = new ArrayList<Pokemon>();
		Pokemon pokemon1 = new Pikachu(1);
		pokemon1.setHp(20);
		trainer1Pokemon.add(pokemon1);
		List<Item> trainer1Items = new ArrayList<Item>();
		trainer1Items.add(new Potion(1, 3));
		Inventory trainer1Inventory = new Inventory(trainer1Items);
		Trainer trainer1 = new Trainer(1, trainer1Pokemon, trainer1Inventory, 3, 8);
		return trainer1;
	}

	static Trainer trainer2() {
		List<Pokemon> trainer2Pokemon = new ArrayList<Pokemon>();
		trainer2Pokemon.add(new Charizard(2));
		trainer2Pokemon.add(new Magikarp(3));
		List<Item> trainer2Items = new ArrayList<Item>();
		trainer2Items.add(new ParalyzeHeal(2, 3));
		Inventory trainer2Inventory = new Inventory(trainer2Items);
		Trainer trainer2 = new Trainer(2, trainer2Pokemon, trainer2Inventory, 0, 0);
		return trainer2;
	}

	 static Trainer trainer3() {
		List<Pokemon> trainer3Pokemon = new ArrayList<Pokemon>();
		trainer3Pokemon.add(new Mewtwo(4));
		List<Item> trainer3Items = new ArrayList<Item>();
		Inventory trainer3Inventory = new Inventory(trainer3Items);
		Trainer trainer3 = new Trainer(3, trainer3Pokemon, trainer3Inventory, 8, 3);
		return trainer3;
	}
	
	 static Trainer trainer4() {
		List<Pokemon> trainer4Pokemon = new ArrayList<Pokemon>();
		trainer4Pokemon.add(new Mewtwo(5));
		List<Item> trainer4Items = new ArrayList<Item>();
		Inventory trainer4Inventory = new Inventory(trainer4Items);
		Trainer trainer4 = new Trainer(4, trainer4Pokemon, trainer4Inventory, 8, 3);
		return trainer4;
	}
}
