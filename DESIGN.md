# API Test Automation – Design Notes

## Overview
This project contains an API test automation framework built with Java, Rest-Assured and Cucumber.
The system under test is an external hotel booking API, which resets frequently and is not under our control.

## Design decisions

### Test data management
- A **Test Data Builder** (`BookingTestDataBuilder`) is used to construct booking payloads in a readable and reusable way.
- The builder supports both valid and invalid data variants (e.g. invalid email, invalid dates).

### Booking creation stability
- A **Factory** (`BookingRequestFactory`) encapsulates the booking creation workflow.
- Because the external API may return `409 Conflict` when a room is already booked, the factory retries booking creation until a valid booking is created.
- This prevents flaky tests while still documenting real API behavior.

### Step definition responsibilities
- Step definitions focus on **behavior** rather than request construction.
- Payload creation and workflow logic are delegated to builders/factories to keep steps clean and expressive.

### Negative and security testing
- The suite includes negative validation scenarios (invalid email, invalid dates).
- Security behavior is validated by asserting `401 Unauthorized` responses when authentication is missing.
- Where the API behaves unexpectedly (e.g. invalid dates being accepted), the test documents the discrepancy without breaking the test suite.

## Notes on external API behavior
- The API under test is external and sometimes unstable.
- Status code assertions are based on observed behavior rather than assumptions.