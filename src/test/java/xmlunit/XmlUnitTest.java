package xmlunit;

import java.lang.invoke.MethodHandles;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import javaslang.Tuple;
import javaslang.collection.Stream;

public class XmlUnitTest {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Rule
  public TestName name = new TestName();

  @Before
  public void before() {
    logger.info("start:{}", name.getMethodName());
  }

  @Test
  public void testXmlUnit() throws Exception {

    Source one = Input.fromFile("src/test/resources/xml/one.xml").build();
    Source two = Input.fromFile("src/test/resources/xml/two.xml").build();

    // most configuration is done in the builder...
    Diff diff = DiffBuilder.compare(one)
        .withTest(two)
        .withDifferenceListeners((c, o) -> logger.info("{}:{}", o, c))
        .ignoreWhitespace()
        .ignoreComments()
        .build();

    Stream<Difference> diffs = Stream.ofAll(diff.getDifferences());

    diffs
        .map(d -> Tuple.of(
            d.getResult(),
            d.getComparison().getControlDetails().getXPath(),
            d.getComparison().getControlDetails().getValue(),
            d.getComparison().getTestDetails().getXPath(),
            d.getComparison().getTestDetails().getValue()))
        .forEach(t -> logger.info("{}", t));

    // assertThat(diff.hasDifferences()).isFalse();
  }
}
