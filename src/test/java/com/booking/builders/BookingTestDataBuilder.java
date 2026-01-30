package com.booking.builders;

import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class BookingTestDataBuilder {

    private int roomid = ThreadLocalRandom.current().nextInt(1, 10);
    private String firstname = "John";
    private String lastname = "Doe";
    private boolean depositpaid = true;
    private BookingDates bookingdates;
    private String email;
    private String phone = "12345678901";

    public BookingTestDataBuilder () {

        LocalDate checkIn = LocalDate.now().plusDays(
                ThreadLocalRandom.current().nextInt(1, 30)
        );
        LocalDate checkOut = checkIn.plusDays(2);

        this.bookingdates = new BookingDates(
                checkIn.toString(),
                checkOut.toString()
        );

        this.email = "john.doe+" + System.currentTimeMillis() + "@test.com";
    }

    public BookingTestDataBuilder withInvalidEmail() {
        this.email = "not-an-email";
        return this;
    }

    public BookingTestDataBuilder withPastDates() {
        LocalDate past = LocalDate.now().minusDays(5);
        this.bookingdates = new BookingDates(
                past.toString(),
                past.plusDays(2).toString()
        );
        return this;
    }
    public BookingTestDataBuilder withRoomId(int roomId) {
        this.roomid = roomId;
        return this;
    }

    public BookingRequest build() {
        return new BookingRequest(
                roomid,
                firstname,
                lastname,
                depositpaid,
                bookingdates,
                email,
                phone
        );
    }
}