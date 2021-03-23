package vb_3.trivial.admin;

public class item {

    private String date;
    private String category;
    private String ID;

    public item(String date,String category,String ID){
        this.date = date;
        this.category = category;
        this.ID = ID;
    }

    public String getDate(){
        return date;
    }

    public String getCategory(){
        return category;
    }

    public String getID(){
        return ID;
    }
}
