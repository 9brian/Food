package com.example.foodflash;

import android.content.Context;
import android.util.Log;

import androidx.room.Query;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.CartDAO;
import com.example.foodflash.DB.DiscountDAO;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;

import java.util.Optional;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //    Used Android Studio documentation to set up DB
//    https://developer.android.com/training/data-storage/room/testing-db
//    This video helped with the intial set up of the db test
//    https://www.youtube.com/watch?v=xGbr9LOSbC0
    private AppDataBase DB;
    private UserDAO userDao;
    private ItemDAO itemDao;
    private CartDAO cartDao;
    private DiscountDAO discountDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        DB = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        userDao = DB.UserDAO();
        itemDao = DB.ItemDAO();
        cartDao = DB.CartDAO();
        discountDao = DB.DiscountDAO();
    }

    @After
    public void closeDb() {
        DB.close();
    }

//    =-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=- User tests

    @Test
    public void insertUser() {
        User user = new User("Test", "crashcourse", 1, false);
        assertNotNull(user);
        userDao.insert(user);
        User findUser = userDao.getUserByName("Test");
        // Before the user is inserted the userid is default to 0
        assertEquals(findUser.getUserId(), user.getUserId() + 1);
        assertEquals(findUser.getUserName(), user.getUserName());
        assertEquals(findUser.getPassWord(), user.getPassWord());
        assertEquals(findUser.getDiscountId(), user.getDiscountId());
        assertEquals(findUser.isAdmin(), user.isAdmin());
    }

    @Test
    public void deleteUser() {
        User user = new User("Test", "crashcourse", 1, false);
        assertNotNull(user);
        userDao.insert(user);

        User findUser = userDao.getUserByName("Test");
        // Before the user is inserted the userid is default to 0
        assertEquals(findUser.getUserId(), user.getUserId() + 1);
        assertEquals(findUser.getUserName(), user.getUserName());
        assertEquals(findUser.getPassWord(), user.getPassWord());
        assertEquals(findUser.getDiscountId(), user.getDiscountId());
        assertEquals(findUser.isAdmin(), user.isAdmin());

        userDao.delete(findUser);

        findUser = userDao.getUserByName("Test");

        assertNull(findUser);
    }

    @Test
    public void updateUser() {
        User user = new User("Test", "crashcourse", 1, false);
        assertNotNull(user);
        userDao.insert(user);

        User findUser = userDao.getUserByName("Test");
        // Before the user is inserted the userid is default to 0
        assertEquals(findUser.getUserId(), user.getUserId()+ 1);
        assertEquals(findUser.getUserName(), user.getUserName());
        assertEquals(findUser.getPassWord(), user.getPassWord());
        assertEquals(findUser.getDiscountId(), user.getDiscountId());
        assertEquals(findUser.isAdmin(), user.isAdmin());

        findUser.setPassWord("password");
        assertEquals("password", findUser.getPassWord());

        userDao.update(findUser);
        findUser = userDao.getUserByName("Test");
        assertEquals("Test", findUser.getUserName());
        assertEquals("password", findUser.getPassWord());
    }

//    =-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=- Item tests

    @Test
    public void insertItem() {
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        assertNotNull(item);
        itemDao.insert(item);

        Item findItem = itemDao.getItemByName("Ramen");

        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(findItem.getItemId(), item.getItemId() + 1);
        assertEquals(findItem.getItemName(), item.getItemName());
        assertEquals(findItem.getItemDescription(), item.getItemDescription());
        assertEquals(findItem.getItemPrice(), item.getItemPrice());
    }

    @Test
    public void deleteItem() {
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        assertNotNull(item);
        itemDao.insert(item);

        Item findItem = itemDao.getItemByName("Ramen");

        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(findItem.getItemId(), item.getItemId() + 1);
        assertEquals(findItem.getItemName(), item.getItemName());
        assertEquals(findItem.getItemDescription(), item.getItemDescription());
        assertEquals(findItem.getItemPrice(), item.getItemPrice());

        itemDao.delete(findItem);

        findItem = itemDao.getItemByName("Ramen");

        assertNull(findItem);
    }

    @Test
    public void updateItem() {
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        assertNotNull(item);
        itemDao.insert(item);

        Item findItem = itemDao.getItemByName("Ramen");

        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(findItem.getItemId(), item.getItemId() + 1);
        assertEquals(findItem.getItemName(), item.getItemName());
        assertEquals(findItem.getItemDescription(), item.getItemDescription());
        assertEquals(findItem.getItemPrice(), item.getItemPrice());

        findItem.setItemDescription("Not Tasty");
        assertEquals("Not Tasty", findItem.getItemDescription());

        itemDao.update(findItem);
        findItem = itemDao.getItemByName("Ramen");
        assertEquals("Ramen", findItem.getItemName());
        assertEquals("Not Tasty", findItem.getItemDescription());
        Double price = 12.00;
        assertEquals(price, findItem.getItemPrice());
    }

//    =-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=- Cart tests

    @Test
    public void insertCart() {
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        User user = new User("Test", "crashcourse", 1, false);

        itemDao.insert(item);
        userDao.insert(user);

        User findUser = userDao.getUserByName("Test");
        Item findItem = itemDao.getItemByName("Ramen");

        int userId = findUser.getUserId();
        int itemId = findItem.getItemId();

        Cart cart = new Cart(itemId, userId);

        cartDao.insert(cart);

        Cart findCart = cartDao.getCartByUserMenu(itemId, userId);
        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(cart.getCartId() + 1, findCart.getCartId());
        assertEquals(userId, findCart.getUserId());
        assertEquals(itemId, findCart.getMenuId());
    }

    @Test
    public void deleteCart() {
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        User user = new User("Test", "crashcourse", 1, false);

        itemDao.insert(item);
        userDao.insert(user);

        User findUser = userDao.getUserByName("Test");
        Item findItem = itemDao.getItemByName("Ramen");

        int userId = findUser.getUserId();
        int itemId = findItem.getItemId();

        Cart cart = new Cart(itemId, userId);

        cartDao.insert(cart);

        Cart findCart = cartDao.getCartByUserMenu(itemId, userId);
        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(cart.getCartId() + 1, findCart.getCartId());
        assertEquals(userId, findCart.getUserId());
        assertEquals(itemId, findCart.getMenuId());

        cartDao.delete(findCart);

        findCart = cartDao.getCartByUserMenu(itemId, userId);

        assertNull(findCart);
    }

    @Test
    public void updateCart() {
        // New user and item
        Item item = new Item("Ramen", "Tasty Ramen", 12.00);
        User user = new User("Test", "crashcourse", 1, false);

        // Insert items to db
        itemDao.insert(item);
        userDao.insert(user);

        // Find items in db
        User findUser = userDao.getUserByName("Test");
        Item findItem = itemDao.getItemByName("Ramen");

        // find id after db insertion
        int userId = findUser.getUserId();
        int itemId = findItem.getItemId();

        // Create new cart
        Cart cart = new Cart(itemId, userId);

        // insert cart to db
        cartDao.insert(cart);

        // find cart in db
        Cart findCart = cartDao.getCartByUserMenu(itemId, userId);

        // assertions
        // The reason it needs to be incremented is because, before it is inserted the itemId is default to 0
        assertEquals(cart.getCartId() + 1, findCart.getCartId());
        assertEquals(userId, findCart.getUserId());
        assertEquals(itemId, findCart.getMenuId());

        // New item in menu
        Item newItem = new Item("Oreos", "So delicious, but so bad for you", 12.00);

        // Insert new item
        itemDao.insert(newItem);
        // find new item in db
        Item foundNewItem = itemDao.getItemByName("Oreos");
        int newItemId = foundNewItem.getItemId();

        // user has changed their item choice
        findCart.setMenuId(newItemId);

        // update cart to reflect changes
        cartDao.update(findCart);

        // find cart
        findCart = cartDao.getCartByUserMenu(newItemId, userId);
        assertEquals(cart.getCartId() + 1, findCart.getCartId());
        assertEquals(userId, findCart.getUserId());
        assertEquals(newItemId, findCart.getMenuId());
    }

//    =-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=- Discount tests

    @Test
    public void insertDiscount() {
        Discount discount = new Discount("code", 0.5);

        discountDao.insert(discount);

        Discount foundDiscount = discountDao.getDiscountByCode("code");

        assertEquals(discount.getDiscountId() + 1, foundDiscount.getDiscountId());
        assertEquals(discount.getDiscountCode(), foundDiscount.getDiscountCode());
//        assertEquals(discount.getDiscountPercentage(), foundDiscount.getDiscountPercentage());
    }

    @Test
    public void deleteDiscount() {
        Discount discount = new Discount("code", 0.5);

        discountDao.insert(discount);

        Discount foundDiscount = discountDao.getDiscountByCode("code");

        assertEquals(discount.getDiscountId() + 1, foundDiscount.getDiscountId());
        assertEquals(discount.getDiscountCode(), foundDiscount.getDiscountCode());
//        assertEquals(discount.getDiscountPercentage(), foundDiscount.getDiscountPercentage());

        discountDao.delete(foundDiscount);

        foundDiscount = discountDao.getDiscountByCode("code");
        assertNull(foundDiscount);
    }

    @Test
    public void updateDiscount() {
        Discount discount = new Discount("code", 0.5);

        discountDao.insert(discount);

        Discount foundDiscount = discountDao.getDiscountByCode("code");

        assertEquals(discount.getDiscountId() + 1, foundDiscount.getDiscountId());
        assertEquals(discount.getDiscountCode(), foundDiscount.getDiscountCode());
//        assertEquals(discount.getDiscountPercentage(), foundDiscount.getDiscountPercentage());

        foundDiscount.setDiscountCode("New Code");
        assertEquals("New Code", foundDiscount.getDiscountCode());

        discountDao.update(foundDiscount);
        foundDiscount = discountDao.getDiscountByCode("New Code");
        assertEquals("New Code", foundDiscount.getDiscountCode());
        assertEquals(discount.getDiscountId() + 1, foundDiscount.getDiscountId());
    }

}