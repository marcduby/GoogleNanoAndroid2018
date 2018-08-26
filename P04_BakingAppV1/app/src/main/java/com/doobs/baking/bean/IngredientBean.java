package com.doobs.baking.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Bean class to hold the ingredient for a recipe
 *
 * Created by mduby on 8/24/18.
 */

public class IngredientBean implements Parcelable {
    // instance variable
    private String measurement;
    private String name;
    private Double amount;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * parcels out the objects
     *
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(measurement);
        parcel.writeString(name);
        parcel.writeDouble(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<IngredientBean> CREATOR = new Parcelable.Creator<IngredientBean>() {
        public IngredientBean createFromParcel(Parcel parcel) {
            IngredientBean ingredientBean = new IngredientBean();

            // set the data
            ingredientBean.setMeasurement(parcel.readString());
            ingredientBean.setName(parcel.readString());
            ingredientBean.setAmount(parcel.readDouble());

            // return
            return ingredientBean;
        }

        public IngredientBean[] newArray(int size) {
            return new IngredientBean[size];
        }
    };

}
