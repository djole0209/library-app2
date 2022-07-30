Feature: Library application login feature
  User story: As a user, I should be able to login with correct credentials to different accounts.
  and dashboard should be displayed.

  Accounts are: librarian, student, user


  @librarian
  Scenario: As a librarian, I should be able to login to LibraryCT.
    Given librarian is on the loginPage
    Then verify that the title is "Login - Library"
    When librarian enters valid email address and password
    And librarian click sign in button
    Then verify that there are 3 models on the page

    @student
    Scenario: As a student, I should be able to login to LibraryCT.
      Given student is on the loginPage
      Then verify that the URL is "https://library2.cydeo.com/login.html"
      When student enters valid email address and password
      And student click sign in button
      Then verify that are 2 models on the page

      @user
      Scenario: As a user, I should be able to login to LibraryCT.
        Given user is on the loginPage
        When user enters invalid email address or password
        And user click sign in button
        Then verify the error message "Sorry, Wrong Email or Password"

