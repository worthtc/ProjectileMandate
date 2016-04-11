package jollyrogergaming.projectilemandate.database;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Class to hold the information corresponding the Schema of Projectile Mandate
 */
public class ScoreDbSchema {
    public static final class ScoreTable{
        public static final String NAME = "scores";

        public static final class Cols{
            public static final String USER = "user";
            public static final String SCORE = "score";
        }
    }
}
