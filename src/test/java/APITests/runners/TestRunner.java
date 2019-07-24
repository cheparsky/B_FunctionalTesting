package APITests.runners;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;
import org.junit.runner.RunWith;

@RunWith(Karate.class)
@KarateOptions(tags = {"@test","~@ignore"}, features = "classpath:APITests/features")
public class TestRunner {
}