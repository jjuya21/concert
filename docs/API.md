# BalanceController

## 1. 잔액 조회 API
- **URL**: `/api/balance/{userId}`
- **Method**: `GET`
- **Description**: 사용자의 잔액을 조회합니다.

### 요청(Request)
- **Path Parameter**:
    - `userId` (long): 잔액을 조회할 사용자의 ID.

```http
GET /api/balance/1 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "userId": 1,
    "balance": 100000
  }
}
```

## 2. 잔액 충전 API
- **URL**: `/api/balance/{userId}`
- **Method**: `POST`
- **Description**: 사용자의 잔액을 충전합니다.

### 요청(Request)
- **Path Parameter**:
  - `userId` (long): 잔액을 조회할 사용자의 ID.
- **Query Parameter**:
  - `amount` (int): 충전할 금액.

```http
POST /api/balance/1?amount=50000 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "userId": 1,
    "balance": 100000
  }
}
```

# ConcertItemController

## 1. 공연 가능한 날짜 조회 API
- **URL**: `/api/concert-item/{concertId}/available-dates`
- **Method**: `GET`
- **Description**: 공연 ID에 따른 가능한 공연 날짜를 조회합니다.

### 요청(Request)
- **Path Parameter**:
    - `concertId` (long): 조회할 공연의 ID.

```http
GET /api/concert-item/1/available-dates HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": [
    {
      "concertId": 1,
      "availableDate": "2024-10-15"
    },
    {
      "concertId": 2,
      "availableDate": "2024-10-16"
    }
  ]
}
```

# PaymentController

## 1. 결제 내역 조회 API
- **URL**: `/api/payment/{userId}`
- **Method**: `GET`
- **Description**: 특정 사용자의 결제 내역을 조회합니다.

### 요청(Request)
- **Path Parameter**:
    - `userId` (long): 결제 내역을 조회할 사용자의 ID.

```http
GET /api/payment/1 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": [
    {
      "paymentId": 1,
      "productId": 2,
      "amount": 80000,
      "paymentDate": "2024-12-21"
    },
    {
      "paymentId": 2,
      "productId": 3,
      "amount": 80000,
      "paymentDate": "2024-12-21"
    }
  ]
}
```

# QueueTokenController

## 1. 대기열 토큰 생성 API
- **URL**: `/api/queue-token/{userId}`
- **Method**: `POST`
- **Description**: 특정 사용자를 위한 대기열 토큰을 생성합니다.

### 요청(Request)
- **Path Parameter**:
  - `userId` (long): 대기열 토큰을 생성할 사용자의 ID.

```http
POST /api/queue-token/1 HTTP/1.1
Content-Type: application/json

```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "queueToken": "afjewfafew.bsgrevearea.esarafaf"
  }
}
```

## 2. 대기열 토큰 검증 API
- **URL**: `/api/queue-token/{tokenId}`
- **Method**: `GET`
- **Description**: 특정 대기열 토큰의 유효성을 검증합니다.

### 요청(Request)
- **Path Parameter**:
  - `tokenId` (long): 검증할 토큰의 ID.

```http
GET /api/queue-token/1 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "queueToken": "afjewfafew.grafafjnkafue.esarafaf"
  }
}
```

## 3. 대기열 위치 조회 API (스케줄러 동작)
- **URL**: `/api/queue-token/{userId}`
- **Method**: `GET`
- **Description**: 특정 대기열 토큰의 유효성을 검증합니다.

### 요청(Request)
- **Path Parameter**:
  - `userId` (long): 대기열 위치를 조회할 사용자의 ID.

```http
GET /api/queue-token/1 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "queueToken": "afjewfafew.fjwieaoafkbykr.esarafaf"
  }
}
```

# ReservationController

## 1. 예약 생성 API
- **URL**: `/api/reservation`
- **Method**: `POST`
- **Description**: 특정 사용자를 위한 대기열 토큰을 생성합니다.

### 요청(Request)
- **Path Parameter**:
  - `userId` (long): 예약을 생성할 사용자의 ID.
- **Query Parameter**:
  - `tokenId` (long): 예약할 좌석의 ID.

```http
POST /api/1/reservation?seatId=2 HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": {
    "reservationId": 1,
    "userId": 2,
    "seatId": 4,
    "reservationDate": "2024-01-01",
    "status": "payed"
  }
}
```

# SeatController

## 1. 좌석 조회 API
- **URL**: `/api/seat/{concertItemId}/available-seats`
- **Method**: `GET`
- **Description**: 특정 공연 아이템에 대해 사용 가능한 좌석 목록을 조회합니다.

### 요청(Request)
- **Path Parameter**:
  - `concertItemId` (long): 좌석을 조회할 공연 아이템의 ID.

```http
GET /api/seat/1/available-seats HTTP/1.1
Content-Type: application/json
```

### 응답(Response)
- **Status Code: 200 OK**
- **Response Body**:s

```json
{
  "code": 200,
  "message": "OK",
  "debug": null,
  "data": [
    {
      "seatId": 1,
      "seatNumber": 43,
      "price": 80000,
      "status": "available"
    },
    {
      "seatId": 2,
      "seatNumber": 44,
      "price": 80000,
      "status": "available"
    }
  ]
}
```