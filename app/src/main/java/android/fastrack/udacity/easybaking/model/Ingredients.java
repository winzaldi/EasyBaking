package android.fastrack.udacity.easybaking.model;

import org.parceler.Parcel;

/**
 * Created by winzaldi on 8/12/17.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Ingredients {
     String measure;

     String ingredient;

     String quantity;

    public Ingredients() {
    }

    public Ingredients(String measure, String ingredient, String quantity) {
        this.measure = measure;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
