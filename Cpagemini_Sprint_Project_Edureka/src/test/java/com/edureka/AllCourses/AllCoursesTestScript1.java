
package com.edureka.AllCourses;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;
import com.edureka.utilities.ExcelUtilities;

import objRepo.AllCoursesPage;

public class AllCoursesTestScript1 extends BaseClass {

    private String excelData(String testId, String column)
            throws Exception {
        return ExcelUtilities.getDataByKey(
                "Test Cases", "TC_ID", testId, column);
    }
    
    
    @Test(groups = {"smoke", "functional"})
    public void verifyAllCoursesPageLoads() throws Exception {

        String testId = "TC_AC_01";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();

        Assert.assertTrue(page.isAllCoursesPageLoaded(),"All Courses page did not load. Expected: " + expected);

        Reporter.log(testId + " passed: " + expected, true);
    }


    @Test(groups = {"functional"})
    public void verifyArtificialIntelligenceCategory() throws Exception {

        String testId = "TC_AC_05";
        String expected = excelData(testId, "Expected Result");

        AllCoursesPage page =new AllCoursesPage(driver, properties, wait);

        page.openAllCoursesPage();
        page.selectCategory("Artificial Intelligence");

        Assert.assertTrue(page.isCategoryVisible("Artificial Intelligence"),"Artificial Intelligence category is not displayed.");

        Reporter.log(testId + " completed: " + expected, true);
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
