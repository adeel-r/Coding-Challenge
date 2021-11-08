package com.company;

import com.company.UtilityClasses.Coins;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    // Array of available coins
    // All available coins are stored as Pences i.e. 2£ = 200Pence
    public static String[] availableCoins = new String[]{"200", "100", "50", "20", "10", "5", "2", "1"};

    // Creating Arraylist to store our minimum number of sterling coins
    public static ArrayList<Coins> possibleCoins = new ArrayList<>();




    public static String addCoinNotation(int coin)
    {
        switch (coin)
        {
            case 200:
                return String.format("%s%d", "£",2);
            case 100:
                return String.format("%s%d", "£",1);

            default:
                return String.format("%d%s", coin,"p");
        }
    }


    public static void displayPossibleChange()
    {
        System.out.print("Minimum Number of Coins Required = ");
        for (int i = 0; i < possibleCoins.size(); ++i)
        {
            if( i == possibleCoins.size()-1)
            {
                System.out.print(possibleCoins.get(i).getNumberOfCoins() + " " + "x " +
                        addCoinNotation(possibleCoins.get(i).getCoinType()));
            }
            else
            {
                System.out.print(possibleCoins.get(i).getNumberOfCoins() + " " + "x " +
                        addCoinNotation(possibleCoins.get(i).getCoinType()) + ", ");
            }
        }
    }


    // This function separates £, p, digits, and validate the input
    public static String[] inputAnalyzer(String amount)
    {
        String poundFlag = "false";
        String penceFlag = "false";
        String extractDigit = "";

        // removing all spaces if present
        amount = amount.trim();

        for (int i = 0; i < amount.length(); ++i)
        {
            // Check if index contains any alphabet other than p
            if( (amount.charAt(i) >= 'a' && amount.charAt(i) < 'p') || (amount.charAt(i) > 'p' && amount.charAt(i) <= 'z')
                    || (amount.charAt(i) >= 'A' && amount.charAt(i) <= 'Z') )
            {
                return null;
            }

            // Check if the index contains £ symbol
            if ( amount.charAt(i) == '£' )
            {
                poundFlag = "true";
            }

            // Check if the index contains p = pence symbol
            if ( amount.charAt(i) == 'p' )
            {
                penceFlag = "true";
            }

            // Check if it is a digit or .
            if ( (amount.charAt(i) >= '0' && amount.charAt(i) <= '9') || amount.charAt(i) == '.' )
            {
                extractDigit = String.format("%s%c", extractDigit, amount.charAt(i));
            }
        }

        return new String[] {poundFlag, penceFlag, extractDigit};
    }


    public static int roundOff( String pences )
    {
        String str = "";

        for (int i = 0; i < 2; ++i)
        {
            str = String.format("%s%c", str, pences.charAt(i));
        }

        return ( Integer.parseInt(str) + 1 );
    }


    public static String convertToPence(String[] analyzedString)
    {
        String pences = "";
        String poundSymbol = analyzedString[0];
        String penceSymbol = analyzedString[1];
        String amount = analyzedString[2];

        if ( amount.contains(".") )
        {
            int pounds = 0;
            int pence = 0;

            String[] poundPence = amount.split( "\\." );

            for (int i = 0; i <poundPence.length; ++i)
            {

                // check if it is the first index
                if( i == 0 )
                {
                    pounds = Integer.parseInt(poundPence[i]) * 100;
                }

                // Check if the user has entered any value after dot
                if ( i == 1 )
                {
                    // Check how many digits have entered by the user
                    if ( poundPence[i].length() > 2 )
                    {
                        // Call the function to round off if there are more than 2 digits
                        pence = roundOff(poundPence[i]);
                    }
                    else
                    {
                        pence = Integer.parseInt(poundPence[i]);
                    }
                }
            }

            pences = String.valueOf( pounds + pence );
        }
        else
        {
            if ( poundSymbol.equals("true") || (poundSymbol.equals("false") && penceSymbol.equals("false")) )
            {
                pences = String.valueOf(Integer.parseInt(amount) * 100);
            }

            if (poundSymbol.equals("false") && penceSymbol.equals("true"))
            {
                pences = amount;
            }

        }

        return pences;
    }


    // This function convert the amount into coins
    public static void getCoins(String amount)
    {
        // Looping through all the available coins to start the conversion
        for (int i=0; i< availableCoins.length; ++i)
        {
            if (Integer.parseInt(availableCoins[i]) <= Integer.parseInt(amount))
            {
                int quotient = Integer.parseInt(amount) / Integer.parseInt(availableCoins[i]);
                int remainder = Integer.parseInt(amount) % Integer.parseInt(availableCoins[i]);

                // Initializing new object of the coin to store it in Arraylist
                Coins coin = new Coins();

                // Setting the values
                coin.setCoinType(Integer.parseInt(availableCoins[i]));
                coin.setNumberOfCoins(quotient);

                // Stored in Arraylist
                possibleCoins.add(coin);

                // if there is no change left then break the loop
                if (remainder == 0)
                {
                    break;
                }
                else
                {
                    amount = Integer.toString(remainder);
                }
            }
        }
    }


    public static void solution(String amount)
    {
        // Check if the string is empty or not
        if (amount.equals(""))
        {
            System.out.print("No value entered.....");
            return;
        }

        // Calling the function to analyze symbols digits and validity
        String[] analyzedString = inputAnalyzer(amount);

        if ( analyzedString == null )
        {
            System.out.print("Invalid Input....");
            return;
        }
        if (analyzedString[2].equals(""))
        {
            System.out.print("No digit is entered.....");
        }
        else
        {
            // Calling the function to convert the amount in total pences
            String totalPences = convertToPence(analyzedString);

            // Now finally get minimum number of coins required for the given amount
            getCoins( totalPences );

            // Display coins
            displayPossibleChange();
        }

    }


    public static void main(String[] args)
    {

        // Initializing Scanner Class to get input from terminal
        Scanner scanner = new Scanner(System.in);

        // Initializing String variable with empty string to get input
        String inputString;

        System.out.print("Enter a value: ");
        inputString = scanner.nextLine();

        solution(inputString);

    }
}
