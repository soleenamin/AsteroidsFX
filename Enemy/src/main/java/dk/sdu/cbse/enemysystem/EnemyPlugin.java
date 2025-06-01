package dk.sdu.cbse.enemysystem;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

/**
 * Spawns one enemy ship at a random screen edge when the game starts.
 */
public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Enemy e = new Enemy();
        // simple triangle, pointing to the right by default
        e.setPolygonCoordinates(-10,-8,  10,0,  -10,8);
        double w = gameData.getDisplayWidth(), h = gameData.getDisplayHeight();
        switch ((int)(Math.random()*4)) {
            case 0: e.setX(Math.random()*w); e.setY(0);       break; // top
            case 1: e.setX(Math.random()*w); e.setY(h);       break; // bottom
            case 2: e.setX(0);                e.setY(Math.random()*h); break; // left
            default:e.setX(w);                e.setY(Math.random()*h);     // right
        }
        e.setRadius(12);
        e.setType("enemy");
        return e;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
