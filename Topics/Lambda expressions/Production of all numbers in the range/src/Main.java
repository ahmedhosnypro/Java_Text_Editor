import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (x, y) -> {
        long result = x;
        while (x < y) {
            x++;
            result *= x;
        }
        return result;
    };
}