package vb_3.trivial;


/**
 *  Class Global_Varable  for saving all the global variables.
 * */
public class Global_Varable {
    /**
     * token(a random String) sent from server and saved here
     * */
    public static String token;

    /**
     * 0: failed(needed to login) 1:player 2:admin 3:spectator
     * */
    public static int usertype = 0;

    public static int Approving_index;

    public static int Approving_index1;

    /**
     * lobby index saved.
     */
    public static int lobby_index;

    public static int request_index;

    /**
     * username saved for current player.
     */
    public static String username;

    public static String username_add;

    //public static int state = 0;


    /**
     * Game ID for a multi-player game.
     */
    public static String Game_ID;
}
