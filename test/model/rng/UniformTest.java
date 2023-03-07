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
package model.rng;

import model.entities.rng.Uniform;
import junit.framework.TestCase;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class UniformTest extends TestCase {

    public UniformTest(String testName) {
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

    /**
     * Test of generate method, of class Uniform.
     */
    public void testGenerate_float_float() {
        float A = 5F;
        float B = 1F;
        Uniform instance = new Uniform();
        Float result = instance.generate(A, B);
        System.out.println("generate: " + result);
        assertTrue(result >= A - B && result <= A + B);
    }

    /**
     * Test of name method, of class Uniform.
     */
    public void testName() {
        Uniform instance = new Uniform();
        String expResult = "Uniform";
        String result = instance.name();
        System.out.println("name: " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of generate method, of class Uniform.
     */
    public void testGenerate_0args() {
        Uniform instance = new Uniform();
        Float result = instance.generate();
        System.out.println("generate: " + result);
        assertTrue(result >= 0 && result <= 1);
    }

}
