package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvSources;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingSystemTest {

    private BookingSystem bookingSystem;
    private RoomRepository roomRepository;
    private TimeProvider timeProvider;
    private NotificationService notificationService;


    @BeforeEach
    public void setUp() {
        roomRepository = mock(RoomRepository.class);
        timeProvider = mock(TimeProvider.class);
        notificationService = mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());}


    @ParameterizedTest
    //Values för vårat parameterizerade test
    @CsvSource({
            "null,2025-12-01T10:00,Exception",
            "2025-12-01T09:00,null,Exception",
            "2025-12-01T09:00,2025-12-01T08:00,Exception"
    })
    void testNullStartTimeOrEndTime(String start, String end, String excpectedResult) {
        //Parameterizedtest för att kasta ett exception om starttid eller sluttid är null
        LocalDateTime startTime = start.equals("null") ? null : LocalDateTime.parse(start);
        LocalDateTime endTime = end.equals("null") ? null : LocalDateTime.parse(end);
        if (excpectedResult.equals("Exception")) {
            assertThrows(IllegalArgumentException.class,
                    () -> bookingSystem.bookRoom("room1", startTime, endTime));
        }
    }

    @Test
    void testStartTimeInThePast(){
        //Testar så att ett exception kastas om starttiden är i dåtid
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.bookRoom("Room1",
                LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(2)));
        }

    @Test
    void testEndTimeBeforeStartTime(){
        //Testar så att ett exception kastas när sluttid är före starttid
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.bookRoom("Room1", LocalDateTime.now(),
                LocalDateTime.now().minusHours(1)));
    }

    @Test
    void testNonExistingRoomId(){
        //Testar så att ett exception kastas när man bokar ett rum som inte finns
        when(roomRepository.findById("room1")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.bookRoom("room1", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)));
    }

    @Test
    void testRoomNotAvailable(){
        //Testar att metoden returnerar false när rummet är upptaget
        Room room = mock(Room.class);
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(room.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        assertThat(bookingSystem.bookRoom("Room1", LocalDateTime.now(), LocalDateTime.now().plusHours(2))).isFalse();
    }

    @Test
    void testRoomAvailable(){
        //Testar så att metoden returnerar true när rummet är ledigt
        Room room = mock(Room.class);
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(room.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);

        try {
            doNothing().when(notificationService).sendBookingConfirmation(any(Booking.class));
        } catch (NotificationException e) {
            throw new RuntimeException(e);
        }

        assertTrue(bookingSystem.bookRoom("Room1", LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)));

        verify(room).addBooking(any(Booking.class));
        verify(roomRepository).save(room);
    }

}