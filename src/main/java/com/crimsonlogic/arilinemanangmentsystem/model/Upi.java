package com.crimsonlogic.arilinemanangmentsystem.model;

/**
 * Entity representing a Upi in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Upi {

    private String upiId;

    private String upiPassword;

    private String bankName;

    private String bankAccountNumber;

    private double balance;

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-25s %-20s %-20s %-12s%n",
                "UPI ID",
                "Bank Name",
                "Bank Account",
                "Balance"
        );

        System.out.println("----------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-25s %-20s %-20s %-12.2f",
                upiId,
                bankName,
                bankAccountNumber,
                balance
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
            +--------------------------------------------------------------------------------------+
            | %-20s | %-50s |
            +--------------------------------------------------------------------------------------+
            | %-20s | %-50s |
            | %-20s | %-50s |
            | %-20s | %-50s |
            | %-20s | %-50.2f |
            | %-20s | %-50s |
            +--------------------------------------------------------------------------------------+
            """,
                "Field", "Value",
                "UPI ID", upiId,
                "Password", "********",
                "Bank Name", bankName,
                "Balance", balance,
                "Bank Account", bankAccountNumber
        );
    }

    /**

     * Retrieves the upiid.

     */

    public String getUpiId() {
        return upiId;
    }

    /**

     * Updates the upiid.

     */

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    /**

     * Retrieves the upipassword.

     */

    public String getUpiPassword() {
        return upiPassword;
    }

    /**

     * Updates the upipassword.

     */

    public void setUpiPassword(String upiPassword) {
        this.upiPassword = upiPassword;
    }

    /**

     * Retrieves the bankname.

     */

    public String getBankName() {
        return bankName;
    }

    /**

     * Updates the bankname.

     */

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**

     * Retrieves the bankaccountnumber.

     */

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**

     * Updates the bankaccountnumber.

     */

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    /**

     * Retrieves the balance.

     */

    public double getBalance() {
        return balance;
    }

    /**

     * Updates the balance.

     */

    public void setBalance(double balance) {
        this.balance = balance;
    }
}