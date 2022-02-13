package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpireDateCalculator {
//    public LocalDate calculatorExpireDate(LocalDate billingDate, int payPrice) {
//        return billingDate.plusMonths(1);
//    }

    public LocalDate calculatorExpireDate(PayData payData) {
        int addedMonth = getAddMonth(payData.getPayAmount());
        if(payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonth);
        }

        return payData.getBillingDate().plusMonths(addedMonth);
    }
    private int getAddMonth(int payAmount){
        int addMonth = payAmount / 10000;
        int yearMonth = addMonth / 10 * 12;
        int month = addMonth % 10;
        return yearMonth + month;
    }


    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonth){
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonth);
        int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
        if (dayOfFirstBilling != candidateExp.getDayOfMonth()) {

            int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
            if(dayLenOfCandiMon < dayOfFirstBilling){
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }

            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        }

        return candidateExp;
    }
}
