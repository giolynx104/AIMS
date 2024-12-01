package isd.aims.main.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.io.IOException;
import java.sql.SQLException;

import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.entity.media.Media;

/**
 * Test class for PlaceOrderController using various testing techniques
 * 
 * Testing Strategy for validatePhoneNumber():
 * 
 * 1. Testing Approach: Black Box Testing
 *    - We test the method based on its specifications without knowing the implementation
 * 
 * 2. Testing Techniques Used:
 * 
 *    a) Equivalence Partitioning:
 *       Valid Classes:
 *       - Phone numbers starting with 03, 05, 07, 08, 09
 *       - Exactly 10 digits
 *       - Contains only numeric characters
 *       
 *       Invalid Classes:
 *       - Null or empty input
 *       - Numbers with length != 10
 *       - Numbers with invalid prefixes
 *       - Input containing non-numeric characters
 * 
 *    b) Boundary Value Analysis:
 *       - Testing phone numbers with 9 digits (just below valid length)
 *       - Testing phone numbers with 11 digits (just above valid length)
 *       - Testing with empty string (minimum length)
 * 
 *    c) Decision Table Testing:
 *       Conditions:
 *       - Is input null/empty?
 *       - Is length 10?
 *       - Contains only digits?
 *       - Has valid prefix?
 *       
 *       Rules covered in test cases:
 *       R1: null input → false
 *       R2: empty string → false
 *       R3: length != 10 → false
 *       R4: non-digits → false
 *       R5: invalid prefix → false
 *       R6: all conditions met → true
 */

/**
 * Testing Strategy for validateName():
 * 
 * 1. Testing Approach: Black Box Testing
 *    - Testing name validation based on specifications
 * 
 * 2. Testing Techniques Used:
 * 
 *    a) Equivalence Partitioning:
 *       Valid Classes:
 *       - Names with 2-50 characters
 *       - Names with letters and single spaces
 *       - Names with Vietnamese characters
 *       
 *       Invalid Classes:
 *       - Null or empty input
 *       - Names with special characters or numbers
 *       - Names with consecutive spaces
 *       - Names starting/ending with spaces
 * 
 *    b) Boundary Value Analysis:
 *       - 1 character (just below min)
 *       - 2 characters (min valid length)
 *       - 50 characters (max valid length)
 *       - 51 characters (just above max)
 * 
 *    c) Decision Table Testing:
 *       Conditions:
 *       - Is input null/empty?
 *       - Is length within bounds?
 *       - Contains only valid characters?
 *       - Has valid spacing?
 *       
 *       Rules covered in test cases:
 *       R1: null/empty input → false
 *       R2: invalid length → false
 *       R3: invalid characters → false
 *       R4: invalid spacing → false
 *       R5: all conditions met → true
 */

/**
 * Testing Strategy for validateAddress():
 * 
 * 1. Testing Approach: Black Box Testing
 *    - Testing address validation based on specifications
 * 
 * 2. Testing Techniques Used:
 * 
 *    a) Equivalence Partitioning:
 *       Valid Classes:
 *       - Addresses with 5-200 characters
 *       - Addresses with letters, numbers, basic punctuation
 *       - Addresses with Vietnamese characters
 *       
 *       Invalid Classes:
 *       - Null or empty input
 *       - Addresses too short (<5) or too long (>200)
 *       - Addresses with invalid characters
 *       - Addresses with excessive whitespace
 * 
 *    b) Boundary Value Analysis:
 *       - 4 characters (just below min)
 *       - 5 characters (min valid length)
 *       - 200 characters (max valid length)
 *       - 201 characters (just above max)
 * 
 *    c) Decision Table Testing:
 *       Conditions:
 *       - Is input null/empty?
 *       - Is length within bounds?
 *       - Contains only valid characters?
 *       - Has valid spacing?
 *       
 *       Rules covered in test cases:
 *       R1: null/empty input → false
 *       R2: invalid length → false
 *       R3: invalid characters → false
 *       R4: invalid spacing → false
 *       R5: all conditions met → true
 */

/**
 * Testing Strategy for validateDeliveryInfo():
 * 
 * 1. Testing Approach: Black Box Testing
 *    - Integration testing of all validation methods
 * 
 * 2. Testing Techniques Used:
 * 
 *    a) Equivalence Partitioning:
 *       Valid Classes:
 *       - HashMap with all valid required fields
 *       - HashMap with optional fields
 *       
 *       Invalid Classes:
 *       - Null or empty HashMap
 *       - Missing required fields
 *       - Invalid field values
 * 
 *    b) Decision Table Testing:
 *       Conditions:
 *       - Is HashMap null/empty?
 *       - Are required fields present?
 *       - Is phone valid?
 *       - Is name valid?
 *       - Is address valid?
 *       
 *       Rules covered in test cases:
 *       R1: null/empty HashMap → throws exception
 *       R2: missing required fields → throws exception
 *       R3: invalid field values → throws exception
 *       R4: all valid → no exception
 */

/**
 * Testing Strategy for calculateShippingFee():
 * 
 * 1. Testing Approach: Black Box Testing
 *    - Testing shipping fee calculation based on order value and items
 * 
 * 2. Testing Techniques Used:
 * 
 *    a) Equivalence Partitioning:
 *       Valid Classes:
 *       - Orders below 100,000 VND (no free shipping)
 *       - Orders above 100,000 VND (eligible for free shipping)
 *       - Orders with single item
 *       - Orders with multiple items
 *       
 *    b) Boundary Value Analysis:
 *       - Order value at 99,999 VND (just below free shipping)
 *       - Order value at 100,000 VND (minimum for free shipping)
 *       - Order with 0 items (empty order)
 *       - Order with 1 item (minimum items)
 * 
 *    c) Decision Table Testing:
 *       Conditions:
 *       - Is order amount > 100,000 VND?
 *       - Is calculated fee > 25,000 VND?
 *       - Does order have items?
 */

public class PlaceOrderControllerTest {
    
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @Test
    void validatePhoneNumber_nullInput_returnsFalse() {
        // Test case 1: Null input should return false
        // Technique: Decision Table Testing - R1
        assertFalse(placeOrderController.validatePhoneNumber(null));
    }

    @Test
    void validatePhoneNumber_emptyString_returnsFalse() {
        // Test case 2: Empty string should return false
        // Technique: Decision Table Testing - R2
        // Technique: Boundary Value Analysis - minimum length
        assertFalse(placeOrderController.validatePhoneNumber(""));
    }

    @Test
    void validatePhoneNumber_invalidLength_returnsFalse() {
        // Test case 3: Phone numbers with invalid length should return false
        // Technique: Equivalence Partitioning - invalid length class
        // Technique: Boundary Value Analysis - testing boundaries (9 and 11 digits)
        assertFalse(placeOrderController.validatePhoneNumber("123456789")); // 9 digits
        assertFalse(placeOrderController.validatePhoneNumber("12345678901")); // 11 digits
    }

    @Test
    void validatePhoneNumber_invalidPrefix_returnsFalse() {
        // Test case 4: Phone numbers with invalid prefixes should return false
        // Technique: Equivalence Partitioning - invalid prefix class
        // Technique: Decision Table Testing - R5
        assertFalse(placeOrderController.validatePhoneNumber("1234567890")); // starts with 1
        assertFalse(placeOrderController.validatePhoneNumber("0234567890")); // starts with 02
        assertFalse(placeOrderController.validatePhoneNumber("0634567890")); // starts with 06
    }

    @Test
    void validatePhoneNumber_containsNonDigits_returnsFalse() {
        // Test case 5: Phone numbers containing non-digits should return false
        // Technique: Equivalence Partitioning - invalid character class
        // Technique: Decision Table Testing - R4
        assertFalse(placeOrderController.validatePhoneNumber("0912345a89")); // contains letter
        assertFalse(placeOrderController.validatePhoneNumber("091-234589")); // contains hyphen
        assertFalse(placeOrderController.validatePhoneNumber("0912 34589")); // contains space
    }

    @Test
    void validatePhoneNumber_validPhoneNumbers_returnsTrue() {
        // Test case 6: Valid phone numbers should return true
        // Technique: Equivalence Partitioning - valid phone number class
        // Technique: Decision Table Testing - R6 (all conditions met)
        assertTrue(placeOrderController.validatePhoneNumber("0912345678")); // starts with 09
        assertTrue(placeOrderController.validatePhoneNumber("0312345678")); // starts with 03
        assertTrue(placeOrderController.validatePhoneNumber("0712345678")); // starts with 07
        assertTrue(placeOrderController.validatePhoneNumber("0512345678")); // starts with 05
        assertTrue(placeOrderController.validatePhoneNumber("0812345678")); // starts with 08
    }

    @Test
    void validateName_nullOrEmpty_returnsFalse() {
        // Test case 1: Null or empty input should return false
        // Technique: Decision Table Testing - R1
        assertFalse(placeOrderController.validateName(null));
        assertFalse(placeOrderController.validateName(""));
        assertFalse(placeOrderController.validateName("   "));
    }

    @Test
    void validateName_invalidLength_returnsFalse() {
        // Test case 2: Names with invalid length should return false
        // Technique: Boundary Value Analysis - testing boundaries
        // Technique: Equivalence Partitioning - invalid length class
        assertFalse(placeOrderController.validateName("a")); // 1 character
        assertFalse(placeOrderController.validateName("a".repeat(51))); // 51 characters
    }

    @Test
    void validateName_invalidCharacters_returnsFalse() {
        // Test case 3: Names with invalid characters should return false
        // Technique: Equivalence Partitioning - invalid character class
        assertFalse(placeOrderController.validateName("John123"));
        assertFalse(placeOrderController.validateName("John@Doe"));
        assertFalse(placeOrderController.validateName("John_Doe"));
    }

    @Test
    void validateName_invalidSpacing_returnsFalse() {
        // Test case 4: Names with invalid spacing should return false
        // Technique: Equivalence Partitioning - invalid spacing class
        assertFalse(placeOrderController.validateName(" John")); // starts with space
        assertFalse(placeOrderController.validateName("John ")); // ends with space
        assertFalse(placeOrderController.validateName("John  Doe")); // consecutive spaces
    }

    @Test
    void validateName_validNames_returnsTrue() {
        // Test case 5: Valid names should return true
        // Technique: Equivalence Partitioning - valid name class
        // Technique: Decision Table Testing - R5 (all conditions met)
        assertTrue(placeOrderController.validateName("An")); // minimum length
        assertTrue(placeOrderController.validateName("John Doe")); // with space
        assertTrue(placeOrderController.validateName("Nguyễn Văn An")); // Vietnamese
        assertTrue(placeOrderController.validateName("a".repeat(50))); // maximum length
    }

    @Test
    void validateAddress_nullOrEmpty_returnsFalse() {
        // Test case 1: Null or empty input should return false
        // Technique: Decision Table Testing - R1
        assertFalse(placeOrderController.validateAddress(null));
        assertFalse(placeOrderController.validateAddress(""));
        assertFalse(placeOrderController.validateAddress("   "));
    }

    @Test
    void validateAddress_invalidLength_returnsFalse() {
        // Test case 2: Addresses with invalid length should return false
        // Technique: Boundary Value Analysis - testing boundaries
        // Technique: Equivalence Partitioning - invalid length class
        assertFalse(placeOrderController.validateAddress("A12")); // 3 chars
        assertFalse(placeOrderController.validateAddress("a".repeat(201))); // 201 chars
    }

    @Test
    void validateAddress_invalidCharacters_returnsFalse() {
        // Test case 3: Addresses with invalid characters should return false
        // Technique: Equivalence Partitioning - invalid character class
        assertFalse(placeOrderController.validateAddress("House #$%")); // invalid special chars
        assertFalse(placeOrderController.validateAddress("Address\n123")); // newline
        assertFalse(placeOrderController.validateAddress("Street|123")); // pipe
    }

    @Test
    void validateAddress_invalidSpacing_returnsFalse() {
        // Test case 4: Addresses with invalid spacing should return false
        // Technique: Equivalence Partitioning - invalid spacing class
        assertFalse(placeOrderController.validateAddress(" 123 Street")); // starts with space
        assertFalse(placeOrderController.validateAddress("123 Street ")); // ends with space
        assertFalse(placeOrderController.validateAddress("123   Street")); // excessive spaces
    }

    @Test
    void validateAddress_validAddresses_returnsTrue() {
        // Test case 5: Valid addresses should return true
        // Technique: Equivalence Partitioning - valid address class
        // Technique: Decision Table Testing - R5 (all conditions met)
        assertTrue(placeOrderController.validateAddress("12345")); // minimum length
        assertTrue(placeOrderController.validateAddress("123 Main Street")); // with space
        assertTrue(placeOrderController.validateAddress("Số 15, Đường Lê Lợi")); // Vietnamese
        assertTrue(placeOrderController.validateAddress("Apartment 2B, 123/45 Nguyen Hue Street, District 1")); // complex
        assertTrue(placeOrderController.validateAddress("a".repeat(200))); // maximum length
    }

    @Test
    void validateDeliveryInfo_nullOrEmpty_throwsException() {
        // Test case 1: Null or empty HashMap should throw exception
        // Technique: Decision Table Testing - R1
        Exception exception = assertThrows(InterruptedException.class, () -> {
            placeOrderController.validateDeliveryInfo(null);
        });
        assertEquals("Delivery Info cannot be null", exception.getMessage());

        exception = assertThrows(InterruptedException.class, () -> {
            placeOrderController.validateDeliveryInfo(new HashMap<>());
        });
        assertEquals("Delivery Info cannot be empty", exception.getMessage());
    }

    @Test
    void validateDeliveryInfo_missingRequiredFields_throwsException() {
        // Test case 2: Missing required fields should throw exception
        // Technique: Equivalence Partitioning - invalid class (missing fields)
        HashMap<String, String> info = new HashMap<>();
        info.put("name", "John Doe"); // missing phone and address

        Exception exception = assertThrows(InterruptedException.class, () -> {
            placeOrderController.validateDeliveryInfo(info);
        });
        assertEquals("Phone number is required", exception.getMessage());

        info.put("phone", "0912345678"); // missing address
        exception = assertThrows(InterruptedException.class, () -> {
            placeOrderController.validateDeliveryInfo(info);
        });
        assertEquals("Address is required", exception.getMessage());
    }

    @Test
    void validateDeliveryInfo_invalidFieldValues_throwsException() {
        // Test case 3: Invalid field values should throw exception
        // Technique: Equivalence Partitioning - invalid class (invalid values)
        HashMap<String, String> info = new HashMap<>();
        info.put("phone", "invalid");
        info.put("name", "John123");
        info.put("address", "a"); // too short

        Exception exception = assertThrows(InterruptedException.class, () -> {
            placeOrderController.validateDeliveryInfo(info);
        });
        assertTrue(exception.getMessage().contains("Invalid"));
    }

    @Test
    void validateDeliveryInfo_validInfo_noException() throws InterruptedException, IOException {
        // Test case 4: Valid delivery info should not throw exception
        // Technique: Equivalence Partitioning - valid class
        // Technique: Decision Table Testing - R4 (all conditions met)
        HashMap<String, String> info = new HashMap<>();
        info.put("phone", "0912345678");
        info.put("name", "Nguyễn Văn An");
        info.put("address", "123 Lê Lợi, District 1");
        info.put("instructions", "Call before delivery"); // optional field

        // Should not throw any exception
        assertDoesNotThrow(() -> placeOrderController.validateDeliveryInfo(info));
    }

    @Test
    void calculateShippingFee_emptyOrder_returnsBaseFee() {
        // Test case 1: Empty order should return base fee
        // Technique: Boundary Value Analysis - minimum items
        Order order = new Order();
        assertEquals(22000, placeOrderController.calculateShippingFee(order));
    }

    @Test
    void calculateShippingFee_singleItem_returnsCorrectFee() throws SQLException {
        // Test case 2: Single item order
        // Technique: Boundary Value Analysis - minimum items
        Order order = new Order();
        Media media = new Media();  // Create a mock media object
        OrderMedia item = new OrderMedia(media, 1, 50000);
        order.addOrderMedia(item);

        // Expected: Base fee (22,000) + 1 item fee (2,500) = 24,500
        assertEquals(24500, placeOrderController.calculateShippingFee(order));
    }

    @Test
    void calculateShippingFee_multipleItems_returnsCorrectFee() throws SQLException {
        // Test case 3: Multiple items order
        // Technique: Equivalence Partitioning - multiple items class
        Order order = new Order();
        Media media1 = new Media();  // Create mock media objects
        Media media2 = new Media();
        OrderMedia item1 = new OrderMedia(media1, 1, 30000);
        OrderMedia item2 = new OrderMedia(media2, 2, 20000);
        order.addOrderMedia(item1);
        order.addOrderMedia(item2);

        // Expected: Base fee (22,000) + 3 items fee (3 * 2,500) = 29,500
        assertEquals(29500, placeOrderController.calculateShippingFee(order));
    }

    @Test
    void calculateShippingFee_belowFreeShipping_returnsFullFee() throws SQLException {
        // Test case 4: Order below free shipping threshold
        // Technique: Boundary Value Analysis - just below threshold
        Order order = new Order();
        Media media = new Media();
        OrderMedia item = new OrderMedia(media, 1, 99999);
        order.addOrderMedia(item);

        // Expected: Base fee (22,000) + 1 item fee (2,500) = 24,500
        assertEquals(24500, placeOrderController.calculateShippingFee(order));
    }

    @Test
    void calculateShippingFee_aboveFreeShipping_returnsDiscountedFee() throws SQLException {
        // Test case 5: Order above free shipping threshold
        // Technique: Equivalence Partitioning - free shipping class
        Order order = new Order();
        Media media = new Media();
        OrderMedia item = new OrderMedia(media, 1, 150000);
        order.addOrderMedia(item);

        // Expected: Base fee (22,000) + 1 item fee (2,500) - 24,500
        // But capped at free shipping discount of 25,000
        assertEquals(0, placeOrderController.calculateShippingFee(order));
    }

    @Test
    void calculateShippingFee_exactFreeShippingThreshold_returnsDiscountedFee() throws SQLException {
        // Test case 6: Order exactly at free shipping threshold
        // Technique: Boundary Value Analysis - at threshold
        Order order = new Order();
        Media media = new Media();
        OrderMedia item = new OrderMedia(media, 1, 100000);
        order.addOrderMedia(item);

        // Should apply free shipping discount
        assertEquals(0, placeOrderController.calculateShippingFee(order));
    }
} 