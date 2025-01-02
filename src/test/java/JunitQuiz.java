import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JunitQuiz {

    @Test
    public void junitTest1() {
        String name1 = "홍길동1";
        String name2 = "홍길동1";
        String name3 = "홍길동3";

        Assertions.assertThat(name1).isNotNull();
        Assertions.assertThat(name2).isNotNull();
        Assertions.assertThat(name3).isNotNull();

        Assertions.assertThat(name1).isEqualTo(name2);

        Assertions.assertThat(name1).isNotEqualTo(name3);
    }
}
