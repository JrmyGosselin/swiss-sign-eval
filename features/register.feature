Feature: Register user

    Scenario: Register to Swiss Post using SwissID then log out
        Given I am an unregistered user with a new email address
        When I access the SwissPost main page
        And I click on the login button
        And I click on the SwissID login button
        And I register a new SwissID account for the unregistered account
        And I complete registration as a private user with the provided code received via email
        And I log out
        Then I am successfully logged out

