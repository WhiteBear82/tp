package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_LATTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddDrinkCommand;
import seedu.address.model.drink.Drink;

public class AddDrinkCommandParserTest {
    private AddDrinkCommandParser parser = new AddDrinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Drink expectedDrink = new Drink(VALID_NAME_LATTE, VALID_PRICE_LATTE, VALID_CATEGORY_COFFEE);

        // standard case
        assertParseSuccess(parser, NAME_DESC_LATTE + PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE,
                new AddDrinkCommand(expectedDrink));

        // different order of fields
        assertParseSuccess(parser, PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE + NAME_DESC_LATTE,
                new AddDrinkCommand(expectedDrink));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDrinkCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_LATTE + PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_LATTE + VALID_PRICE_LATTE + CATEGORY_DESC_COFFEE,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_LATTE + PRICE_DESC_LATTE + VALID_CATEGORY_COFFEE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_LATTE + VALID_PRICE_LATTE + VALID_CATEGORY_COFFEE,
                expectedMessage);
    }

    @Test
    public void parse_invalidPrice_failure() {
        // Non-numeric price
        assertParseFailure(parser, NAME_DESC_LATTE + " p/abc" + CATEGORY_DESC_COFFEE,
                "Price must be a valid number with at most two decimal places");

        // More than two decimal places
        assertParseFailure(parser, NAME_DESC_LATTE + " p/3.141" + CATEGORY_DESC_COFFEE,
                "Price must be a valid number with at most two decimal places");

        // Negative price
        assertParseFailure(parser, NAME_DESC_LATTE + " p/-5.00" + CATEGORY_DESC_COFFEE,
                "Price must be a positive number");

        // Zero price
        assertParseFailure(parser, NAME_DESC_LATTE + " p/0" + CATEGORY_DESC_COFFEE,
                "Price must be a positive number");
    }


}
