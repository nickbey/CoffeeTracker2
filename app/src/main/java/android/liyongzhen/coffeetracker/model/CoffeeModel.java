package android.liyongzhen.coffeetracker.model;


public class CoffeeModel {

    //private variables
    int _id;
    int _userId;
    String _coffee;

    // Empty constructor
    public CoffeeModel(){

    }
    // constructor
    public CoffeeModel(int id, int user_id, String coffee){
        this._id = id;
        this._userId = user_id;
        this._coffee = coffee;
    }

    // constructor
    public CoffeeModel(int user_id, String coffee){
        this._userId = user_id;
        this._coffee = coffee;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getUserId(){
        return this._userId;
    }

    public void setUserId(int user_id){
        this._userId = user_id;
    }

    public String getCoffee(){
        return this._coffee;
    }

    public void setCoffee(String _coffee){
        this._coffee = _coffee;
    }
}
