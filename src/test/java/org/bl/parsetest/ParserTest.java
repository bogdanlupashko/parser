import org.bl.parsetest.qname.Parser;
import org.bl.parsetest.qname.QName;
import org.bl.parsetest.qname.exeption.IllegalNameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * ParserTest
 *
 * @version 0.1
 *
 * @author blupashko
 * @since 21.03.2015
 */

@RunWith(Parameterized.class)
public class ParserTest {
    private Parser parser;
    private String nameQName;
    private boolean isPositive;

    public ParserTest(String nameQName, boolean isPositive) {
        this.nameQName = nameQName;

        this.isPositive = isPositive;
    }

    /**
     * tested data
     * true - positive scenario
     * false - negative scenario
     */

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {"name", true},
                {"prefix:name", true},
                {"prefix:na me", true},
                {"prefix: name", false},
                {"", false},
                {":name", false},
                {".", false},
                {"..", false},
                {"prefix:", false},
                {" name", false},
                {" prefix:name", false},
                {"prefix: name", false},
                {"pre fix:name ", false},
                {"name/name ", false},
                {"name[name ", false},
                {"prefix:name:name", false},
        };
    }

    @Before
    public void init() {
        parser = new Parser();
    }

    @Test
    public void test() {
        try {
            QName name = parser.parse(nameQName);
            String[] nameQNameSplitted = nameQName.split(":");
            if (isPositive && nameQNameSplitted.length == 2) {
                Assert.assertEquals(nameQNameSplitted[0], name.getPrefix());
                Assert.assertEquals(nameQNameSplitted[1], name.getLocalName());
            } else if (isPositive){
                Assert.assertEquals(nameQNameSplitted[0], name.getAsString());
            }
        } catch (Exception e) {
            if (isPositive) {
                Assert.fail("Not expected exception");
            } else {
                Assert.assertEquals(IllegalNameException.class, e.getClass());
            }
        }
    }
}
