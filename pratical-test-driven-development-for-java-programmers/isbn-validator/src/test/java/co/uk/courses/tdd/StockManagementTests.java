package co.uk.courses.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


/**
 * Demonstrates the differences between Fakes, Stubs, and Mocks in unit testing
 * using Mockito.
 *
 * <p><b>Test Double Types:</b></p>
 *
 * <ul>
 *   <li><b>Fake</b> – Used when you need a working implementation but don't care
 *       about test assertions. No real test verification happens.
 *       <br>Mockito: {@code mock()}</li>
 *
 *   <li><b>Stub</b> – Used to provide controlled test data. You set up a method
 *       to return a specific value when called.
 *       <br>Mockito: {@code mock()} + {@code when().thenReturn()}</li>
 *
 *   <li><b>Mock</b> – Used to verify behaviour. You assert that a specific method
 *       was called during the test.
 *       <br>Mockito: {@code mock()} + {@code verify().myMethod()}</li>
 * </ul>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * // Fake – just a mock with no configuration or verification
 * PaymentService fake = mock(PaymentService.class);
 *
 * // Stub – returns test data
 * PaymentService stub = mock(PaymentService.class);
 * when(stub.getBalance()).thenReturn(BigDecimal.valueOf(1000));
 *
 * // Mock – verifies behaviour
 * PaymentService mock = mock(PaymentService.class);
 * mock.processPayment(BigDecimal.TEN);
 * verify(mock).processPayment(BigDecimal.TEN);
 * }</pre>
 */
public class StockManagementTests {


    private ExternalISBNDataService databaseService;
    private ExternalISBNDataService webService;
    private StockManager stockManager;

    @BeforeEach
    public void setup(){
        System.out.println("setup running");
        // This line will create a dummy class that is an implementation of this interface
        // that we can call its methods.
        this.databaseService = mock(ExternalISBNDataService.class);
        this.webService = mock(ExternalISBNDataService.class);
        this.stockManager = new StockManager();
        this.stockManager.setWebService(webService);
        this.stockManager.setDatabaseService(databaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {

        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        // The idea here is leave out-of-date with the rest of test methods to demonstrate the stub use without Mocks.
        // Using Stubs - I can't decide what I want to return like mocks.
        // I can't test behavior like mocks.
        // Use Stubs when you are not testing behavior. This is a lightweight approach.
        this.stockManager.setWebService(testWebService);
        this.stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }


    @Test
    public void databaseIsUsedIfDataIsPresent(){

        when(databaseService.lookup("0140177396"))
                .thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = this.stockManager.getLocatorCode(isbn);

        verify(this.databaseService, times(1)).lookup("0140177396");
        verify(this.webService, times(0)).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase(){

        when(databaseService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396"))
                .thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = this.stockManager.getLocatorCode(isbn);

        verify(this.databaseService, times(1)).lookup("0140177396");
        verify(this.webService, times(1)).lookup("0140177396");
    }
}
