package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Player class
public class PlayerTest {
    private Player player1;
    private Player player2;

    @BeforeEach
    public void runBefore() {
        player1 = new Player("a",100);
        player2 = new Player("b",1);
    }

    @Test
    public void testGetName() {
        assertEquals("a",player1.getName());
        assertEquals("b",player2.getName());
    }

    @Test
    public void testGetBalance() {
        assertEquals(100,player1.getBalance());
        assertEquals(1,player2.getBalance());
    }

    @Test
    public void testAddBalance() {
        player1.addBalance(1);
        assertEquals(101,player1.getBalance());
        player2.addBalance(100);
        assertEquals(101,player2.getBalance());
    }

    @Test
    public void testSubBalance() {
        player1.subBalance(50);
        assertEquals(50,player1.getBalance());
        player2.subBalance(1);
        assertEquals(0,player2.getBalance());
    }
}
