package joox;

import static org.joox.JOOX.*;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.joox.Match;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class JooxTest {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Rule
  public TestName name = new TestName();

  @Before
  public void before() {
    logger.info("start:{}", name.getMethodName());
  }

  @Test
  public void testJoox() throws Exception {

    // Parse the document from a file
    File xml = new File("src/test/resources/xml/joox-example.xml");
    Document document = $(xml).document();

    // Wrap the document with the jOOX API
    Match x1 = $(document);

    // This will get all books (wrapped <book/> DOM Elements)
    Match x2 = $(document).find("book");

    // This will get all even or odd books
    Match x3 = $(document).find("book").filter(even());
    Match x4 = $(document).find("book").filter(odd());

    // This will get all book ID's
    List<String> ids = $(document).find("book").ids();

    // This will get all books with ID = 1 or ID = 2
    Match x5 = $(document).find("book").filter(ids("1", "2"));

    // Or, use css-selector syntax:
    Match x6 = $(document).find("book#1, book#2");

    // This will use XPath to find books with ID = 1 or ID = 2
    Match x7 = $(document).xpath("//book[@id = 1 or @id = 2]");
    logger.info("{}", x7);
  }

}
