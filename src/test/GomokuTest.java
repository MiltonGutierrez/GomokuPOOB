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
	
	@Test
	void shouldAddTokenCreateNormal() {
		assertTrue(board.addToken("Normal", "Mutsia", new int[] {0,0}) instanceof NormalToken);
		assertTrue(board.ok());
	}
	@Test
	void shouldAddTokenCreateHeavy() {
		assertTrue(board.addToken("Heavy", "Mutsia", new int[] {0,3}) instanceof HeavyToken);
		assertTrue(board.ok());
	}
	@Test
	void shouldAddTokenCreateTemporal() {
		assertTrue(board.addToken("Temporal", "Mutsia", new int[] {0,1}) instanceof TemporalToken);
		assertTrue(board.ok());
	}
	
	@Test
	void shouldAddTokenSetNormal() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t = board.addToken("Normal", "Mutsia", new int[] {0,0});
		assertTrue(board.ok());
		board.addToken("Mutsia", t, new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldAddTokenSetTemporal() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t3 = board.addToken("Temporal", "Mutsia", new int[] {0,3});
		assertTrue(board.ok());
		board.addToken("Mutsia", t3, new int[] {0,3});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldBeTemporalToken() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
	    board.loadPlayer("Mutsia").addTokenToUse("Temporal");
	    board.play(0, 0);
	    board.play(0, 1);
	    board.play(0, 2);
	    board.play(0, 3);
	    assertEquals(board.getToken(0, 0), null);
	}
	
	@Test
	void shouldAddTokenSetHeavy() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t2 = board.addToken("Heavy", "Mutsia", new int[] {0,1});
		assertTrue(board.ok());
		board.addToken("Mutsia", t2, new int[] {0,1});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldBeHeavyToken() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t = board.addToken("Heavy", "Mutsia", new int[] {0,0});
		assertEquals(t.getValue(), 2);
	}
	
	@Test
	void shouldCreateNormalBox() {
		board.createBox("Normal", new int[] {0,0});
		assertTrue(board.ok());
		
	}
	
	@Test
	void shouldCreateGoldenBox() {
		board.createBox("Golden", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldCreateTeleportingBox() {
		board.createBox("Teleporting", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldCreateExplosiveBox() {
		board.createBox("Explosive", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldExplodeNearTokens() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
	    board.startGame();
	    Box b = board.createBox("Explosive", new int[] {0,0});
	    board.getBoxMatrix()[0][0] = b;
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
	void shouldCalculateLastPositions() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		Token t = board.addToken("Normal", board.getTurn(),new int[] {0, 1}); //
		board.addToken("Mutsia", t, new int[] {0,1});
		board.getToken(0, 1).setIdentifier('D');
		board.calculateLastPositionTokens();
		assertEquals(board.getLastPositionTokens().size(), 1);
	}
}


