package co.uk.courses.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationIsbnTest {



    @Test
    public void checkAValid13DigitISBN(){
        ValidateIsbn validateIsbn = new ValidateIsbn();
        boolean result = validateIsbn.checkISBN("9781853260087");
        assertTrue(result, "first value");
         result = validateIsbn.checkISBN("9781853267338");
        assertTrue(result, "second Value");
    }

    @Test
    public void TenDigitISBNNumbersEndingInAnXAreValid(){
        ValidateIsbn validateIsbn = new ValidateIsbn();
        boolean result = validateIsbn.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test
    public void checkAnInvalid10DigitsISBN() {
        ValidateIsbn validateIsbn = new ValidateIsbn();
        boolean result = validateIsbn.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitsISBN() {
        ValidateIsbn validateIsbn = new ValidateIsbn();
        boolean result = validateIsbn.checkISBN("9781853267336");
        assertFalse(result);
    }


    @Test
    public void nineDigitsISBNAreNotAllowed() {
        ValidateIsbn validateIsbn = new ValidateIsbn();
        NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
            validateIsbn.checkISBN("123456789");
        });
    }

    @Test
    public void nonNumericISBNsAreNotAllowed(){
        ValidateIsbn validateIsbn = new ValidateIsbn();
        NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
            validateIsbn.checkISBN("HelloWorld");
        });
    }


}
