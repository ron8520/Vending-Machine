package W14B_G4_Assignment2.model;
import java.util.HashMap;

public class ShoppingCart{

    HashMap<String, Integer> items = new HashMap<String, Integer>();

    public ShoppingCart(){

    }

    public HashMap<String,Integer> getItems(){
        return this.items;
    }

    public void addItem(String item_name, int num){
        for (String item : this.items.keySet()){
            if (item.equals(item_name)){
                this.items.replace(item, this.items.get(item), this.items.get(item) + num);
                return;
            }
        }
        if (num > 0){
            this.items.put(item_name, num);
        }
    }

    public void removeItem(String item_name){
        this.items.remove(item_name);
    }

    public void cancel(){
        this.items.clear();
    }

    public int getItemNum(String item){
        if (this.items.get(item) == null) return 0;
        return this.items.get(item);
    }

    public int getSize(){
        return this.items.size();
    }
}
