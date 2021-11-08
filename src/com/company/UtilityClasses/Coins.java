package com.company.UtilityClasses;

public class Coins
{
    private int coinType;
    private int numberOfCoins;

    // Setters of variables
    public void setCoinType(int type)
    {
        this.coinType = type;
    }
    public void setNumberOfCoins(int number)
    {
        this.numberOfCoins = number;
    }

    // Getters of variables
    public int getCoinType()
    {
        return this.coinType;
    }
    public int getNumberOfCoins()
    {
        return this.numberOfCoins;
    }
}
