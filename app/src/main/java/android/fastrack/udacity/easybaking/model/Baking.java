package android.fastrack.udacity.easybaking.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by winzaldi on 8/12/17.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Baking {

     String id;

     String servings;

     String name;

     String image;

    ArrayList<Steps> steps;

     ArrayList<Ingredients> ingredients;

    public Baking() {
    }

    public Baking(String id, String servings, String name, String image, ArrayList<Steps> steps, ArrayList<Ingredients> ingredients) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Baking{ \n" +
                "id='" + id + '\'' +
                "\n, servings='" + servings + '\'' +
                "\n, name='" + name + '\'' +
                "\n, image='" + image + '\'' +
                "\n, steps=" + steps.toString() +
                "\n, ingredients=" + ingredients.toString() +
                '}';
    }
}
