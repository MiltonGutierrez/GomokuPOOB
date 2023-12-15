package test;
import domain.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GomokuTest {
	private Gomoku board = Gomoku.getGomoku();
	
	@BeforeEach
	void setUp() throws Exception {
		board.setDimension(15);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void shouldCreateRivals1() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.createRivals();
		for(Player p: board.getPlayers().values()) {
			assertTrue(p instanceof Human);
		}
	}
	
	@Test
	void shouldCreateRivals2() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setOpponent("pve");
		board.setMachineType("Expert");
		board.createRivals();
		assertTrue(board.loadPlayer("Mutsia") instanceof Human);
		assertTrue(board.loadPlayer("machine") instanceof Machine);
	}
	@Test
	void shouldStartGame() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		assertTrue(board.ok());
		assertNotEquals(board.getTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Mutsia").returnTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Miltown").returnTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Mutsia").getTokenToUse(), null);
		assertNotEquals(board.loadPlayer("Miltown").getTokenToUse(), null);
		assertNotEquals(board.getTypesOfTokens(), null);
		assertEquals(board.getTurn(), "Mutsia");
		
	}
	
	@Test
	void shouldAddToken() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.setMachineType("Agressive");
		board.createRivals();
		board.startGame();
		board.addToken("Normal", board.getTurn(), new int[]{0, 0});
		assertTrue(board.ok());
		board.nextTurn();
		board.addToken("Temporal", board.getTurn(), new int[]{0, 0});
		assertTrue(board.ok());
		board.nextTurn();
		board.addToken("Heavy", board.getTurn(), new int[]{0, 1});
		assertTrue(board.ok());
		assertEquals(board.getTokens().size(), 4); //Son 4 más la adición del metodo CalculateLastPositions
	}
	
	@Test
	void shouldValidPlay() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		assertTrue(board.validPlay(0, 0));
	}
	
	@Test
	void shouldCalculateLastPositions() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		board.addToken("Normal", board.getTurn(),new int[] {0, 1});
		board.getToken(0, 1).setIdentifier('D');
		board.calculateLastPositionTokens();
		assertEquals(board.getLastPositionTokens().size(), 1);
	}
	
	@Test
	void shouldCreateBoxes() {
		board.createBox("Normal", new int[] {0,0});
		assertTrue(board.ok());
		board.createBox("Golden", new int[] {0,0});
		assertTrue(board.ok());
		board.createBox("Teleporting", new int[] {0,0});
		assertTrue(board.ok());
		board.createBox("Explosive", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldCreateMatrixBox() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		for(int i = 0; i < Gomoku.getGomoku().getDimension(); i++) {
			for(int j = 0; j < Gomoku.getGomoku().getDimension(); j++) {
				assertTrue(Gomoku.getGomoku().getBox(i, j) instanceof Box);
			}
		}
	}
	
	void shouldExplosiveBoxBeExplosive() {
		board.setDimension(5);
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		
	}
}


