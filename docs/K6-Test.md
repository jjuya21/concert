# 부하 테스트 실행 및 장애 시뮬레이션 분석 보고서

## **STEP 19: 부하 테스트 계획 및 수행**

### **1. 개요**

부하 테스트는 예상되는 최대 부하를 견딜 수 있는지 테스트하고 개선하는 것이다. 이를 통해 장애 발생 시 효과적인 대응 방안을 마련하고, 시스템의 안정성 및 신뢰성을 높일 수 있다.

### **2. 부하 테스트 대상 선정**

#### 대상

- **콘서트 예약**
    - 높은 중요도: 콘서트 서비스의 핵심 기능으로써, 비즈니스적 성공과 가장 밀접한 연관이 있으므로 높은 중요도를 가지고 있다.
    - 복잡한 트랜잭션: 콘서트, 예약, 결제 등 복잡한 트랜잭션이 연속적으로 이루어져 있고 이 과정 중 데이터의 읽기, 쓰기 작업이 빈번하게 발생한다. 복잡한 프로세스가 부하 상황에서도 원활하게 수행되어야
      한다.
    - 높은 트래픽 처리 필요: 인기 콘서트의 경우 수많은 사용자가 동시에 시스템에 접근하게 되므로, 성능 저하 및 시스템 장애 등의 위험을 방지하기 위해 트래픽 관리가 중요하다.
- **대기열 참여**
    - 시스템 자원 관리: 서버 부하 조절 및 자원 관리를 위해 필수적인 기능이므로, 시스템의 안정성을 위한 평가가 필요하다.
    - 동시 사용자 처리: 다수의 사용자가 동시에 서비스를 접근할 때 안정성을 유지하기 위한 메커니즘을 갖고 있으므로, 사용자들의 공정성 및 안정성을 확인해야 한다.

두 기능 모두 사용자 경험 및 시스템 안정성과 직접적인 관계를 갖고 있으므로, 실사용 환경에서 안정적으로 작동하는지 평가하고 최적화를 시켜야 한다.

### **3. 테스트 시나리오 실행 결과**

#### 시나리오 1

**사용자 50명이 토큰 생성, 좌석조회 후 좌석 예매, 결제**

| **항목**                                      | **값**                                                                        |
|---------------------------------------------|------------------------------------------------------------------------------|
| **체크 (checks)**                             | 36.37% (836 / 2298)                                                          |
| **수신된 데이터 (data_received)**                 | 1.8 MB (5.4 kB/s)                                                            |
| **전송된 데이터 (data_sent)**                     | 436 kB (1.3 kB/s)                                                            |
| **요청 차단 시간 (http_req_blocked)**             | 평균=344.41µs, 최소=1µs, 중앙값=190.5µs, 최대=4.66ms, p(90)=913.4µs, p(95)=1.21ms     |
| **연결 시간 (http_req_connecting)**             | 평균=304.21µs, 최소=0s, 중앙값=160µs, 최대=4.63ms, p(90)=806.5µs, p(95)=1.03ms        |
| **요청 지속 시간 (http_req_duration)**            | 평균=14.27ms, 최소=628µs, 중앙값=12.48ms, 최대=141.32ms, p(90)=28.94ms, p(95)=35.98ms |
| **요청 지속 시간 (응답 기대값=true)**                  | 평균=16.07ms, 최소=693µs, 중앙값=14.83ms, 최대=141.32ms, p(90)=35.97ms, p(95)=41.34ms |
| **실패한 요청 비율 (http_req_failed)**             | 63.70% (1404 / 2204)                                                         |
| **데이터 수신 시간 (http_req_receiving)**          | 평균=92.83µs, 최소=10µs, 중앙값=38µs, 최대=12.2ms, p(90)=172µs, p(95)=263µs           |
| **데이터 전송 시간 (http_req_sending)**            | 평균=31.4µs, 최소=3µs, 중앙값=16µs, 최대=2.02ms, p(90)=52µs, p(95)=75µs               |
| **TLS 핸드셰이크 시간 (http_req_tls_handshaking)** | 평균=0s, 최소=0s, 중앙값=0s, 최대=0s, p(90)=0s, p(95)=0s                              |
| **대기 시간 (http_req_waiting)**                | 평균=14.15ms, 최소=604µs, 중앙값=12.34ms, 최대=141.15ms, p(90)=28.79ms, p(95)=35.84ms |
| **총 요청 수 (http_reqs)**                      | 2204 (6.678749/s)                                                            |
| **반복 작업 시간 (iteration_duration)**           | 평균=46.5s, 최소=11.1s, 중앙값=44.1s, 최대=55.12s, p(90)=55.09s, p(95)=55.1s          |
| **반복 작업 수 (iterations)**                    | 320 (0.969691/s)                                                             |
| **가상 사용자 수 (vus)**                          | 50                                                                           |

- HTTP 요청 실패율(http_req_failed)
    - 실패율 30.00%로 모든 요청이 성공적으로 수행되었다.
    - 좌석 중복, 조회 실패로
- HTTP 요청 시간(http_req_duration)
    - 평균 14.27ms, 90%의 요청이 28.94ms 이내에 완료되었다.

#### 시나리오 2

**사용자 100명이 토큰 6개씩 생성**

| **항목**                                      | **값**                                                                         |
|---------------------------------------------|-------------------------------------------------------------------------------|
| **체크 (checks)**                             | 100.00% (600 / 600)                                                           |
| **수신된 데이터 (data_received)**                 | 134 kB (20 kB/s)                                                              |
| **전송된 데이터 (data_sent)**                     | 105 kB (15 kB/s)                                                              |
| **요청 차단 시간 (http_req_blocked)**             | 평균=248.12µs, 최소=1µs, 중앙값=4µs, 최대=2.75ms, p(90)=1.39ms, p(95)=1.64ms           |
| **연결 시간 (http_req_connecting)**             | 평균=201.91µs, 최소=0s, 중앙값=0s, 최대=1.65ms, p(90)=1.33ms, p(95)=1.49ms             |
| **요청 지속 시간 (http_req_duration)**            | 평균=39.24ms, 최소=2.81ms, 중앙값=21.55ms, 최대=162.9ms, p(90)=143.07ms, p(95)=154.5ms |
| **실패한 요청 비율 (http_req_failed)**             | 0.00% (0 / 600)                                                               |
| **데이터 수신 시간 (http_req_receiving)**          | 평균=227.44µs, 최소=10µs, 중앙값=118.5µs, 최대=7.94ms, p(90)=312µs, p(95)=449.14µs     |
| **데이터 전송 시간 (http_req_sending)**            | 평균=16.97µs, 최소=3µs, 중앙값=10µs, 최대=532µs, p(90)=30.1µs, p(95)=47.24µs           |
| **TLS 핸드셰이크 시간 (http_req_tls_handshaking)** | 평균=0s, 최소=0s, 중앙값=0s, 최대=0s, p(90)=0s, p(95)=0s                               |
| **대기 시간 (http_req_waiting)**                | 평균=39ms, 최소=2.77ms, 중앙값=21.31ms, 최대=162.46ms, p(90)=142.9ms, p(95)=154.3ms    |
| **총 요청 수 (http_reqs)**                      | 600 (102.16714/s)                                                             |
| **반복 작업 시간 (iteration_duration)**           | 평균=1.04s, 최소=1s, 중앙값=1.02s, 최대=1.16s, p(90)=1.14s, p(95)=1.15s                |
| **반복 작업 수 (iterations)**                    | 600 (87.571834/s)                                                             |
| **가상 사용자 수 (vus)**                          | 100                                                                           |

- HTTP 요청 실패율(http_req_failed)
    - 실패율 0.00%로 모든 요청이 성공적으로 수행되었다.
- HTTP 요청 시간(http_req_duration)
    - 평균 39.24ms, 90%의 요청이 143.07ms 이내에 완료되었다.

## STEP 20: 성능 분석 및 개선

### **4. 테스트 탐색 및 개선**

시나리오2에서 평균 응답 시간은 39.24ms였다.
토큰 발급 로직치고 너무 오래걸린다고 판단했다.

#### 기존 코드: 토큰 생성 -> 큐에 삽입 -> 반환

```java

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueToken createToken() {

        String token = UUID.randomUUID().toString();

        queueTokenRepository.enqueue(token);

        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        return queueToken;
    }
}
```

#### 수정 코드: 토큰 생성 -> 큐에 삽입 / 반환

```java

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueToken createToken() {

        String token = UUID.randomUUID().toString();

        // 비동기 메서드 호출
        enqueueTokenAsync(token);

        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        return queueToken;
    }

    @Async
    protected void enqueueTokenAsync(String token) {
        queueTokenRepository.enqueue(token);
    }
}
```

기존 큐에 삽입하던 부분을 비동기 처리로 수정

응답속도를 39.24m에서 23.89ms까지 단축

## 회고

단순하게 접근하여 단순한 성능 개선만 시도해 보았다.

조금 더 공부하여 여러 부분들을 모니터링해서 시각화하고 싶었졌다.
(DB, Kafka, Redis)

9주간 진행하면서 단위 테스트, 통합 테스트만 하다가 새로운 영역을 배운거 같아 뿌듯합니다.

코치님 10주간 수고하셨습니다.