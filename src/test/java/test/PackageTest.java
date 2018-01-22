package test;

import org.junit.Test;
import org.springframework.boot.SpringApplication;

/**
 * created date：2016-12-16
 *
 * @author niezhegang
 */
public class PackageTest {

    @Test
    public void testPackage(){
        Package pkg = this.getClass().getPackage();
        System.out.println((pkg != null ? pkg.getImplementationVersion() : null));
    }
}
