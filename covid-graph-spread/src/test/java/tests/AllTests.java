package tests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({TestCovidGraphSpreadMain.class, TestCovidGraphSpread.class, TestDataTable.class, TestHtmlCovidTableBuilder.class})
public class AllTests {

}
