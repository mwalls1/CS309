package com.mygdx.Cards;

public class CardTest {
public static void main(String[] args)
{
	Deck deck = new Deck();
	
	for (int i = 0; i<52; i++) System.out.println(deck.getCard(i).toString());


}

}
