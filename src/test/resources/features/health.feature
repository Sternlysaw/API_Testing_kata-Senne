@health
Feature: API health check

  Scenario: API is up and running
    When I check the API health
    Then the API status should be UP