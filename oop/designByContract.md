# APPENDIX A. 계약에 의한 설계

<br/>

**기존의 문제점**

- 명령과 쿼리를 분리 (Chapter 6) 해도 명령으로 인해 발생하는 부수효과를 명확하게 표현하는 데 한계 존재
  - 구현이 복잡하고 부수효과를 가진 메서드들을 연이어 호출하는 코드를 분석하는 경우, 실행 결과를 예측 어려움
- 명령의 부수효과를 쉽고 명확하게 표현할 수 있는 커뮤니케이션 수단 필요

**👉🏻 설계(Design By Contract, DBC)**

- 실행 가능한 검증 도구로 사용하고 명확한 커뮤니케이션 가능
  - 협력에 필요한 **다양한 제약**과 **부수효과**를 **명시적으로 정의하고 문서화**할 수 있음
  - 계약: 실행 가능 → 구현에 동기화돼 있는지 여부를 런타임에 검증할 수 있음

<br/>

## 01. 협력과 계약

### 부수효과를 명시적으로

**구현 조건:**

1. `IsSatisfied` 메서드를 호출
   - `RecurringSchedule` 의 조건을 만족 여부 확인
2. `Reschedule` 메서드를 호출

- **Interface**
  - 메서드의 순서와 관련된 제약을 설명하기 쉽지 않음
- **Code Contracts**
  - 계약에 의한 설계 라이브러리
  - IsSatisfied 메서드의 실행 결과가 true 일 때만 Reschedule 메서드를 호출할 수 있다는 사실을 명확하게 표현할 수 있음

```java
class Event {
  public bool IsSatisfied(RecurringSchedule schedule) { ...}

  public void Reschedule(RecurringSchedule schedule) {
    Contract.Requires(IsSatisfied(schedule));
    //...
  }
}
```

<br/>

#### 일반 if 문과 대표적인 차이점 👉🏻 문서화

- Code Contracts: 일반 로직과 구분할 수 있도록 제약 조건을 명시적으로 표현
  - 일반적인 정합성 체크 로직은 코드의 구현 내부에 숨겨져 있어 정확하게 파악하기가 쉽지 않음
  - 일반 로직과 조건을 기술한 로직을 구분하기도 쉽지 않음

<br/>

#### 계약

> - 각 계약 당사자는 계약으로부터 <b>이익(benefit)</b>을 기대하고 이익을 얻기 위해 <b>의무(obligation)</b>를 이행한다.
> - 각 계약 당사자의 이익과 의무는 계약서에 **문서화**된다.

👉🏻 **한쪽의 의무가 반대쪽의 권리가 된다**

두 계약 당사자 중 어느 한쪽이라도 계약서에 명시된 내용을 위반한다면 계약은 정상적으로 완료되지 않음

<br/>

**Example.** 집 리모델링을 위해 인테리어 전문가에게 작업을 위탁하고 계약을 체결한다고 가정.

- **본인의 의무**: 인테리어 전문가에게 대금을 지급하는 것 👉🏻 리모델링된 집을 얻는 것
- **인테리어 전문가의 의무**: 집을 리모델링하는 것 👉🏻 대금을 지급받는 것

→ 인테리어 전문가가 계약을 이행하는 구체적인 방식에 대해서는 간섭하지 않는다

<br/>

## 02. 계약에 의한 설계

#### 의도를 드러내는 인터페이스

오퍼레이션의 시그니처만으로도 어느 정도까지는 클라이언트와 서버가 협력을 위해 수행해야 하는 제약조건을 명시 가능

<img src="./image/imageA1.png" />

- 오퍼레이션이 클라이언트에게 어떤 것을 제공하려고 하는지를 충분히 설명할 수 있음
- 의도를 드러내는 인터페이스를 만들면 오퍼레이션의 시그니처만으로도 클라이언트와 서버가 협력을 위해 수행해야 하는 제약조건을 어느 정도 명시 가능

> 계약은 여기서 한걸음 더 나아감.
> 즉, 위의 내용과 더불어 **협력하는 클라이언트는 정상적인 상태를 가진 객체와 협력해야 함**

<img src="./image/imageA2.png" />

- 서버는 자신이 처리할 수 있는 범위의 값들을 클라이언트가 전달할 것이라고 기대함 👉🏻**사전조건(precondition)**
- 클라이언트는 자신이 원하는 값을 서버가 반환할 것이라고 예상 👉🏻 **)**
- 클라이언트는 메시지 전송 전과 후의 서버의 상태가 정상일 것이라고 기대 👉🏻 **불변식(invariant)**

<br/>

- 사전조건: 메서드가 정상적으로 실행되기 위해 만족해야 하는 조건
- 사후조건: 메서드가 실행된 후에 클라이언트에게 보장해야 하는 조건
-

<br/>

### Precondition, 사전조건

:: 메서드가 정상적으로 실행되기 위해 만족해야 하는 조건

- 메서드가 호출되기 위해 만족돼야 하는 조건. 메서드의 요구사항을 명시.
- 사전조건이 만족되지 않을 경우, 메서드가 실행돼서는 안 된다.
- 사전조건을 만족시키는 것은 메서드를 실행하는 클라이언트의 의무다.

_Example._

<table>
<tr><th>메서드에 전달된 인자의 정합성을 체크하기 위해 사용</th></tr>
<tr>
<td>

```cplusplus
public Reservation Reserve(Customer customer, int audienceCount) {
    Contract.Requires(customer != null);
    Contract.Requires(audienceCount >= 1);
    return new Reservation(customer, this,
        calculateFee(audienceCount), audienceCount);
}
```

<small>_사전 조건을 만족시킬 책임은 Reserve 메서드를 호출하는 클라이언트에게 있음_</small>

</td>
</tr>
<tr>
<td>

- customer가 null이 아니어야 하고, audienceCount 의 값은 1 보다 크거나 같음
- 이 조건을 만족시키지 못할 경우 Reserve 메서드는 실행되면 안됨

따라서 이 조건을 메서드의 **사전조건으로 정의**함으로써 **메서드가 잘못된 값을 기반으로 실행되는 것을 방지**할 수 있음

</td>
</tr>
<tr>
<td>

```java
var reservation = screening.Reserve(null, 2);  // ContractException 예외 발생
```

</td>
</tr>
</table>

<br/>

### Postcondition, 사후조건

:: 메서드가 실행된 후에 클라이언트에게 보장해야 하는 조건

- 클라이언트가 사전조건을 만족시켰다면 메서드는 사후조건에 명시된 조건을 만족시켜야 함
- 메서드의 **실행 결과가 올바른지를 검사**하고, **실행 후에 객체가 유효한 상태로 남아 있는지**를 검증
  - 인스턴스 변수의 상태가 올바른지
  - 메서드에 전달된 파라미터의 값이 올바르게 변경됐는지
  - 반환값이 올바른지
- 만약 클라이언트가 사전조건을 만족시켰는데도 사후조건을 만족시키지 못한 경우에는 클라이언트에게 예외를 던져야 함
- 사후조건을 만족시키는 것은 서버의 의무

#### 사후조건을 정의하는 것이 어려운 이유

- 한 메서드 안에서 return 문이 여러 번 나올 경우
  - 모든 return 문마다 결괏값이 올바른지 검증하는 코드를 추가해야 함.
  - _대부분의 라이브러리는 사후조건을 한 번만 기술할 수 있게 지원_
- 실행 전과 실행 후의 값을 비교해야 하는 경우
  - 다른 값으로 변경됐을 수 있기 때문에 비교하기 어려울 수 있음.
  - _대부분의 라이브러리는 실행 전의 값에 접근할 수 있는 간편한 방법을 제공._

<br/>

_Example._

<table>
<tr><th>사후조건</th></tr>
<tr>
<td>

```cplusplus
public Reservation Reserve(Customer customer, int audienceCount) {
        Contract.Requires(customer != null);
        Contract.Requires(audienceCount >= 1);
        Contract.Ensures(Contract.Result<Reservation>() != null);   // add post-condition
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
        }
```

- <small>`Contract.Result<T>`: 실행 결과에 접근할 수 있게 해주는 메서드. 이 메서드는 제너릭 타입으로 메서드의 반환 타입에 대한 정보를 명시할 것을 요구</small>
- <small>`Contract.Ensures`: 사후조건을 정의 메서드</small>

</td>
</tr>
<tr>
<td>

- 반환값인 Reservation 인스턴스가 null 이어서는 안 됨

</td>
</tr>
<tr>
<td>

```cplusplus
public string Middle(string text){
    Contract.Requires(text != null && text.Length >= 2);
    Contract.Ensures(Contract.Result<string>().Length < Contract.OldValue<string>(text).Length);
    text = text.Substring(1, text.Length - 2);
    return text.Trim();
}
```

<small>`Contract.OldValue<T>`: 메서드를 실행할 때 text의 값에 접근할 수 있음.</small>

</td>
</tr>
</table>

<br/>

### Invariant, 불변식

:: 항상 참이라고 보장되는 서버의 조건

- 메서드를 실행하기 전이나 종료된 후에 불변식은 항상 참이어야 함 (실행 중에는 불변식을 만족시키지 못할 수도 있음)
- 사전조건과 사후조건에 추가되는 공통의 조건으로 생각할 수 있음

**특성**

- 불변식은 클래스의 모든 인스턴스가 생성된 후에 만족돼야 함
  - 이것은 클래스에 정의된 모든 생성자는 불변식을 준수해야 한다는 것을 의미.
- 불변식은 클라이언트에 의해 호출 가능한 모든 메서드에 의해 준수돼야 함.
  - 메서드가 실행되는 중에는 객체의 상태가 불안정한 상태로 빠질 수 있기 때문에 불변식을 만족시킬 필요는 없지만 메서드 실행 전과 메서드 종료 후에는 항상 불변식 을 만족하는 상태가 유지돼야 한다

<br/>

#### Code Contracts

- `Contract.Invariant` 메서드를 이용해 불변식을 정의
- `ContractInvariantMethod` 애튜리뷰트가 지정된 메서드를 불변식을 체크해야 하는 모든 지점에 자동으로 추가

```java
public class Screening {
    private Movie movie;
    private int sequence;
    private DateTime whenScreened;

    // [ContractInvariantMethod]: Code Contracts가 적절한 타이밍에 자동으로 메서드를 호출해서 객체가 불변식을 유지하고 있는지를 검증

    private void Invariant() {
        Contract.Invariant(movie != null);
        Contract.Invariant(sequence >= 1);
        Contract.Invariant(whenScreened > DateTime.Now);
    }
}
```

<small>≪Thinking in Java≫ 에서 Bruce Ekel은 특별한 라이브러리를 사용하지 않고, 계약에 의한 설계 개념을 설명하기 위해 자바의 assert를 이용해 불변식을 체크하는 코드를 모든 메서드에서 직접 호출하는 예제를 소개</small>

<br/>

## 03. 계약에 의한 설계와 서브타이핑

- **리스코프 치환 원칙**: 슈퍼타입의 인스턴스와 협력하는 클라이언트의 관점에서, 서브타입의 인스턴스가 슈퍼타입을 대체하더라도 협력에 지장이 없어야 한다는 것을 의미

> 서브타입이 리스코프 치환 원칙을 만족시키기 위해서는 클라이언트와 슈퍼타입 간에 **체결된 계약을 준수**해야 한다.

<br/>

**리스코프 치환 원칙을 세분화한 두 가지 규칙**

1. **계약 규칙**

   - : 슈퍼타입과 서브타입 사이의 사전조건, 사후조건, 불변식에 대해 서술할 수 있는 제약에 관한 규칙
   - 서브타입에 더 강력한 사전조건을 정의할 수 없음
   - 서브타입에 더 완화된 사후조건을 정의할 수 없음
   - 슈퍼타입의 불변식은 서브타입에서도 반드시 유지돼야 함

2. **가변성 규칙**
   - : 파라미터와 리턴 타입의 변형과 관련된 규칙
   - 서브타입의 메서드 파라미터는 반공변성을 가져야 함
   - 서브타입의 리턴 타입은 공변성을 가져야 함
   - 서브타입은 슈퍼타입이 발생시키는 예외와 다른 타입의 예외를 발생시켜서는 안됨

<small>공변성과 반공변성이 중요해지는 곳은 상속이 제네릭 프로그래밍과 만나는 지점</small>

<br/>

- `C#`의 Code Contracts와 같은 라이브러리를 사용하는 대신 자바에서 기본적으 로 제공하는 단정문인 assert를 사용해 사전조건, 사후조건, 불변식을 직접 구현
- 계약에 의한 설계를 위한 라이브러리가 없거나 언어에서 지원하지 않아도 계약에 의한 설계를 적용하는 것은 가능

<br/>

### 계약 규칙

<img src="./image/imageA3.png" />

위의 이 클래스들은 리스코프 치환 원칙을 만족하는가?

```java
public interface RatePolicy {
    Money calculateFee(List<Call> calls);
}
```

```java
public class Bill {
    private Phone phone;
    private Money fee;

    public Bill(Phone phone, Money fee) {
        if (phone == null) {
            throw new IllegalArgumentException();
        }

        if (fee.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }

        this.phone = phone;
        this.fee = fee;
    }
}
```

```java
public class Phone {
    private RatePolicy ratePolicy;
    private List<Call> calls = new ArrayList<>();

    public Phone(RatePolicy ratePolicy) {
        this.ratePolicy = ratePolicy;
    }

    public void call(Call call) {
        calls.add(call);
    }

    /**
     * 가입자에게 청구할 요금을 담은 Bill 인스턴스를 생성한 후 반환
    */
    public Bill publishBill() {
        return new Bill(this, ratePolicy.calculateFee(calls));
    }
}
```

- `**publishBill**`: `calculateFee` 의 반환값을 Bill 생성자에 전달.
  - 사후 조건: 청구서의 요금은 최소한 0 원보다 크거나 같아야 하므로 `calculateFee`의 반환값은 0 원보다 커야 함
  - `assert result.isGreaterThanOrEqual(Money.ZERO);`
- `**calculateFee**`: 파라미터로 전달된 Call 목록에 대한 요금의 총합을 계산.
  - 사전 조건: 파라미터인 calls 가 null 이 아니어야 함
  - `assert calls != null;`

<br/>

**BasicRatePolicy 와 AdditionalRatePolicy 를 RatePolicy 의 서브타입으로 제작**

#### BasicRatePolicy - 기본 정책 구현

```java
public abstract class BasicRatePolicy implements RatePolicy {

    @Override
    public Money calculateFee(List<Call> calls) {
        assert calls != null; // 사전조건

        Money result = Money.ZERO;

        for(Call call : calls) {
            result.plus(calculateCallFee(call));
        }

        assert result.isGreaterThanOrEqual(Money.ZERO); // 사후조건
        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}
```

<br/>

#### AdditionalRatePolicy - 부가 정책 구현

```java
public abstract class AdditionalRatePolicy implements RatePolicy {
    private RatePolicy next;

    public AdditionalRatePolicy(RatePolicy next) {
        this.next = next;
    }

    @Override
    public Money calculateFee(List<Call> calls) {
        assert calls != null; // 사전조건

        Money fee = next.calculateFee(calls);
        Money result = afterCalculated(fee);

        assert result.isGreaterThanOrEqual(Money.ZERO);  // 사후조건
        return result;
    }

    abstract protected Money afterCalculated(Money fee);
}
```

### 계약 규칙과 가변성 규칙

#### ✔️ 서브타입에 더 강력한 사전조건을 정의할 수 없다

_한 번도 통화가 발생하지 않은 Phone에 대한 청구서를 발행하는 시나리오_

```java
Phone phone = new Phone(new RegularPolicy(Money.wons(100), Duration.ofSeconds(10)));
Bill bill = phone.publishBill();
```

<table>
<tr><th>더 강력한 사전 조건</th><th>더 약한 사전 조건</th></tr>
<tr><td>

```java
public abstract class BasicRatePolicy implements RatePolicy {

    @Override
    public Money calculateFee(List<Call> calls) {
        // 사전조건
        assert calls != null;
        assert !calls.isEmpty();  // calls가 빈 리스트여서는 안 된다는 사전조건 추가
        // ...
    }
}
```

- BasicRatePolicy는 사전조건에 새로운 조건을 추가함으로써 Phone과 RatePolicy 사이에 맺은 계약을 위반.
  - Phone의 입장에서 더 이상 RatePolicy 와 BasicRatePolicy는 동일하지 않음
- 따라서, 클라이언트의 관점에서 BasicRatePolicy 는 RatePolicy 를 대체할 수 없기 때문에 **리스코프 치환 원칙을 위반**

</td><td>

```java
public abstract class BasicRatePolicy implements RatePolicy {

    @Override
    public Money calculateFee(List<Call> calls) {
        if (calls == null) {
            return Money.ZERO;
        }
        // ...
    }
}
```

- 사전조건을 보장해야 하는 책임은 클라이언트
  - 이미 클라이언트인 Phone은 인자가 null 이 아닌 값을 전달하도록 보장
- 즉, 사전조건을 완화시키는 것 은 리스코프 치환 원칙을 위반하지 않음

</td></tr>
</table>

<br/>

#### ✔️ 서브타입에 더 완화된 사후조건을 정의할 수 없다

_RatePolicy 의 calculateFee 오퍼레이션의 반환값이 0 원보다 작은 경우의 시나리오_

```java
/**
 * - 일반 요금제(RegularPolicy): 10 초당 100 원 부과
 * - 기본 요금 할인 정책(RateDiscountablePolicy): 1000원 할인
 **/
Phone phone = new Phone(
    new RateDiscountablePolicy(Money.wons(1000),
        new RegularPolicy(Money.wons(100), Duration.ofSeconds(10))));

/**
 * 통화 시간 1 분 → 통화 요금 600 원
 **/
phone.call(new Call(LocalDateTime.of(2017, 1, 1, 10, 10),
    LocalDateTime.of(2017, 1, 1, 10, 11)));
Bill bill = phone.publishBill();
```

- calculateFee 오퍼레이션은 반환값이 0원보다 커야 한다는 사후조건을 정의
- 사후조건 을 만족시킬 책임은 클라이언트가 아니라 서버에 있음

<br/>

<table>
<tr><th>완화된 사후 조건</th><th>강화된 사후 조건</th></tr>
<tr><td>

```java
public abstract class AdditionalRatePolicy implements RatePolicy {

    @Override
    public Money calculateFee(List<Call> calls) {
        assert calls != null;

        Money fee = next.calculateFee(calls);
        Money result = calculate(fee);

        // 사후조건
        // assert result.isGreaterThanOrEqual(Money.ZERO);

        return result;
    }

    abstract protected Money calculate(Money fee);
}
```

- Phone과의 협력은 문제없이 처리 가능
  - AdditionalRatePolicy는 마이너스 금액도 반환할 수 있기 때문
- Bill의 생성자에서 예외 발생
  - fee가 마이너스 금액일 경우 예외를 던지도록 구현돼 있기 때문 (아래 코드 참고)

```java
public class Bill {
    public Bill(Phone phone, Money fee) {
        if (fee.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }
        // ...
    }
}
```

- 오류는 Bill의 생성자 문제라고 하지만, 실제적으론 Bill의 문제가 아님
  - Bill의 입장에서 요금이 0 원보다 크거나 같다고 가정하는 것은 자연스러움.
- 문제는 기존에 Phone과 RatePolicy 사이에 체결된 계약을 위반했기 때문에 발생한 것
  - AdditionalRatePolicy 가 사후조건을 완화했기 때문에 발생
- 클라이언트인 Phone 의 입장에서 AdditionalRatePolicy 는 더 이상 RatePolicy의 서브타입이 아님

</td><td>

```java
public abstract class AdditionalRatePolicy implements RatePolicy {

    @Override
    public Money calculateFee(List<Call> calls) {
        // ...
        // 사후조건
        assert result.isGreaterThanOrEqual(Money.wons(100));
        return result;
    }
    abstract protected Money calculate(Money fee);
}
```

- Phone은 반환된 요금이 0 원보다 크기만 하다면 아무런 불만도 가지지 않기 때문에 위 변경은 클라이언 트에게 아무런 영향도 미치지 않음
- 즉, 사후조건 강화는 계약에 영향을 미치지 않음

</td></tr>
</table>

<br/>
