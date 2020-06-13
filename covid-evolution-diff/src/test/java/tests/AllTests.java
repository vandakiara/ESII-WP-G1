package tests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ CovidEvolutionDiffMainTest.class, ConnGitTest.class, HtmlDiffBuilderTest.class })
public class AllTests {

}
