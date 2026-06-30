package com.ssw.blog.service.impl;

import com.ssw.blog.dto.YgoJammingRateParam;
import com.ssw.blog.enums.ResultEnum;
import com.ssw.blog.exception.BlogException;
import com.ssw.blog.service.UtilService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UtilServiceImpl implements UtilService {

    private static final int HAND_COUNT = 5;

    @Override
    public String getYGOJammingRate(YgoJammingRateParam param) {
        checkParam(param);

        int deckCount = param.getDickCount();
        int singleCard = param.getSingleCard();
        int doubleCardOne = param.getDoubleCardOne();
        int doubleCardTwo = param.getDoubleCardTwo();
        int otherCard = deckCount - singleCard - doubleCardOne - doubleCardTwo;

        long totalCount = combination(deckCount, HAND_COUNT);
        long successCount = 0L;

        for (int single = 0; single <= Math.min(singleCard, HAND_COUNT); single++) {
            for (int one = 0; one <= Math.min(doubleCardOne, HAND_COUNT - single); one++) {
                for (int two = 0; two <= Math.min(doubleCardTwo, HAND_COUNT - single - one); two++) {
                    int other = HAND_COUNT - single - one - two;
                    if (other < 0 || other > otherCard) {
                        continue;
                    }
                    if (single > 0 || (one > 0 && two > 0)) {
                        successCount += combination(singleCard, single)
                                * combination(doubleCardOne, one)
                                * combination(doubleCardTwo, two)
                                * combination(otherCard, other);
                    }
                }
            }
        }

        BigDecimal rate = BigDecimal.valueOf(successCount)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalCount), 2, RoundingMode.HALF_UP);
        return rate.toPlainString() + "%";
    }

    private void checkParam(YgoJammingRateParam param) {
        if (param == null) {
            throw new BlogException(ResultEnum.PARAMS_NULL);
        }
        int deckCount = param.getDickCount();
        int singleCard = param.getSingleCard();
        int doubleCardOne = param.getDoubleCardOne();
        int doubleCardTwo = param.getDoubleCardTwo();
        if (deckCount < HAND_COUNT || deckCount > 60
                || singleCard < 0 || doubleCardOne < 0 || doubleCardTwo < 0
                || singleCard + doubleCardOne + doubleCardTwo > deckCount) {
            throw new BlogException(ResultEnum.PARAMS_ERROR);
        }
    }

    private long combination(int total, int select) {
        if (select < 0 || select > total) {
            return 0L;
        }
        int minSelect = Math.min(select, total - select);
        long result = 1L;
        for (int i = 1; i <= minSelect; i++) {
            result = result * (total - minSelect + i) / i;
        }
        return result;
    }
}
