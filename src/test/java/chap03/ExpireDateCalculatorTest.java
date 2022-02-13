package chap03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * 매달 지불해야하는 유료 서비스
 *
 * 1. 서비스를 사용하려면 매달 1만원을 선불로 납부, 납부일 기준으로 한 달 뒤가 서비스만료이
 * 2. 2개월 이상 요금 납부 가능
 * 3. 10만원을 납부하면 서비스를 1년 제공
 *
 * 테스트 기준
 * 구현이 쉬운것 부터 테스트
 * 예외 상황을 테스트
 *
 * 일반적으로 중복을 제거하는게 좋지만, 테스트 코드의 경우 명확하게 설명해 줘야하기 때문에 고민해볼 필요 있음
 *
 *
 * 1. TDD시에는 테스트할 내용들을 정리하는것이 좋다.
 * 2. 가장 쉬운테스트 (즉 제약조건이 적은 테스트) 순으로 TDD진행
 * 3. 새로운 테스트 발견시 꼭 추가
 * 지라, 트렐로 활용
 * 4. 테스르 코드를 한번에 작성하게 되면 리팩토링이 들어 진다.
 * 그러므로 테스트 코드 작성 -> 테스트 -> 리팩토링순으로 개발
 * 코드가 많아지면 심리적으로 리팩토링이 힘들어 진다. 즉, 개발 시간이 더 오래 걸릴 수 있다.
 *
*/
public class ExpireDateCalculatorTest {

    private void assertExpireDate(PayData payData, LocalDate expireExpectedDate){
        ExpireDateCalculator calculator = new ExpireDateCalculator();
        LocalDate expireDate = calculator.calculatorExpireDate(payData);
        Assertions.assertEquals(expireDate, expireExpectedDate);
    }

    @Test
    @DisplayName("만원을 납부하면 만료일은 한달뒤")
    void pay_one_then_expire_one(){

        assertExpireDate(PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(10000).build()
                ,LocalDate.of(2019, 4, 1));
        assertExpireDate(PayData.builder()
                .billingDate(LocalDate.of(2019, 5, 5))
                .payAmount(10000).build(), LocalDate.of(2019, 6, 5));
    }

    @Test
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음")
    void pay_one_expire_exception(){

        assertExpireDate(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(10000).build()
                ,LocalDate.of(2019, 2, 28));

        assertExpireDate(PayData.builder()
                        .billingDate(LocalDate.of(2019, 5, 31))
                        .payAmount(10000).build()
                ,LocalDate.of(2019, 6, 30));

        assertExpireDate(PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10000).build()
                ,LocalDate.of(2020, 2, 29));
    }

    @Test
    void 첫_납부과_만료일_일자가_다를때_만원_납부(){
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10000).build();

        assertExpireDate(payData, LocalDate.of(2019,3,31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 30))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10000).build();

        assertExpireDate(payData2, LocalDate.of(2019,3,30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 5, 31))
                .billingDate(LocalDate.of(2019, 6, 30))
                .payAmount(10000).build();

        assertExpireDate(payData3, LocalDate.of(2019,7,31));

        PayData payData4 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 2, 28))
                .billingDate(LocalDate.of(2019, 3, 28))
                .payAmount(10000).build();

        assertExpireDate(payData4, LocalDate.of(2019,4,28));
    }

    @Test
    void 이만원이상_납부하면_비례해서_만료일_계산(){
        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(20000)
                .build();

        assertExpireDate(payData, LocalDate.of(2019,5,1));

        PayData payData2 = PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(30000)
                .build();

        assertExpireDate(payData2, LocalDate.of(2019,6,1));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부(){
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(20000)
                .build();

        assertExpireDate(payData, LocalDate.of(2019,4,30));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(40000)
                .build();

        assertExpireDate(payData2, LocalDate.of(2019,6,30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 3, 31))
                .billingDate(LocalDate.of(2019, 4, 30))
                .payAmount(30000)
                .build();

        assertExpireDate(payData3, LocalDate.of(2019,7,31));

    }

    @Test
    void 십만원을_납부하면_1년_제공(){

        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 28))
                .payAmount(100000)
                .build();

        assertExpireDate(payData, LocalDate.of(2020,1,28));

    }

    @Test
    void 십만이상을_납부하면_1년이상을_제공(){

        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 28))
                .payAmount(110000)
                .build();

        assertExpireDate(payData, LocalDate.of(2020,2,28));
    }



}
