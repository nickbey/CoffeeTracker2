package android.liyongzhen.coffeetracker;

import android.liyongzhen.coffeetracker.model.CoffeeModel;
import android.liyongzhen.coffeetracker.model.UserModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG_LIYONGZHEN = "*****";

    private DBHelper mydb ;

    private int selectedUserNumber;

    //-- Appbar Controllers
    private AppCompatButton profileButton, coffeeButton;
    private AppCompatSpinner userSpinner;
    private RelativeLayout profileLayout, coffeeLayout;
    //-- Profile Controllers
    private TextView userIdTextView;
    private EditText nameEditText, emailEditText;
    private AppCompatButton profileSaveButton;
    //-- Coffee Controllers
    private AppCompatButton coffeeSaveButton, coffeeRemoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initControllers();
    }

    private void initDatabase(){

        mydb = new DBHelper(this);

        int n = mydb.numOfUsers();
        if(n == 0) {
            Log.d(TAG_LIYONGZHEN, "EMPUTY DATA");
            for (int i = 0; i < 11; i++){
                UserModel user = new UserModel("", "");
                int id = mydb.insertUser(user);
                user.setID(id);
            }
        }
        selectedUserNumber = 0;
    }

    private void initControllers(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //-- AppBar Controllers
        profileButton = (AppCompatButton) findViewById(R.id.profileButton);
        coffeeButton = (AppCompatButton) findViewById(R.id.coffeesButton);
        userSpinner = (AppCompatSpinner) findViewById(R.id.spinner);

        profileButton.setOnClickListener(this);
        coffeeButton.setOnClickListener(this);
//        userSpinner.setOnClickListener(this);
        userSpinner.setOnItemSelectedListener(this);

        //-- Profile Layout
        profileLayout = (RelativeLayout) findViewById(R.id.profileLayout);
        userIdTextView = (TextView) findViewById(R.id.idTextView);
        nameEditText = (EditText) findViewById(R.id.nameEditview);
        emailEditText = (EditText) findViewById(R.id.emailEditview);
        profileSaveButton = (AppCompatButton) findViewById(R.id.profileSaveButton);
        profileSaveButton.setOnClickListener(this);
        //-- Coffee Layout
        coffeeLayout = (RelativeLayout) findViewById(R.id.coffeeLayout);
        coffeeSaveButton = (AppCompatButton) findViewById(R.id.coffeeSaveButton);
        coffeeSaveButton.setOnClickListener(this);
        coffeeRemoveButton = (AppCompatButton) findViewById(R.id.coffeeRemoveButton);
        coffeeRemoveButton.setOnClickListener(this);

    }

    private void switchPage(int index){
        if(index == 0){ // profile page
            coffeeLayout.setVisibility(View.INVISIBLE);
            profileLayout.setVisibility(View.VISIBLE);
        }
        else if(index == 1){ // coffee page
            coffeeLayout.setVisibility(View.VISIBLE);
            profileLayout.setVisibility(View.INVISIBLE);
        }
        else // first page
        {
            coffeeLayout.setVisibility(View.INVISIBLE);
            profileLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void setupPage(){
        UserModel user = mydb.getUser(selectedUserNumber);
        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        userIdTextView.setText("User"+selectedUserNumber);
    }

    private void updateCoffeeView(){
        List<CoffeeModel> coffees = mydb.getCoffees(selectedUserNumber);
        LinearLayout layout = (LinearLayout) findViewById(R.id.coffeeListLayout);
        layout.removeAllViews();
        for(CoffeeModel coffee: coffees){
            TextView tv = new TextView(this);
            tv.setText(coffee.getCoffee());
            layout.addView(tv);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.equals(profileButton)){
            if(selectedUserNumber != 0)
                switchPage(0);
        }
        else if(view.equals(coffeeButton)){
            if(selectedUserNumber != 0)
                switchPage(1);
        }
        else if(view.equals(profileSaveButton)){
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            mydb.updateContact(selectedUserNumber, name, email);
        }
        else if(view.equals(coffeeSaveButton)){
            Date date = new Date();
            String stringDate = DateFormat.getDateTimeInstance().format(date);
            UserModel userModel = mydb.getUser(selectedUserNumber);
            mydb.insertCoffee(new CoffeeModel(userModel.getID(), stringDate));
            updateCoffeeView();
        }
        else if(view.equals(coffeeRemoveButton)){
            mydb.removeCoffees(selectedUserNumber);
            updateCoffeeView();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG_LIYONGZHEN, " - "+i);
        selectedUserNumber = i;
        if(selectedUserNumber != 0){
            switchPage(0);
            setupPage();
            updateCoffeeView();
        }
        else
            switchPage(2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
