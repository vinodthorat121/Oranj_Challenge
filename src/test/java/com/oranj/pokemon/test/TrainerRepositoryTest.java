package com.oranj.pokemon.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

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
import com.oranj.pokemon.repository.TrainerRepository;

import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial;

public class TrainerRepositoryTest {

	private Path backup;

	@Before
	public void before() throws IOException, URISyntaxException {
		backup = File.createTempFile("trainers_backup.db", "json").toPath();
		Files.copy(TestUtils.pathOfResource("trainers_2.db.json"), backup, StandardCopyOption.REPLACE_EXISTING);
	}
	
	@After
	public void after() throws IOException, URISyntaxException {
		Files.copy(backup, TestUtils.pathOfResource("trainers_2.db.json"), StandardCopyOption.REPLACE_EXISTING);
	}
	
//	@Test
	public void init() throws Exception {
		List<Trainer> allTrainers = TestUtils.trainerSet2();
		TrainerRepository trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		trainerRepository.updateRepo(allTrainers);
	}
	
	@Test
	public void loadAllRecords_parsesJson() throws Exception {
		TrainerRepository trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		List<Trainer> allRecordsActual = trainerRepository.loadAllRecords();	
		
		List<Trainer> allRecordsExpected = TestUtils.trainerSet2();
		
		//ReflectionAssert.assertReflectionEquals(allRecordsExpected, allRecordsActual);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(allRecordsExpected,allRecordsActual);

	}
	
	@Test
	public void saveNew_insertsNewRecordAtEnd() throws Exception {
		
		TrainerRepository trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		Trainer trainer4 = TestUtils.trainer4();
		trainerRepository.saveNew(trainer4);
		List<Trainer> allRecordsActual = trainerRepository.loadAllRecords();
		
		List<Trainer> allRecordsExpected = TestUtils.trainerSet2();
		allRecordsExpected.add(trainer4);
		
		//ReflectionAssert.assertReflectionEquals(allRecordsExpected, allRecordsActual);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(allRecordsExpected, allRecordsActual);

	}
	
	@Test
	public void getById_FetchesCorrectTrainer() throws Exception {
		TrainerRepository trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		Trainer actualTrainer = trainerRepository.get(2);
		Trainer expectedTrainer = TestUtils.trainer2();
		//ReflectionAssert.assertReflectionEquals(expectedTrainer, actualTrainer);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(expectedTrainer, actualTrainer);

	}
	
	@Test
	public void updateExisting_UpdatesTrainerRecord() throws Exception {
		TrainerRepository trainerRepository = new TrainerRepository(TestUtils.loadFromResources("trainers_2.db.json"));
		
		Trainer updatedTrainer = TestUtils.trainer1();
		updatedTrainer.getPokemon().get(0).setHp(5);
		updatedTrainer.markVictory();
		trainerRepository.updateExisting(updatedTrainer);
		Trainer actualTrainerAfterUpdate = trainerRepository.get(1);
		//ReflectionAssert.assertReflectionEquals(updatedTrainer, actualTrainerAfterUpdate);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(updatedTrainer, actualTrainerAfterUpdate);

	}
	
}
