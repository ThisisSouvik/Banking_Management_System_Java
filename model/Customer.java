
package model;

public class Customer {

    // Static variable: generates a unique customer ID for each customer 
    private static int nextCustomerId = 1;

    private final int customerId;
    private String name;
    private String phoneNumber;

    private Customer(String name, String phoneNumber) {
        this.customerId = nextCustomerId++;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Static factory method: keeps customer creation in one validated path
    public static Customer create(String name, String phoneNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        if (phoneNumber == null || phoneNumber.length() != 10) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
            }
        }

        return new Customer(name.trim(), phoneNumber);
    }

    // Encapsulation: fields are private, accessed through getters/setters
    // customerId has no setter because it should never change
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Two customers are considered equal if they have the same customerId
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Customer customer = (Customer) obj;
        return customerId == customer.customerId;
    }

    // Objects that are equal() must return the same hashCode()
    @Override
    public int hashCode() {
        return Integer.hashCode(customerId);
    }
}
