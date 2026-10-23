
package com.edureka.AllCourses;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;
import com.edureka.utilities.ExcelUtilities;

import objRepo.AllCoursesPage;

public class AllCoursesTestScript2 extends BaseClass {

    private String excelData(String testId, String column)
            throws Exception {

        return ExcelUtilities.getDataByKey("Test Cases", "TC_ID", testId, column);
    }

    @Test(groups = {"functional"})
    public void verifyOnlineMBACategory() throws Exception {

        String testId = "TC_AC_07";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Online MBA");

        Assert.assertTrue(page.isCategoryVisible("Online MBA"),expected);

        Reporter.log(testId + " passed: " + expected, true);
    }

    @Test(groups = {"functional"})
    public void verifyProgrammingFrameworksCategory()
            throws Exception {

        String testId = "TC_AC_08";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Programming & Frameworks");

        Assert.assertTrue(page.isCategoryVisible("Programming & Frameworks"),expected);

        Reporter.log(testId + " passed: " + expected, true);
    }

    
    @Test(groups = {"functional"})
    public void verifyProjectManagementCategory() throws Exception {

        String testId = "TC_AC_10";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Project Management and Methodologies");

        Assert.assertTrue(page.isCategoryVisible("Project Management and Methodologies"),expected);

        Reporter.log(testId + " passed: " + expected, true);
    }

    @Test(groups = {"functional"})
    public void verifyDatabasesCategory() throws Exception {

        String testId = "TC_AC_11";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page = new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Databases");

        Assert.assertTrue(page.isCategoryVisible("Databases"),expected);

        Reporter.log(testId + " passed: " + expected, true);
    }

    @Test(groups = {"functional"})
    public void verifyOthersCategory() throws Exception {

        String testId = "TC_AC_12";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Others");

        Assert.assertTrue(page.isAdditionalCategoriesPageDisplayed(),expected);

        Reporter.log(testId + " passed: Additional categories page displayed.",true);
    }
}
