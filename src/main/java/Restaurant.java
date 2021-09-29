//import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime CurrentTime = getCurrentTime(); // declaring object to fetch current time (LocalTime.now())
        if ((CurrentTime.isAfter(openingTime) || CurrentTime.equals(openingTime)) && (CurrentTime.isBefore(closingTime) || CurrentTime.equals(closingTime))) { //checks if current time falls between restaurant opening and closing time
            return true;
        }
        else{
            return false;
        }
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {

        return menu;//Returns the menu list
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    // Method Implementation for calculating order value, thus enabling failed test cases to now pass
    public int OrderTotal(List<String> itemsSelected){
        int total = 0;
        for (String itemSelected : itemsSelected){
            total =total + findItemByName(itemSelected).getPrice();
        }
        return total;
    }


    public String getName() {
        return name;
    }

}
