package W14B_G4_Assignment2.model;

public abstract class User{

    public String username;
    public String password;
    private ShoppingCart cart = new ShoppingCart();


    public String getName(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public ShoppingCart getCart(){
        return this.cart;
    }
}
