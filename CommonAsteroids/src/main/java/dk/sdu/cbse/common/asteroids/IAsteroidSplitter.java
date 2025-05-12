package dk.sdu.cbse.common.asteroids;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

/** Splits an asteroid into smaller pieces upon collision. */
public interface IAsteroidSplitter {
    /**
     * When an asteroid is hit, create its smaller children.
     *
     * @param e the asteroid being split (not null)
     * @param world where to add the new asteroids (not null)
     */
    void createSplitAsteroid(Entity e, World world);
}
