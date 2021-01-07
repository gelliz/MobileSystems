package com.example.Lab8;

import java.util.ArrayList;

public class MockedData {
    public static ArrayList<Contact> getMockedContacts() {
        ArrayList<Contact> mockedContacts = new ArrayList<>();

        Contact firstContact = new Contact();
        firstContact.setId(1);
        firstContact.setName("Vika");
        firstContact.setPhone("+7654321");
        firstContact.setLocation("Mogilev");
        firstContact.setVkLink("victory");

        Contact secondContact = new Contact();
        secondContact.setId(2);
        secondContact.setName("Yana");
        secondContact.setPhone("+9876543");
        secondContact.setLocation("Bobruisk");
        secondContact.setVkLink("4ya");

        Contact thirdContact = new Contact();
        thirdContact.setId(3);
        thirdContact.setName("Max");
        thirdContact.setPhone("+8765432");
        thirdContact.setLocation("Tel-Aviv");
        thirdContact.setVkLink("max123");

        mockedContacts.add(firstContact);
        mockedContacts.add(secondContact);
        mockedContacts.add(thirdContact);

        return mockedContacts;
    }
}
