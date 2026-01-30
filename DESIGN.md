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

## API Contract Validation

Schema validation is used selectively on read operations where the response
represents a stable contract, rather than on all endpoints indiscriminately.

For responses where the API returns a full booking representation (GET /booking/{id}),
JSON Schema validation is used to assert the structural correctness of the response.

The schema is derived from the provided OpenAPI specification and stored under
`src/test/resources/spec`.

During implementation it was observed that, although `email` and `phone` are required
fields in the booking request, they are not consistently returned by the GET booking
endpoint. For this reason:

- The JSON schema allows these fields to be nullable
- Assertions on `email` and `phone` values are intentionally omitted

This approach reflects observed API behavior and avoids false negatives while still
ensuring strong contract validation.