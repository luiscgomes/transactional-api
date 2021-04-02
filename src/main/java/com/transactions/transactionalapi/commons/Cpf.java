package com.transactions.transactionalapi.commons;

import java.util.InputMismatchException;

public class Cpf {
    public static boolean isCpfLength(String number) {
        return number.length() == 11;
    }

    public static boolean validate(String number) {
        if (number.equals("00000000000") ||
                number.equals("11111111111") ||
                number.equals("22222222222") || number.equals("33333333333") ||
                number.equals("44444444444") || number.equals("55555555555") ||
                number.equals("66666666666") || number.equals("77777777777") ||
                number.equals("88888888888") || number.equals("99999999999") ||
                (number.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 10;
            for (i=0; i<9; i++) {
                num = (int)(number.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            weight = 11;
            for(i=0; i<10; i++) {
                num = (int)(number.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == number.charAt(9)) && (dig11 == number.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException error) {
            return(false);
        }
    }
}
