package com.mgs.Pages.RestPage.POJO;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class OrderDetails {
    private String orderId;
    private String customerName;
    private String orderDate;
    private String deliveryDate;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
    private ArrayList<Item> items;
    private PaymentDetails paymentDetails;
    private OrderHistory orderHistory;
    private CustomerFeedback customerFeedback;

    @Getter
    @Setter
    public static class CustomerFeedback{
        private boolean feedbackSubmitted;
        private String expectedFeedbackDate;
        private Object comments;
    }

    @Getter
    @Setter
    public static class OrderHistory{
        private OrderPlaced orderPlaced;
        private OrderConfirmed orderConfirmed;
        private OrderDispatched orderDispatched;

        @Getter
        @Setter
        public static class OrderPlaced{
            private String date;
            private String updatedBy;
        }

        @Getter
        @Setter
        public static class OrderConfirmed{
            private String date;
            private String updatedBy;
        }

        @Getter
        @Setter
        public static class OrderDispatched{
            private String date;
            private String updatedBy;
        }
    }

    @Getter
    @Setter
    public static class PaymentDetails{
        private String paymentMethod;
        private String cardLastFourDigits;
        private String transactionId;
        private String paymentDate;
        private String paymentStatus;
    }

    @Getter
    @Setter
   public static class ShippingAddress {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

    @Getter
    @Setter
    public static class BillingAddress{
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

    @Getter
    @Setter
    public static class Item{
        private String productId;
        private String productName;
        private int quantity;
        private int pricePerUnit;
        private int totalPrice;
        private WarehouseLocation warehouseLocation;
        private ShipmentStatus shipmentStatus;

        @Getter
        @Setter
       public static class WarehouseLocation {
            public String warehouseId;
            public String city;
            public String state;
       }

        @Getter
        @Setter
       public static class ShipmentStatus{
           public String status;
           public String dispatchedDate;
           public String expectedDelivery;
           public String trackingId;
           public String packedDate;
       }
    }
}
