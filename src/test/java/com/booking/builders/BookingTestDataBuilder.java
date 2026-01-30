package com.booking.builders;

import com.booking.models.BookingRequest;
import com.booking.models.BookingDates;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class BookingTestDataBuilder {
    public static BookingRequest validBooking() {
        LocalDate checkin = LocalDate.now()
                .plusDays(ThreadLocalRandom.current().nextInt(5, 30));
        LocalDate checkout = checkin.plusDays(2);

        return new BookingRequest(
                ThreadLocalRandom.current().nextInt(1, 6),
                "John",
                "Doe",
                true,
                new BookingDates(
                        checkin.toString(),
                        checkout.toString()
                ),
                "john.doe@test.com",
                "12345678901"
        );
    }
}
