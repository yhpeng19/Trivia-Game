package vb_3.trivial.player;

public class leader_item {
    private String rank;
    private String usename;
    private String score;

    public leader_item(String rank, String username, String score){
        this.rank=rank;
        this.usename=username;
        this.score=score;
    }

    public String getRank(){
        return rank;
    }
    public String getUsername(){ return usename; }
    public String getScore(){ return score; }
}
