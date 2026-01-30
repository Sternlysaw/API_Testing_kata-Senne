@booking
Feature: Booking API

  Scenario: Create a booking successfully
    When I create a booking with valid data
    Then the booking is created successfully