package constants;

import models.Contact;

import java.util.ArrayList;

public class Constants {
    public static int LinearType = 1;
    public static int GridType = 2;

    public static ArrayList<Contact> getMockedData() {
        ArrayList<Contact> mockedContacts = new ArrayList<>();

        Contact firstContact = new Contact();
        firstContact.setId(1);
        firstContact.setName("Liza");
        firstContact.setPhone("+9876543");
        firstContact.setLocation("Minsk");
        firstContact.setVkLink("liza");

        Contact secondContact = new Contact();
        secondContact.setId(2);
        secondContact.setName("Yarik");
        secondContact.setPhone("+7654321");
        secondContact.setLocation("Mogilev");
        secondContact.setVkLink("yar");

        Contact thirdContact = new Contact();
        thirdContact.setId(3);
        thirdContact.setName("Yana");
        thirdContact.setPhone("+1234567");
        thirdContact.setLocation("Bobruisk");
        thirdContact.setVkLink("yana");

        mockedContacts.add(firstContact);
        mockedContacts.add(secondContact);
        mockedContacts.add(thirdContact);

        return mockedContacts;
    }
}
