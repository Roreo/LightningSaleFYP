package com.example.rory.lightningsalefyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Rory on 17/02/2015.
 */
public class PrefMenu extends Activity {

    private CheckBox mens;
    private CheckBox women;
    private CheckBox child;
    private CheckBox foot;
    private CheckBox health;
    private CheckBox jewellery;
    private CheckBox elec;
    private CheckBox food;
    private CheckBox services;
    private CheckBox sports;
    private CheckBox books;
    private CheckBox toys;
    private CheckBox market;

    private String data;
    private String file = "mydata";

    private String mensApparel, womensApparel, childApparel, footwear, healthAndBeauty, jewelleryAcc, electronics, foodDrink, servicesStr, sportsWear, booksNews, toysHobbies, supermarket;

    //getting preferences from a specified file
    public SharedPreferences settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1_layout);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        mens = (CheckBox) findViewById(R.id.chkMens);
        women = (CheckBox) findViewById(R.id.chkWomens);
        child = (CheckBox) findViewById(R.id.chkChildrens);
        foot = (CheckBox) findViewById(R.id.chkFootwear);
        health = (CheckBox) findViewById(R.id.chkHealth);
        jewellery = (CheckBox) findViewById(R.id.chkJewellery);
        elec = (CheckBox) findViewById(R.id.chkElec);
        food = (CheckBox) findViewById(R.id.chkFood);
        services = (CheckBox) findViewById(R.id.chkServices);
        sports = (CheckBox) findViewById(R.id.chkSport);
        books = (CheckBox) findViewById(R.id.chkBooks);
        toys = (CheckBox) findViewById(R.id.chkToys);
        market = (CheckBox) findViewById(R.id.chkSuper);

        settings = getSharedPreferences("Test", Context.MODE_PRIVATE);

    }

    public void savePrefs(View view){

        if (mens.isChecked()){
            mensApparel = "mens";
        }
        else{
            mensApparel = "";
        }

        if(women.isChecked()){
            womensApparel = "woman";
        }
        else{
            womensApparel = "";
        }

        if(child.isChecked()){
            childApparel = "childrens";
        }
        else{
            childApparel = "";
        }

        if(foot.isChecked()){
            footwear = "footwear";
        }
        else{
            footwear = "";
        }

        if(health.isChecked()){
            healthAndBeauty = "health";
        }
        else{
            healthAndBeauty = "";
        }

        if(jewellery.isChecked()){
            jewelleryAcc = "jewellery";
        }
        else{
            jewelleryAcc = "";
        }

        if(elec.isChecked()){
            electronics = "elec";
        }
        else{
            electronics = "";
        }

        if(food.isChecked()){
            foodDrink = "drink";
        }
        else{
            foodDrink = "";
        }

        if(services.isChecked()){
            servicesStr = "services";
        }
        else{
            servicesStr = "";
        }

        if(sports.isChecked()){
            sportsWear = "sports";
        }
        else{
            sportsWear = "";
        }

        if(books.isChecked()){
            booksNews = "books";
        }
        else{
            booksNews = "";
        }

        if(toys.isChecked()){
            toysHobbies = "toys";
        }
        else{
            toysHobbies = "";
        }

        if(market.isChecked()){
            supermarket = "market";
        }
        else{
            supermarket = "";
        }

        SharedPreferences.Editor edit = settings.edit();
        edit.putString("dataM", mensApparel);
        edit.putString("dataW", womensApparel);
        edit.putString("dataC", childApparel);
        edit.putString("dataFoot", footwear);
        edit.putString("dataH", healthAndBeauty);
        edit.putString("dataJ", jewelleryAcc);
        edit.putString("dataE", electronics);
        edit.putString("dataFood", foodDrink);
        edit.putString("dataSer", servicesStr);
        edit.putString("dataSpo", sportsWear);
        edit.putString("dataB", booksNews);
        edit.putString("dataT", toysHobbies);
        edit.putString("dataSup ", supermarket);
        edit.apply();

        Intent nextScreen = new Intent(this, MainActivity.class);
        this.startActivity(nextScreen);
    }


}
