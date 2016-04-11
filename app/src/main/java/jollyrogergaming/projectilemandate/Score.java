package jollyrogergaming.projectilemandate;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Score class to hold the data corresponding toe a player name and their score.
 */
public class Score {
    private int mScore;
    private String mUser;

    public Score(int score, String user) {
        mScore = score;
        mUser = user;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }
}
