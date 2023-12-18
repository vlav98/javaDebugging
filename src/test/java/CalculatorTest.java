import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(LoggingExtension.class)
class CalculatorTest {
    private static Instant startedAt;
    private Calculator calculatorUnderTest;

    private Logger logger;

    @BeforeAll
    public static void initStartingTime() {
        System.out.println("Appel avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    public static void showTestDuration() {
        System.out.println("Appel après tous les tests");
        Instant endedAt = Instant.now();
        long duration = Duration.between(startedAt, endedAt).toMillis();
        System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @BeforeEach
    public void initCalculator() {
        calculatorUnderTest = new Calculator();
        logger.info("Appel avant chaque test");
    }

    @AfterEach
    public void undefCalculator() {
        logger.info("Appel après chaque test");
        calculatorUnderTest = null;
    }

    @Test
    @Tag("QuatreOperations")
    void testAddTwoPositiveNumbers() {
        final int a = 2;
        final int b = 3;

        final int sum = calculatorUnderTest.add(a, b);

        assertThat(sum).isEqualTo(5);
    }

    @Test
    @Tag("QuatreOperations")
    void testMultiplyTwoPositiveNumbers() {
        final int a = 2;
        final int b = 3;

        final int product = calculatorUnderTest.multiply(a, b);

        assertThat(product).isEqualTo(6);
    }

    @ParameterizedTest(name = "{0} x 0 doit être égal à 0")
    @ValueSource(ints = {1, 2, 42})
    public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
        final int actualResult = calculatorUnderTest.multiply(arg, 0);

        assertThat(actualResult).isEqualTo(0);
    }

    @ParameterizedTest(name = "{0} + {1} doit être égal à {2}")
    @CsvSource({"1, 1, 2", "2, 3, 5", "42, 57, 99"})
    public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
        final int actualResult = calculatorUnderTest.add(arg1, arg2);

        assertThat(expectResult).isEqualTo(actualResult);
    }

    @Test
    @Timeout(1)
    public void longCalcul_shouldComputeInLessThan1Second() {
        calculatorUnderTest.longCalculation();
    }

    @Test
    public void listDigits_shouldRetursTheListOfDigits_ofPositiveInteger() {
        // GIVEN
        int number = 95897;

        // WHEN
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        // THEN
        final Set<Integer> expectedDigits = Stream.of(5, 7, 8, 9).collect(Collectors.toSet());
        assertThat(actualDigits).containsExactlyInAnyOrder(9, 5, 7, 8);
    }

    @Test
    public void listDigits_shouldRetursTheListOfDigits_ofNegativeInteger() {
        int number = -12342;
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
        assertThat(actualDigits).containsExactlyInAnyOrder(1, 2, 3, 4);
    }

    @Test
    public void listDigits_shouldRetursTheListOfZero_ofZero() {
        int number = 0;
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
        assertThat(actualDigits).containsExactlyInAnyOrder(0);
    }
}