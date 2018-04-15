package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {

  // TODO: populate as part of Question 1 and Question 3

    private int order;
    private List<T> list;

    public Matrix(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Empty list");
        }
        this.list = list;
        this.order = ((int) Math.sqrt(list.size()));
    }

    public T get(int row, int col) {
        return list.get(row * order + col);
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < order; i++) {
            stringBuilder.append('[');
            stringBuilder.append(get(i, 0));
            for (int j = 1; j < order; j++) {
                stringBuilder.append(' ');
                stringBuilder.append(get(i, j));
            }
            stringBuilder.append(']');
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
        List<T> sum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            sum.add(elementSum.apply(list.get(i), other.list.get(i)));
        }
        return new Matrix<>(sum);
    }

    public Matrix<T> product(Matrix<T> other, BinaryOperator<T> elementSum, BinaryOperator<T> elementProduct) {
        List<T> product = new ArrayList<>();
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                T sum = elementProduct.apply(get(i, 0), other.get(0, j));
                for (int k = 1; k < order; k++) {
                    sum = elementSum.apply(sum, elementProduct.apply(get(i, k), other.get(k, j)));
                }
                product.add(sum);
            }
        }
        return new Matrix<>(product);
    }
}
