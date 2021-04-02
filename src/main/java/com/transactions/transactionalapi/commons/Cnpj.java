package com.transactions.transactionalapi.commons;

import java.util.InputMismatchException;

public class Cnpj {
    public static boolean validate(String number) {
        if (number.equals("00000000000000") || number.equals("11111111111111") ||
                number.equals("22222222222222") || number.equals("33333333333333") ||
                number.equals("44444444444444") || number.equals("55555555555555") ||
                number.equals("66666666666666") || number.equals("77777777777777") ||
                number.equals("88888888888888") || number.equals("99999999999999") ||
                (number.length() != 14))
            return(false);

        char dig13, dig14;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 2;
            for (i=11; i>=0; i--) {
                num = (int)(number.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

            sm = 0;
            weight = 2;
            for (i=12; i>=0; i--) {
                num = (int)(number.charAt(i)- 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

            if ((dig13 == number.charAt(12)) && (dig14 == number.charAt(13)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
