import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JunitCycleTest {

    // @BeforeAll은 모든 테스트 메소드가 실행되기 전에 실행된다.
    // 전체 테스트 시작하기 전 1회만 실행하기 때문에 static으로 선언해야 한다.
    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    // @AfterAll은 모든 테스트 메소드가 실행된 후에 실행된다.
    // 전체 테스트 마치고 종료하기 전 1회만 실행하기 때문에 static으로 선언해야 한다.
    @AfterAll
    static void afterAll() {
        System.out.println("@AfterAll");
    }

    // @BeforeEach는 각 테스트 메소드가 실행되기 전에 실행된다.
    @BeforeEach
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    // @AfterEach는 각 테스트 메소드가 실행된 후에 실행된다.
    @AfterEach
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}
