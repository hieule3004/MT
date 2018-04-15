package generalmatrices.examples;

import generalmatrices.matrix.Matrix;
import generalmatrices.pair.PairWithOperators;
import java.util.List;
import java.util.NoSuchElementException;

public class Example {

  public static Matrix<PairWithOperators> multiplyPairMatrices(
        List<Matrix<PairWithOperators>> matrices) {
    // TODO: implement as part of Question 4
    try {
      return matrices.stream().reduce((ma, mb) -> ma.product(mb, PairWithOperators::sum, PairWithOperators::product)).get();
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Empty list");
    }
  }

}
