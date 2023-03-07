/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE   TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON YOUR
 * BEHALF.
 * IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT AGREE TO THEM,
 * YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER.
 * UPC IS THE OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY
 * AUTHORIZES YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN
 * THE LICENSE AGREEMENT.
 */
package model.entities;

import exceptions.MalformedFunctionDistributionException;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class FunctionTest extends TestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public FunctionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void test1() throws Exception {
        Function f = new Function("test", "1", "D4", "1,3/2,5/3,8/4,12");
        assertEquals(4, f.getDistributionSize());
    }

    @Test
    public void test2() throws Exception {
        Function f = new Function("test", "1", "C3", "1,3/2,5/3,8");
        assertEquals(3, f.getDistributionSize());
    }

    @Test
    public void test3() throws MalformedFunctionDistributionException {

        try {
            Function f = new Function("test", "1", "C6", "1,3/2,5/3,8");
        } catch (MalformedFunctionDistributionException e) {
            assertEquals(e.getMessage(), "Malformed function distribution");
        }
    }

    @Test
    public void test4() throws MalformedFunctionDistributionException {

        try {
            Function f = new Function("test", "1", "C3", "1,3/2,/3,8");
        } catch (MalformedFunctionDistributionException e) {
            assertEquals(e.getMessage(), "Malformed function distribution");
        }
    }

    @Test
    public void test5() throws MalformedFunctionDistributionException {

        try {
            Function f = new Function("test", "1", "C3", "1,3/2,r/3,8");
        } catch (MalformedFunctionDistributionException e) {
            assertEquals(e.getMessage(), "Malformed function distribution");
        }
    }

    @Test
    public void test6() throws MalformedFunctionDistributionException {

        try {
            Function f = new Function("test", "1", "X3", "1,3/2,5/3,8");
        } catch (MalformedFunctionDistributionException e) {
            assertEquals(e.getMessage(), "Malformed function distribution");
        }
    }

    @Test
    public void test7() throws Exception {
        Function f = new Function("test", "1", "C3", "1,3/2,5/3,8");
        assertTrue(f.getB().startsWith("C"));
    }

    @Test
    public void test8() throws MalformedFunctionDistributionException {

        try {
            Function f = new Function("test", "1", "C03", "1,3/2,5/3,8");
        } catch (MalformedFunctionDistributionException e) {
            assertEquals(e.getMessage(), "Malformed function distribution");
        }
    }

    @Test
    public void test9() throws Exception {

        Function f = new Function("test", "0.4", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        assertEquals(f.evaluate(), 1f);
    }

    @Test
    public void test10() throws Exception {

        Function f = new Function("test", ".7", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        assertEquals(f.evaluate(), 2f);
    }

    @Test
    public void test11() throws Exception {

        Function f = new Function("test", ".5", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        assertEquals(f.evaluate(), 2f);
    }

    @Test
    public void test12() throws Exception {

        Function f = new Function("test", ".96", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        assertEquals(f.evaluate(), 5f);
    }

    @Test
    public void test13() throws Exception {

        Function f = new Function("test", "1.5", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        assertEquals(f.evaluate(), 5f);
    }

    @Test
    public void test14() throws Exception {

        Function f = new Function("test", "1", "L4", "1,3/2,5/3,8/4,12");
        assertEquals(f.evaluate(), 3f);
    }

    @Test
    public void test15() throws Exception {

        Function f = new Function("test", "4", "L4", "1,3/2,5/3,8/4,12");
        assertEquals(f.evaluate(), 12f);
    }

    @Test
    public void test16() throws Exception {

        Function f = new Function("test", "20", "L4", "1,3/2,5/3,8/4,12");
        assertEquals(f.evaluate(), 0f);
    }

    @Test
    public void test17() throws Exception {

        Function f = new Function("test", "RN1", "D5", ".4,1/.7,2/.85,3/.95,4/1,5");
        Float result = f.evaluate();
        assertTrue(result >= 1f && result <= 5f);
    }

    @Test
    public void test18() throws Exception {

        Function f = new Function("test", "RN5", "D5", "1,1/2,2/3,3/4,4/5,5");
        Float result = f.evaluate();
        assertTrue(result >= 1f && result <= 5f);
    }
}
