package com.jgo.swisssigneval.stepdef;

import com.jgo.swisssigneval.mailprovider.MailProvider;
import io.cucumber.java.en.Given;

public class EmailStepdefs {
    public static MailProvider mailProvider;

    @Given("I am an unregistered user with a new email address")
    public void fetch_email_address() {
        MailProvider mailProvider = MailProvider.initNewAccount();

        EmailStepdefs.mailProvider = mailProvider;
    }
}
