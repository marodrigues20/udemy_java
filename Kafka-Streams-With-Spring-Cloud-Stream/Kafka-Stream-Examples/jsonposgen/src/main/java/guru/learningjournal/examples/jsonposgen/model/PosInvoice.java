package guru.learningjournal.examples.jsonposgen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * The @JsonProperty make the class definition a JSON friendly class.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //Ignore Nulls if there is fields with null values.
public class PosInvoice {

    @JsonProperty("InvoiceNumber")
    private String invoiceNumber;
    @JsonProperty("CreatedTime")
    private Long createdTime;
    @JsonProperty("StoreID")
    private String storeID;
    @JsonProperty("PosID")
    private String posID;
    @JsonProperty("CashierID")
    private String cashierID;
    @JsonProperty("CustomerType")
    private String customerType;
    @JsonProperty("CustomerCardNo")
    private String customerCardNo;
    @JsonProperty("TotalAmount")
    private Double totalAmount;
    @JsonProperty("NumberOfItems")
    private Integer numberOfItems;
    @JsonProperty("PaymentMethod")
    private String paymentMethod;
    @JsonProperty("TaxableAmount")
    private Double taxableAmount;
    @JsonProperty("CGST")
    private Double cGST;
    @JsonProperty("SGST")
    private Double SGST;
    @JsonProperty("CESS")
    private Double CESS;
    @JsonProperty("DeliveryType")
    private String deliveryType;
    @JsonProperty("DeliveryAddress")
    private DeliveryAddress deliveryAddress;
    @JsonProperty("InvoiceLineItems")
    private List<LineItem> invoiceLineItems = new ArrayList<LineItem>();


}
