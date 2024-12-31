import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class JUnitQuiz {

    @Test
    public void junitTest() {
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();

        //name1과 name2가 같은지 비교
        assertThat(name1).isEqualTo(name2);

        //name1과 name3가 다른지 비교
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    public void junitTest2() {
        int number1 = 15;;
        int number2 = 0;
        int number3 = -5;

        assertThat(number1).isPositive();
        assertThat(number2).isZero();
        assertThat(number3).isNegative();
        assertThat(number1).isGreaterThan(number3);
        assertThat(number3).isLessThan(number2);
    }
}
