# swiss-sign-eval
My submission to the SwissSign evalutation

# Disclaimer(s)

Some things to be aware before reviewing this :

  * This is not exactly how I would build a Cucumber based project with front-end testing using Selenium within a professional setting. My week-end was busy so I had to make compromises regarding time and code quality.
  * The end result is not 100% reliable. This is due to factors that are external to me.
  * The project will stop working by the start of the next month. This is because the API I'm using provides domain names that are valid only until the end of the current month. I could workaround that, but I chose not to.
  * This code actually creates accounts in a production environment. I assume this is okay, but I wanted to insist on that. SwissID now has a lot of accounts which have "Dupont-Swisssign" as a last name.

# How to run it

To run this you need the latest ESR from Firefox, a Java 8 compatible JVM and Maven.

From the root of the project, run:

    mvn clean test

# Some of the difficulties I encountered

  * The gender selection part of the registration form behaves strangely when handled by Selenium. To make it work I had to perform some kind of dance. I can verbally explain it if needed.
  * Performing the test case requires a fresh email. However, most throwaway email solution actually defend themselves against automated users, to avoid being used by malicious bots. So, I had to try different ones, and ended up with a provider that offered an API (this is why you don't see the browser checking mails)
  * Sometimes, SwissID just doesn't recognizes the code I give. I don't know why, and I didn't have time to investigate. The code not being recognized is the main reason the solution is not reliable.
