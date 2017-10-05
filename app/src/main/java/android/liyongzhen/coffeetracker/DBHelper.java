package android.liyongzhen.coffeetracker;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase;
        import android.liyongzhen.coffeetracker.model.CoffeeModel;
        import android.liyongzhen.coffeetracker.model.UserModel;

        import java.util.ArrayList;
        import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_USER_TABLE_NAME = "users";
    public static final String KEY_ID = "id";
    public static final String CONTACTS_USER_NAME = "name";
    public static final String CONTACTS_USER_EMAIL = "email";

    public static final String CONTACTS_COFFEE_TABLE_NAME = "coffees";
    public static final String CONTACTS_USER_ID = "user_id";
    public static final String CONTACTS_TIMESTAP = "time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_user = "CREATE TABLE " + CONTACTS_USER_TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + CONTACTS_USER_NAME + " TEXT,"
                + CONTACTS_USER_EMAIL + " TEXT" + ")";
        db.execSQL(create_table_user);
        String create_table_coffee = "CREATE TABLE " + CONTACTS_COFFEE_TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + CONTACTS_USER_ID + " INTEGER,"
                + CONTACTS_TIMESTAP + " TEXT" + ")";
        db.execSQL(create_table_coffee);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_COFFEE_TABLE_NAME);
        onCreate(db);
    }

    public void insertCoffee(CoffeeModel coffee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_USER_ID, coffee.getUserId());
        contentValues.put(CONTACTS_TIMESTAP, coffee.getCoffee());
        db.insert(CONTACTS_COFFEE_TABLE_NAME, "", contentValues);
    }

    public void removeCoffees(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_COFFEE_TABLE_NAME, CONTACTS_USER_ID + "=" + userId, null);
    }

    public List<CoffeeModel> getCoffees(int userId){
        List<CoffeeModel> coffees = new ArrayList<CoffeeModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+CONTACTS_COFFEE_TABLE_NAME+" WHERE "+CONTACTS_USER_ID+" = '"+userId+"'", null);
        if(c.moveToFirst()){
            do{
                //assing values
                CoffeeModel coffeeModel = new CoffeeModel();
                coffeeModel.setUserId(c.getInt(1));
                coffeeModel.setCoffee(c.getString(2));
                coffees.add(coffeeModel);
            }while(c.moveToNext());
        }
        c.close();
        return coffees;
    }

    public int insertUser (UserModel userModel) {
        int id;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_USER_NAME, userModel.getName());
        contentValues.put(CONTACTS_USER_EMAIL, userModel.getEmail());
        id = (int)db.insert(CONTACTS_USER_TABLE_NAME, "", contentValues);
        return id;
    }

    public UserModel getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+ CONTACTS_USER_TABLE_NAME +" where id="+id+"", null );
        if (cursor != null)
            cursor.moveToFirst();

        UserModel userModel = new UserModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return userModel;
    }

    public int numOfUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_USER_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_USER_NAME, name);
        contentValues.put(CONTACTS_USER_EMAIL, email);
        db.update(CONTACTS_USER_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

//    public Integer deleteUser (Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(CONTACTS_USER_TABLE_NAME,
//                "id = ? ",
//                new String[] { Integer.toString(id) });
//    }
}