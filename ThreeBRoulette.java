package CasinoProject.Roulette;

import CasinoProject.MainUser;

import java.util.Random;
import java.util.Scanner;

import static CasinoProject.CasinoGamesHelper.moneyFormat;
import static CasinoProject.CasinoGamesHelper.pauseAction;
import static CasinoProject.CasinoGamesHelper.readYesNo;

public class ThreeBRoulette {
    //activating the imports
    static Scanner keyboard = new Scanner(System.in);
    static Random rdm = new Random();
    //generating the figures and odds for generating
    static final int NUMBERODDS = 35, OTHERODDS = 2;
    static int bigRandom = rdm.nextInt(37);
    static int smallRandom = rdm.nextInt(2);
    //in milliseconds. For passing as parameters into pauseAction
    private static final int SHORTPAUSE = 2000, LONGPAUSE = 3500;
    //Values for games in menus
    private static final int NUMBERGAME = 1, ODDEVENGAME = 2, COLOURGAME = 3, TERMINATOR = 4;

    //prints introductory welcome message
    private static void printIntoMessage() {
        System.out.println("Welcome to Three Bet Roulette!");
        pauseAction(SHORTPAUSE);
        System.out.println("Lets get started!");
    }//printIntoMessage

    //gets the value from scanner for stake
    private static double readStake(MainUser user) {
        double stake = -1;
        boolean validStake = false;
        while (!validStake){
            System.out.println("You currently have " + moneyFormat.format(user.getBank()) + " in the bank"+ "\n");
            System.out.print("Please enter your stake: ");
            stake = keyboard.nextDouble();
            if(user.getBank() >= stake && stake > 0){
                validStake = true;
            }//if
            else if (user.getBank() < stake){
                System.out.println("You have insufficient funds, please try again");
            }//elseif
            else {
                System.out.println("This field cannot be zero.");
            }//else
        }//while

        return stake;
    }//readStake

    private static String menuString() {
        return "Please select the bet you would like to make\n\n" +
                NUMBERGAME + ". Bet on the number the ball lands on\n" +
                ODDEVENGAME + ". Bet on the number being ODD or EVEN\n" +
                COLOURGAME + ". Bet on the colour the number lands on (Red or Black)\n" +
                +TERMINATOR + ". Alternatively leave the game and return to the Main Menu.";
    }//menuString

    private static int readGameChoice() {
        int menuChoice;
        boolean validChoice;

        do {
            System.out.println(menuString());
            System.out.print("Selection : ");
            menuChoice = keyboard.nextInt();
            if (menuChoice == NUMBERGAME || menuChoice == ODDEVENGAME || menuChoice == COLOURGAME || menuChoice == TERMINATOR) {
                validChoice = true;
            }//if
            else {
                System.out.println("Invalid choice entered. Please try again\n");
                validChoice = false;
            }//else
        } while (!validChoice);

        return menuChoice;
    }//readGameChoice

    //identifies the number bet is being placed on and stores as a variable
    private static int readNumberBet() {
        int userNumber = -1;
        boolean validNumber = false;
        while (!validNumber) {
            System.out.println("\nLet's place a number bet, the odds of winning are 35/1\n");
            System.out.print("Please enter the number you would like to bet on between 0-36: ");
            userNumber = keyboard.nextInt();
            validNumber = (userNumber >= 0 && userNumber <= 36);
            if (!validNumber)
                System.out.println("You must enter a number from 0-36\n");
        }//while
        return userNumber;
    }//readNumberBet

    //identifies if the bet is being placed on Odd or Even and stores that selection
    private static int readOddEven() {
        int odd = 1;
        int even = 2;
        System.out.println("\nLet's place a bet on the ball landing on an EVEN or ODD number, the odds of winning are 2/1");
        System.out.print("Please select \n"
                + odd + ". to bet on an ODD number or \n"
                + even + ". to bet on an EVEN number \n"
                + "select:");
        return keyboard.nextInt();
    }//readOddEven

    //identifies if the bet is being placed on red or black and stores that selection
    private static int readColourBet() {
        int red = 1;
        int black = 2;
        System.out.println("\nLet's place a bet on which colour the ball will land on, the odds of winning are 2/1\n");
        System.out.print("Please select the colour you would like to bet on, \n"
                + red + ". Red\n"
                + black + ". Black\n"
                + "select:");
        return keyboard.nextInt();
    }//readColourBet

    private static void runGame(MainUser user, int menuChoice, double stake) {
        if (menuChoice == NUMBERGAME)
            runNumber(user,stake);
        if (menuChoice == ODDEVENGAME)
            runOddEven(user,stake);
        if (menuChoice == COLOURGAME)
            runColour(user,stake);
    }//runGame

    //runs the process to compare input vs random generation, outputs win/loss for a number bet
    private static void runNumber(MainUser user, double stake) {
        int number = readNumberBet();
        pauseAction(LONGPAUSE);
        if (number == bigRandom) {
            user.setBank(user.getBank() + (stake*35+stake));
            System.out.println("You have WON!");
            System.out.println("That bet earned you " + moneyFormat.format(stake*35+stake));
            System.out.println("Your new balance is " + moneyFormat.format(user.getBank()));
        }//if
        else {
            user.setBank(user.getBank() - stake);
            System.out.println("You have LOST!");
            System.out.println("You lost " + moneyFormat.format(stake));
            System.out.println("Your balance is now " + moneyFormat.format(user.getBank()));
        }//else
    }//runNumber

    //runs the process to compare input vs random generation, outputs win/loss for an odd or even bet
    private static void runOddEven(MainUser user, double stake) {
        int number = readOddEven();
        pauseAction(LONGPAUSE);
        if (number == smallRandom) {
            user.setBank(user.getBank() + (stake * OTHERODDS + stake));
            System.out.println("You have WON!");
            System.out.println("That bet earned you " + moneyFormat.format(stake*2+stake));
            System.out.println("Your new balance is " + moneyFormat.format(user.getBank()));
        }//if
        else {
            user.setBank(user.getBank() - stake);
            System.out.println("You have LOST!");
            System.out.println("You lost " + moneyFormat.format(stake));
            System.out.println("Your balance is now " + moneyFormat.format(user.getBank()));
        }//else
    }//runOddEven

    //runs the process to compare input vs random generation, outputs win/loss for a bet on red or black
    private static void runColour(MainUser user, double stake) {
        int number = readColourBet();
        String colourChosen;
        if (number==1)
            colourChosen = "Red";
        else
            colourChosen = "Black";

        System.out.println("you have selected " + colourChosen);
        pauseAction(LONGPAUSE);
        if (number == smallRandom) {
            user.setBank(user.getBank() + (stake * OTHERODDS + stake));
            System.out.println("You have WON!");
            System.out.println("That bet earned you " + moneyFormat.format(stake*2+stake));
            System.out.println("Your new balance is " + moneyFormat.format(user.getBank()));
        }//if
        else {
            user.setBank(user.getBank() - stake);
            System.out.println("You have LOST!");
            System.out.println("You lost " + moneyFormat.format(stake));
            System.out.println("Your balance is now " + moneyFormat.format(user.getBank()));
        }//else
    }//runNumber

    //This is the method that sets and runs the layout of the game
    public static void playGame(MainUser user) {
        printIntoMessage();
        boolean playAgain = true;

        while (playAgain) {
            pauseAction(SHORTPAUSE);
            int menuChoice = readGameChoice();

            if (menuChoice != TERMINATOR) {
                double stake = readStake(user);
                runGame(user, menuChoice,stake);

                pauseAction(LONGPAUSE);
                System.out.println("Play again?");
                playAgain = readYesNo();
            }//if
            else {
                playAgain = false;
            }//else

        }//while
        System.out.println("Thank you for playing Three Bet Roulette");
        pauseAction(SHORTPAUSE);

    }//playGame
}//class