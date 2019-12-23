/*

CSCI 6617 S2 Fall 2019
Java Programming
KRIKOR HERLOPIAN
Kherl1@unh.newhaven.edu
Instructor: Sheehan

CurrencyConverter.java
Application that converts a user inputed amount from one currency into another currency.
Only US Dollars, Indian Rupees, British Punds and Swiss Francs supported.

 */
import java.util.*;

/**
 *
 * @author kherl1
 */
public class CurrencyConverter {

    //list of Currencies available for conversion from and into
    static String[] currencies = {"United States Dollars (USD)", "British Pounds (GBP)", "Swiss Francs (CHF)", "Indian Rupees (INR)"};
    //data conversion table
    static double[][] currencyConversionTable = new double[4][4];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        setUpCurrencyConversionTable();
        System.out.println("Welcome to currency converter application");
        int sourceCurrency = 0;
        int resultCurrency = 0;
        double amountToConvert = 0;
        //print currency options available for user to pick
        for (int i = 0; i < currencies.length; i++) {
            System.out.println((i + 1) + " to select " + currencies[i] + " ");
        }
        System.out.println("");
        while (true) {
            sourceCurrency = selectCurrency("Choose the currency you want to convert from 1 to 4(Q or q to quit)", -1);
            //if firstCurrency returned 0, it means q or Q was typed. so quit from the loop and exit application.
            if (sourceCurrency == 0) {
                break;
            }
            // print what the user has selected
            print(sourceCurrency, "convert from");

            amountToConvert = enterAmount("Enter amount you wish to convert(Q or q to quit)");
            //if amounttoconvert returned 0, it means q or Q was typed. so quit from the loop and exit application.
            if (amountToConvert == 0) {
                break;
            }
            // print the amount user typed. In case he typed 20, it will print 20.0
            System.out.println("You entered " + amountToConvert + " as amount to convert.");

            resultCurrency = selectCurrency("Choose the currency you want to convert into 1 to 4(Q or q to quit)", sourceCurrency);
            //if secondCurrency returned 0, it means q or Q was typed. so quit from the loop and exit application.
            if (resultCurrency == 0) {
                break;
            }
            // print what the user has selected
            print(resultCurrency, "convert to");

            // array index starts from 0, not 1. So Selection 1 = USD for user is array index of 0 for developer.
            sourceCurrency = sourceCurrency - 1;
            resultCurrency = resultCurrency - 1;
            double convertedAmount = currencyConversionTable[sourceCurrency][resultCurrency] * amountToConvert;
            convertedAmount = Math.round(convertedAmount * 100);
            convertedAmount = convertedAmount / 100;
            System.out.println(String.format("%.2f", convertedAmount) + " " + currencies[resultCurrency] + " = " + String.format("%.2f", amountToConvert) + " " + currencies[sourceCurrency]);

            //ask if user wants to do more conversion.if user enters y or Y continue with the same process again
            //else post thank you message and break out of the while loop.
            System.out.println("Do you want to do another conversion(enter y or Y) or terminate the program(anything else)");
            String input = sc.nextLine();
            if (input != null && input.trim().toUpperCase().equals("Y")) {
                System.out.println("");
                System.out.println("New currency converstion to start.");
            } else {
                System.out.println("Thank You for using currency converter application.");
                break;
            }

        }
    }

    // print what the user has selected, like You selected to convert from  USD. Or You selected to convert to INR.
    static void print(int selectedCurrency, String information) {
        switch (selectedCurrency) {
            case 1:
                System.out.println("You selected to " + information + " USD");
                break;
            case 2:
                System.out.println("You selected to " + information + " GBP");
                break;
            case 3:
                System.out.println("You selected to " + information + " CHF");
                break;
            case 4:
                System.out.println("You selected to " + information + " INR");
                break;
        }
    }

    /**
     * Prompt user to enter amount.IF amount is double by checking for
     * sc.hasNextDouble() returning true check if its between 0 and one
     * million.If its negative number or 0 or above one million prompt user to
     * enter amount again Else if amount entered is between 0 and one million
     * return correct result In case hasNextdouble returns false, check if user
     * typed q or Q to return 0 and quit app in main method or else if he typed
     * something else like prompt him to enter valid amount.
     *
     * @param message
     * @return
     */
    static double enterAmount(String message) {
        Scanner sc = new Scanner(System.in);
        double amountToConvert = 0;

        //prompt user to enter amount.
        System.out.println(message);
        if (sc.hasNextDouble()) {
            amountToConvert = sc.nextDouble();
            //if user entered negative amount, or zero or above one million prompt him to enter correct number.
            if (amountToConvert <= 0 || amountToConvert > 1000000) {
                return enterAmount("You entered " + amountToConvert + ". Please Enter  Amount greater then 0 and less than or equal to one million(Q or q to quit).\nMaximum Amount allowed is one Million.");
            }
            //if all is valid, return valid amount typed by user
            return amountToConvert;
        } else {
            String input = sc.next();
            //is user typed q or Q return 0, to quit application in main method.
            if (input != null && input.trim().toUpperCase().equals("Q")) {
                System.out.println("You entered " + input + ".Thank You for using currency converter application. We are sad to see you go.");
                return 0;
            } //if user typed anything else, prompt him to make valid selection and repeat selection process again.
            else {
                return enterAmount("You entered " + input + ". Please Enter Valid Amount to convert(Q or q to quit)");
            }
        }

    }

    /**
     * This function is called for both selecting currency to convert from, and
     * currency to convert into Ask for input, if a number is entered by
     * checking for hasNextInt() returning true and its not between 1 and 4
     * prompt user to choose a number between 1 and 4 else if in case of
     * selecting currency to convert into is same like currency chosen to
     * convert from prompt user not to select same currency conversion Else if
     * correct input number was entered return result.In case sc.hasNextInt()
     * returns false aka not number was typed check if user typed q or Q to
     * return 0 to quit the app in main method else if user typed something else
     * prompt user to type valid information.
     */
    static int selectCurrency(String message, int sourceCurrency) {
        Scanner sc = new Scanner(System.in);
        //prompt user to choose currency 
        System.out.println(message);
        if (sc.hasNextInt()) {
            int result = sc.nextInt();
            //if user typed a number below 1 or above 4 , prompt him to choose correctly.
            if (result <= 0 || result > 4) {
                return selectCurrency("You entered " + result + " Please choose a number between 1 and 4.Select again.", sourceCurrency);
            } //if two currency chosen are same, inform user to select a different currency to convert into.
            else if (result == sourceCurrency) {
                return selectCurrency("You entered " + result + ".  You cannot convert into same currency.Select again.", sourceCurrency);
            } else {
                //in case every validation passed return correct result.
                return result;
            }
        } else {
            String input = sc.next();
            //is user typed q or Q , thank him and return 0 to quit app in main method above.
            if (input != null && input.toUpperCase().equals("Q")) {
                System.out.println("You entered " + input + ".Thank You for using currency converter application.We are sad to see you go.");
                return 0;
            } //if user typed anything else, prompt him to make valid selection and repeat selection process again.
            else {
                return selectCurrency("You entered " + input + ".Please Make Valid Selection(like type 1).Select again.", sourceCurrency);
            }
        }
    }

    /*
        this function sets up the table conversion.Multi Dimensional array.
     */
    static void setUpCurrencyConversionTable() {
        //from USD.
        currencyConversionTable[0][0] = 1.000000;//to usd
        currencyConversionTable[0][1] = 0.814349;//to gbp
        currencyConversionTable[0][2] = 0.987370;//to chf
        currencyConversionTable[0][3] = 71.687245;// to inr

        //from GBP
        currencyConversionTable[1][0] = 1.227902;//to usd
        currencyConversionTable[1][1] = 1.000000;//to gbp
        currencyConversionTable[1][2] = 1.212602;// to chf
        currencyConversionTable[1][3] = 88.022221;// to inr

        //from CHF
        currencyConversionTable[2][0] = 1.012514;//to usd
        currencyConversionTable[2][1] = 0.824820;//to gbp
        currencyConversionTable[2][2] = 1.000000;//to chf
        currencyConversionTable[2][3] = 72.582298;//to inr

        //from INR
        currencyConversionTable[3][0] = 0.013950;//to usd
        currencyConversionTable[3][1] = 0.011365;//to gbp
        currencyConversionTable[3][2] = 0.013777;//to chf
        currencyConversionTable[3][3] = 1.000000;//to inr
    }
}
