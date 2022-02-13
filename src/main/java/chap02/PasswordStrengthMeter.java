package chap02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        if(s == null || s.isEmpty()) return PasswordStrength.INVALID;

        int matchCount = matchCount(s);

        if(matchCount <= 1) return PasswordStrength.WEAK;
        if(matchCount == 2) return PasswordStrength.NORMAL;
        return PasswordStrength.STRONG;
    }

    private int matchCount(String s){
        int matchCount = 0;
        boolean isLengthEnough = s.length() >= 8;
        boolean isContainsNum = isContainsNum(s);
        boolean isContainsUpperCase = isContainsUpperCase(s);

        if(isLengthEnough) matchCount++;
        if(isContainsNum) matchCount++;
        if(isContainsUpperCase) matchCount++;
        return matchCount;
    }

    private boolean isContainsNum(String s){
        for (char c : s.toCharArray()) {
            if(c >= '0' && c <= '9'){
                return true;
            }
        }
        return false;
    }

    private boolean isContainsUpperCase(String s){
        for (char c : s.toCharArray()) {
            if(Character.isUpperCase(c)){
                return true;
            }
        }
        return false;
    }
}
