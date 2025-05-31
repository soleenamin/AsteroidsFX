package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.*;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class PlayerControlSystemTest {

    private IEntityProcessingService playerControlSystem;
    private GameData gameData;
    private GameKeys gameKeys;
    private World world;
    private Player player;

    @BeforeEach
    public void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = mock(GameData.class);
        world = mock(World.class);
        player = mock(Player.class);
        gameKeys = mock(GameKeys.class);

        when(gameData.getKeys()).thenReturn(gameKeys);
        when(world.getEntities(Player.class)).thenReturn(List.of(player));
    }

    @Test
    public void testPlayerMovesUp() {
        // Setup
        when(gameKeys.isDown(GameKeys.UP)).thenReturn(true);
        when(player.getRotation()).thenReturn(0.0); // Facing right
        when(player.getX()).thenReturn(0.0);
        when(player.getY()).thenReturn(0.0);

        // Execute
        playerControlSystem.process(gameData, world);

        // Assert
        verify(player, times(1)).setX(anyDouble());
        verify(player, times(1)).setY(anyDouble());
    }

    @Test
    public void testPlayerTurnsLeft() {
        when(gameKeys.isDown(GameKeys.LEFT)).thenReturn(true);
        when(player.getRotation()).thenReturn(90.0); // Arbitrary initial rotation

        playerControlSystem.process(gameData, world);

        verify(player, times(1)).setRotation(anyDouble());
    }

    @Test
    public void testPlayerTurnsRight() {
        when(gameKeys.isDown(GameKeys.RIGHT)).thenReturn(true);
        when(player.getRotation()).thenReturn(90.0); // Arbitrary initial rotation

        playerControlSystem.process(gameData, world);

        verify(player, times(1)).setRotation(anyDouble());
    }
}
