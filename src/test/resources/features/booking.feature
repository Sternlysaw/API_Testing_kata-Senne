@booking
Feature: Booking API

  Scenario: Create a booking successfully
    When I create a booking with valid data
    Then the booking is created successfully

  @booking
  Scenario: Retrieve a booking by id
    Given I am authenticated
    And a booking exists
    When I retrieve the booking by id
    Then the booking details are returned correctly