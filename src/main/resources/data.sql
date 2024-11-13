insert into USER_TABLE (username)
values ('김주형');

-- insert into BALANCE_TABLE (user_id, balance)
-- values (1L, 800000L);

insert into CONCERTITEM_TABLE (concert_id, concert_date)
values (1, DATEADD('DAY', 30, CURRENT_TIMESTAMP()));

insert into SEAT_TABLE (id, seat_no, concert_item_id, price, status, hold_expiry_time)
values (1, 1, 1, 80000, 'EMPTY', DATEADD('DAY', 30, CURRENT_TIMESTAMP));

insert into RESERVATION_TABLE (user_id, seat_id, price, status)
values (1, 1, 80000, 'WAITING_PAYMENT');