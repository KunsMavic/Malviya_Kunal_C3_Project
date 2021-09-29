import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class RestaurantTest {
    Restaurant restaurant;
    Restaurant spyObjectForRestaurant;

    @BeforeEach //REFACTORED REPEATED LINES OF CODE
    public void openingTimeArrange() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new RestaurantService().addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        spyObjectForRestaurant = Mockito.spy(restaurant);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {

        LocalTime timeInRange = LocalTime.parse("12:00:00"); // time parameter within opening and closing hours
        LocalTime edgecase1 = LocalTime.parse("10:30:00"); // checks edgecase (just at opening time)
        LocalTime edgecase2 = LocalTime.parse("22:00:00"); // checks edgecase (just at closing time)


        Mockito.when(spyObjectForRestaurant.getCurrentTime()).thenReturn(timeInRange,edgecase1,edgecase2); // mocking current time to the time parameters declared above


        assertTrue(spyObjectForRestaurant.isRestaurantOpen()); // assertion to test that time is between opening and closing hours
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime timeOutOfRange = LocalTime.parse("08:30:45"); // time parameter outside range of opening and closing opening hours

        Mockito.when(spyObjectForRestaurant.getCurrentTime()).thenReturn(timeOutOfRange); // mocking current time to the time parameter declared above


        assertFalse(spyObjectForRestaurant.isRestaurantOpen()); // assertion to test that time is outside range of opening and closing hours
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER-TOTAL>>>>>>>>>>>>>>>>>>>>>>

    @Test //FAILING TEST CASE WILL NOW PASS SINCE REQUIRED METHOD IS IMPLEMENTED
    public void items_selected_should_return_total_value(){
        List<String> itemsSelected = new ArrayList<String>();
        itemsSelected.add("Sweet corn soup");
        itemsSelected.add("Vegetable lasagne");
        assertEquals(119+269,restaurant.OrderTotal(itemsSelected));
    }
    @Test //FAILING TEST CASE WILL NOW PASS SINCE REQUIRED METHOD IS IMPLEMENTED
    public void items_selected_should_return_total_value_of_selected_items_only_after_removing_one_or_more_items(){
        List<String> itemsSelected = new ArrayList<String>();
        itemsSelected.add("Vegetable lasagne");
        assertEquals(269,restaurant.OrderTotal(itemsSelected));
    }
    @Test //FAILING TEST CASE WILL NOW PASS SINCE REQUIRED METHOD IS IMPLEMENTED
    public void if_nothing_is_selected_it_should_return_total_as_zero(){
        List<String> itemsSelected = new ArrayList<String>();
        assertEquals(0,restaurant.OrderTotal(itemsSelected));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER-TOTAL>>>>>>>>>>>>>>>>>>>>>>


}