package com.oranj.pokemon.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.Status;
import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.domain.Trainer;
import com.oranj.pokemon.domain.monsters.Pokemon;
import com.oranj.pokemon.repository.BattleRepository;
import com.oranj.pokemon.repository.TrainerRepository;
import com.oranj.pokemon.service.BattleService;

public class BattleServiceTest {
	
	private TrainerRepository trainerRepository;
	private BattleService battleService;
	private BattleRepository battleRepository;
	
	private Path trainerRepoBackup;
	private Path battleRepoBackup;
	private Battle battle1;
	
	@Before
	public void before() throws Exception {
		trainerRepoBackup = File.createTempFile("trainers_backup.db", "json").toPath();
		Files.copy(TestUtils.pathOfResource("trainers_2.db.json"), trainerRepoBackup, StandardCopyOption.REPLACE_EXISTING);
		battleRepoBackup = File.createTempFile("battles_backup.db", "json").toPath();
		Files.copy(TestUtils.pathOfResource("battles_1.db.json"), battleRepoBackup, StandardCopyOption.REPLACE_EXISTING);
		
		trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_1.db.json"));
		Trainer trainer1 = trainerRepository.get(1);
		Trainer trainer2 = trainerRepository.get(2);
		battleService = new BattleService(battleRepository, trainerRepository, trainer1, trainer2);
		battle1 = battleRepository.get(1);
	}
	
	@After
	public void after() throws IOException, URISyntaxException {
		Files.copy(battleRepoBackup, TestUtils.pathOfResource("battles_1.db.json"), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(trainerRepoBackup, TestUtils.pathOfResource("trainers_2.db.json"), StandardCopyOption.REPLACE_EXISTING);
	}

	@Test
	// Note: provide implementation in Potion.java
	public void applyPotion_HealsPokemon() throws Exception {
		battleService.applyItem(1, battle1);
		Pokemon healedPokemon = battleService.getActivePokemon(battle1);
		Assert.assertTrue(healedPokemon.getHp() > 20);
	}
	
	@Test
	// Note: provide implementation in Potion.java
	public void applyPotion_HealsPokemonNoOverflow() throws Exception {
		battleService.applyItem(1, battle1);
		Pokemon healedPokemon = battleService.getActivePokemon(battle1);
		Assert.assertTrue(healedPokemon.getHp() == 35);
	}
	
	@Test
	// Note: provide implementation in ParalyzeHeal.java
	public void paralyzeHeal_UnparalyzesPokemon() throws Exception {
		battle1.setTrainer2SelectedPokemonId(2);
		battle1.toggleTurn();
		Pokemon activePokemon = battleService.getActivePokemon(battle1);
		activePokemon.setStatuses(new Status[] {Status.PARALYZED});
		battleService.applyItem(2, battle1);
		Assert.assertTrue(activePokemon.getStatuses().length == 0);
	}
	
	@Test
	public void applyMove_DamagesOtherPokemon() throws Exception {
		battleService.applyMove(Move.THUNDERBOLT, battle1);
		Pokemon idlePokemon = battleService.getIdlePokemon(battle1);
		Assert.assertTrue(idlePokemon.getHp() < 20);
	}
	
	@Test
	public void applyMove_DamagesOtherPokemonNoOverflow() throws Exception {
		battleService.applyMove(Move.THUNDERBOLT, battle1);
		Pokemon idlePokemon = battleService.getIdlePokemon(battle1);
		Assert.assertTrue(idlePokemon.getHp() == 0);
	}

	@Test
	public void applyMove_AffectsOtherPokemonStatus() throws Exception {
		battleService.applyMove(Move.THUNDERBOLT, battle1);
		Pokemon idlePokemon = battleService.getIdlePokemon(battle1);
		Status status = idlePokemon.getStatuses()[0];
		Assert.assertTrue(status.equals(Status.PARALYZED));
	}
	
}
