package com.slimtrade.tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.enums.MessageType;

public class SaveManagerTest {

	@Test
	public void test() {

		/*
		 * Testing the robustness of a JSON based save manager
		 * 
		 * 
		 */

		SaveManager saveManager = new SaveManager();
		saveManager.putObject("String", "key1", "key2");
		saveManager.putObject("value", "key1", "key2", "key3", "key4", "key5", "key6");
		saveManager.putObject(15, "int");
		saveManager.putObject(20, "num");

		saveManager.putObject(true, "boolT");
		saveManager.putObject(false, "boolF");
		
		
		saveManager.putObject(MessageType.CHAT_SCANNER, "enum1");
		saveManager.putObject(Sound.PING1.name(), "enum2");

		String s1 = saveManager.getString("key1", "key2");
		String s2 = saveManager.getString("key1", "key2", "key3", "key4");
		String s3 = saveManager.getStringDefault("Cool", "fakeKey");
		String s4 = saveManager.getString("int");
		String s5 = saveManager.getStringDefault("default", "defaultStringTest");
		String s6 = saveManager.getString("key1", "key2", "key3", "key4", "key5", "key6");

		int i1 = saveManager.getInt("num");

		boolean bt = saveManager.getBool("boolT");
		boolean bf = saveManager.getBool("boolF");
		
		saveManager.saveToDisk();
		
		
		MessageType e1 = MessageType.valueOf(saveManager.getEnumValue(MessageType.class, "enum1"));
		String es2 = saveManager.getEnumValue(Sound.class, "enum2");
		System.out.println("TEST : " + es2);
		Sound e2 = Sound.valueOf(saveManager.getEnumValue(Sound.class, "enum2"));
		System.out.println("ENUM TEST ::: " + e2);

		

		assert (s1 == null);
		assert (s2 == null);
		System.out.println(s3);
		assert (s3.equals("Cool"));
		assert (s4 == null);
		assert (s5.equals("default"));
		assert (s6.equals("value"));

		assert (i1 == 20);

		assert (bt == true);
		assert (bf == false);

	}

}
