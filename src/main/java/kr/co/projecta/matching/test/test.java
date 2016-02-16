package kr.co.projecta.matching.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.SupportedSourceVersion;

public class test {
	
	Card card;
	String number;
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public test(Card card) {
		super();
		this.card = card;
	}

	@Override
	public String toString() {
		return "test [card=" + card + "] "+number;
	}

	static class Card {
		String id;
		String name;
		public Card(String id, String name) {
			this.id = id;
			this.name = name;
		}
		@Override
		public boolean equals(Object obj) {
			System.out.println("call equals");
			// TODO Auto-generated method stub
			return super.equals(obj);
		}
		@Override
		public String toString() {
			return "Card [id=" + id + ", name=" + name + "]";
		}
	}

	public static void main(String[] args) {
		/*
		String id = "id";
		String name = "name";
		String number = "number";
		
		Card card = new Card(id, name);
		System.out.println(card);
		
		test t = new test(card);
		t.setNumber(number);
		System.out.println(t);
		
		card.id = "1";
		card.name = "2";
		
		System.out.println(t);
		*/
		
		/*
		List<Card> cards = new ArrayList<>();
		
		Card c1 = new Card("1", "1");
		Card c2 = new Card("2", "2");
		
		cards.add(c1);
		cards.add(c2);
		
		System.out.println("[1]"+c1);
		System.out.println("[1]"+c2);
		
		for (Card c : cards) {
			System.out.println("[2]"+c);
			c.id = "100";
			c.name = "100";
		}
		
		System.out.println("[3]"+c1);
		System.out.println("[3]"+c2);		
		
		c1.id = "99"; c1.name = "99";
		
		for (Card c : cards) {
			System.out.println("[4]"+c);
		}
		*/
		
		Map<String, Card> map = new HashMap<>();
		Card c1 = new Card("1", "1");
		Card c2 = new Card("2", "2");
		
		map.put("1", c1);
		map.put("2", c2);
		
		for (String key : map.keySet()) {
			System.out.println(map.get(key));
		}
		Card c3 = new Card("1","1");
		System.out.println(map.containsValue(c3));
		
		
	}

}
