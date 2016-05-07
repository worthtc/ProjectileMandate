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

    /**
     * default constructor for score
     * @param score
     * @param user
     */
    public Score(int score, String user) {
        mScore = score;
        mUser = user;
    }

    /**
     * getters used by the class
     * @return
     */
    public int getScore() {
        return mScore;
    }

    public String getUser() {
        return mUser;
    }

    /**
     * setter for setting the user
     * @param user
     */
    public void setUser(String user) {
        mUser = user;
    }
}
