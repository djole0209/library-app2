Feature: Librarian is adding new user

  @librarianAddUser
  Scenario: As a librarian, I should be able to add a new user
    Given librarian is on the homePage
    When librarian click Users module
    And librarian click "+Add User" button
    When librarian enter full name, password, email and address
    And librarian click save changes
    Then verify a new user is created
