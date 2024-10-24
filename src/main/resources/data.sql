INSERT INTO QUEUETOKEN_TABLE (token, queue_position, status, expiry_time)
VALUES ('a8098c1a-f86e-11da-bd1a-00112444be1e', 1, 'PROCESSED', DATEADD('DAY', 7, CURRENT_TIMESTAMP));

insert into USER_TABLE (username)
values ('김주형');

insert into BALANCE_TABLE (user_id, balance)
values (1, 0L);

insert into CONCERTITEM_TABLE (concert_id, concert_date)
values (1, DATEADD('DAY', 30, CURRENT_TIMESTAMP));

insert into SEAT_TABLE (id, seat_no, concert_item_id, price, status, hold_expiry_time)
values (1, 1, 1, 80000, 'EMPTY', DATEADD('DAY', 30, CURRENT_TIMESTAMP));

--
-- insert into RESERVATION_TABLE (user_id, seat_id, price, status)
-- values (1, 3, 80000, 'WAITING_PAYMENT');