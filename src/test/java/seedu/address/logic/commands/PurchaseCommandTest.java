package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.DrinkCatalog;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

public class PurchaseCommandTest {

    private static final String VALID_DRINK_NAME = "Matcha";
    private static final double VALID_DRINK_PRICE = 4.50;
    private static final String INVALID_DRINK_NAME = "Nonexistent Drink";

    private Model model;
    private DrinkCatalog drinkCatalog;

    @BeforeEach
    public void setUp() {
        // Create a drink catalog with the test drink
        drinkCatalog = new DrinkCatalog();
        drinkCatalog.addDrink(new Drink(VALID_DRINK_NAME, VALID_DRINK_PRICE, "Tea"));

        // Create an address book with at least one customer
        AddressBook addressBook = new AddressBook();
        Customer testCustomer = new Customer(
            new Name("Test Customer"),
            new Phone("12345678"),
            new Email("test@example.com"),
            new Address("Test Address"),
            new Remark(""),
            new HashSet<>(),
            "C001",
            100, // Initial reward points
            5, // Initial visit count
            "Coffee", // Initial favorite item
            50.0 // Initial total spent
        );
        addressBook.addCustomer(testCustomer);

        // Create the model with our address book and drink catalog
        model = new ModelManager(addressBook, new UserPrefs(), drinkCatalog);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        PurchaseCommand purchaseCommand = new PurchaseCommand(outOfBoundIndex, VALID_DRINK_NAME);

        assertCommandFailure(purchaseCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDrinkName_throwsCommandException() {
        PurchaseCommand purchaseCommand = new PurchaseCommand(INDEX_FIRST_PERSON, INVALID_DRINK_NAME);

        // Make sure this matches EXACTLY what your command returns
        String expectedMessage = String.format(PurchaseCommand.MESSAGE_DRINK_NOT_FOUND, INVALID_DRINK_NAME);
        assertCommandFailure(purchaseCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        PurchaseCommand purchaseFirstCommand = new PurchaseCommand(INDEX_FIRST_PERSON, VALID_DRINK_NAME);
        PurchaseCommand purchaseSecondCommand = new PurchaseCommand(INDEX_SECOND_PERSON, VALID_DRINK_NAME);
        PurchaseCommand purchaseDifferentDrinkCommand = new PurchaseCommand(INDEX_FIRST_PERSON, "Green Tea");

        // same object -> returns true
        assertTrue(purchaseFirstCommand.equals(purchaseFirstCommand));

        // same values -> returns true
        PurchaseCommand purchaseFirstCommandCopy = new PurchaseCommand(INDEX_FIRST_PERSON, VALID_DRINK_NAME);
        assertTrue(purchaseFirstCommand.equals(purchaseFirstCommandCopy));

        // different index -> returns false
        assertFalse(purchaseFirstCommand.equals(purchaseSecondCommand));

        // different drink -> returns false
        assertFalse(purchaseFirstCommand.equals(purchaseDifferentDrinkCommand));

        // different types -> returns false
        assertFalse(purchaseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(purchaseFirstCommand.equals(null));
    }
}
