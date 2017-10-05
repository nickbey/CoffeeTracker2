package android.liyongzhen.coffeetracker.model;


public class UserModel {

    //private variables
    int _id;
    String _name;
    String _email;

    // Empty constructor
    public UserModel(){
    }
    // constructor
    public UserModel(int id, String name, String _email){
        this._id = id;
        this._name = name;
        this._email = _email;
    }

    // constructor
    public UserModel(String name, String _email){
        this._name = name;
        this._email = _email;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getEmail(){
        return this._email;
    }

    public void setEmail(String _email){
        this._email = _email;
    }
}