package isd.aims.main.controller;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                                                   cartMedia.getQuantity(),
                                                   cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
     * Validates all delivery information
     * Required fields:
     * - phone: valid Vietnamese phone number
     * - name: valid Vietnamese name
     * - address: valid Vietnamese address
     * 
     * @param info HashMap containing delivery information
     * @throws InterruptedException if validation fails
     * @throws IOException if system error occurs
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        // Check for null or empty HashMap
        if (info == null) {
            throw new InterruptedException("Delivery Info cannot be null");
        }
        if (info.isEmpty()) {
            throw new InterruptedException("Delivery Info cannot be empty");
        }

        // Validate phone number
        String phone = info.get("phone");
        if (phone == null) {
            throw new InterruptedException("Phone number is required");
        }
        if (!validatePhoneNumber(phone)) {
            throw new InterruptedException("Invalid phone number format");
        }

        // Validate name
        String name = info.get("name");
        if (name == null) {
            throw new InterruptedException("Name is required");
        }
        if (!validateName(name)) {
            throw new InterruptedException("Invalid name format");
        }

        // Validate address
        String address = info.get("address");
        if (address == null) {
            throw new InterruptedException("Address is required");
        }
        if (!validateAddress(address)) {
            throw new InterruptedException("Invalid address format");
        }

        // Optional fields don't need validation
        LOGGER.info("Delivery info validation successful");
    }

    /**
     * Validates a phone number according to Vietnamese phone number format
     * Valid formats:
     * - Must be exactly 10 digits
     * - Must start with one of the following prefixes: 03, 05, 07, 08, 09
     * - Must contain only digits
     * 
     * @param phoneNumber the phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        // Check for null or empty string
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Check if length is exactly 10
        if (phoneNumber.length() != 10) {
            return false;
        }

        // Check if string contains only digits
        if (!phoneNumber.matches("\\d+")) {
            return false;
        }

        // Check if starts with valid prefix
        String prefix = phoneNumber.substring(0, 2);
        String validPrefixes = "03|05|07|08|09";
        
        return validPrefixes.contains(prefix);
    }

    /**
     * Validates a person's name according to Vietnamese name format
     * Valid formats:
     * - Length between 2 and 50 characters
     * - Contains only letters and single spaces
     * - Cannot start or end with space
     * - No consecutive spaces
     * - Supports Vietnamese characters
     * 
     * @param name the name to validate
     * @return true if the name is valid, false otherwise
     */
    public boolean validateName(String name) {
        // Check for null or empty or blank string
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Check length (2-50 characters)
        if (name.length() < 2 || name.length() > 50) {
            return false;
        }

        // Check for starting/ending spaces
        if (name.startsWith(" ") || name.endsWith(" ")) {
            return false;
        }

        // Check for consecutive spaces
        if (name.contains("  ")) {
            return false;
        }

        // Check if contains only letters and spaces
        // This regex allows Vietnamese characters
        return name.matches("^[\\p{L} ]+$");
    }

    /**
     * Validates an address according to Vietnamese address format
     * Valid formats:
     * - Length between 5 and 200 characters
     * - Contains letters, numbers, spaces, commas, and basic punctuation
     * - No excessive whitespace
     * - Supports Vietnamese characters
     * 
     * @param address the address to validate
     * @return true if the address is valid, false otherwise
     */
    public boolean validateAddress(String address) {
        // Check for null or empty or blank string
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        // Check length (5-200 characters)
        if (address.length() < 5 || address.length() > 200) {
            return false;
        }

        // Check for starting/ending spaces
        if (address.startsWith(" ") || address.endsWith(" ")) {
            return false;
        }

        // Check for excessive whitespace
        if (address.contains("  ")) {
            return false;
        }

        // Check if contains only valid characters
        // This regex allows:
        // - Letters (including Vietnamese)
        // - Numbers
        // - Basic punctuation commonly used in addresses
        return address.matches("^[\\p{L}\\p{N} ,./()-]+$");
    }

    /**
     * Calculates shipping fee for an order
     * Rules:
     * - Base fee: 22,000 VND (inner city)
     * - Additional fee per item: 2,500 VND
     * - Free shipping (up to 25,000 VND discount) for orders over 100,000 VND
     * 
     * @param order the order to calculate shipping fee for
     * @return calculated shipping fee in VND
     */
    public int calculateShippingFee(Order order) {
        // Constants
        final int BASE_FEE = 22000;
        final int PER_ITEM_FEE = 2500;
        final int FREE_SHIPPING_THRESHOLD = 100000;
        final int MAX_DISCOUNT = 25000;

        // Calculate total items in order
        int totalItems = 0;
        for (Object obj : order.getlstOrderMedia()) {
            OrderMedia media = (OrderMedia) obj;
            totalItems += media.getQuantity();
        }

        // Calculate base shipping fee
        int shippingFee = BASE_FEE + (totalItems * PER_ITEM_FEE);

        // Apply free shipping discount if eligible
        if (order.getAmount() >= FREE_SHIPPING_THRESHOLD) {
            shippingFee = Math.max(0, shippingFee - MAX_DISCOUNT);
        }

        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + shippingFee);
        return shippingFee;
    }
}
