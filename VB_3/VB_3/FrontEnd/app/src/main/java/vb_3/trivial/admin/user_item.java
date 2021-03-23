package vb_3.trivial.admin;

public class user_item {

    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String games;

    /**
    * Constructor for user_item.
    * */
    public user_item(String id,String username,String email,String password,String role, String games){
        this.id=id;
        this.username=username;
        this.email=email;
        this.password=password;
        this.role=role;
        this.games=games;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }

    public String getGames(){
        return games;
    }
}
