package com.oranj.pokemon.domain;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.TurnAction;

public class TurnRequest {
	private long id;
	private long battleId;
	private TurnAction turnAction;
	private long requestingUserId;
	private Move move;
	private long activatedItemId;
	private long switchedInPokemonId;
	
	public TurnRequest() {}
	
	public TurnRequest(long battleId, TurnAction turnAction, long requestingUserId, Move move, long activatedItemId,
			long switchedInPokemonId) {
		super();
		this.battleId = battleId;
		this.turnAction = turnAction;
		this.requestingUserId = requestingUserId;
		this.move = move;
		this.activatedItemId = activatedItemId;
		this.switchedInPokemonId = switchedInPokemonId;
	}

	public long getBattleId() {
		return battleId;
	}
	public void setBattleId(long battleId) {
		this.battleId = battleId;
	}
	public TurnAction getTurnAction() {
		return turnAction;
	}
	public void setTurnAction(TurnAction turnAction) {
		this.turnAction = turnAction;
	}
	public long getRequestingTrainerId() {
		return requestingUserId;
	}
	public void setRequestingUserId(long requestingUserId) {
		this.requestingUserId = requestingUserId;
	}
	public Move getMove() {
		return move;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public long getActivatedItemId() {
		return activatedItemId;
	}
	public void setActivatedItemId(long activatedItemId) {
		this.activatedItemId = activatedItemId;
	}
	public long getSwitchedInPokemonId() {
		return switchedInPokemonId;
	}
	public void setSwitchedInPokemonId(long switchedInPokemonId) {
		this.switchedInPokemonId = switchedInPokemonId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}

