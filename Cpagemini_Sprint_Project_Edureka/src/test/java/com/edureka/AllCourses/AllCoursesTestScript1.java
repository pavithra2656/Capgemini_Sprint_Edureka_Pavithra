
package com.edureka.AllCourses;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;

public class AllCoursesTestScript1 extends BaseClass {

	@Test(groups = { "smoke", "functional" })
	public void verifyAllCoursesPageLoads() throws Exception {

		String testId = "TC_AC_01";

		page.openAllCoursesPage();

		Assert.assertTrue(page.isAllCoursesPageLoaded(), "All Courses page did not load");

		Reporter.log(testId + " passed ", true);
	}

	@Test(groups = { "functional" })
	public void verifyArtificialIntelligenceCategory() throws Exception {

		String testId = "TC_AC_05";

		page.openAllCoursesPage();
		page.selectCategory(excel.getData("allcourses", 1, 0));

		Assert.assertTrue(page.isCategoryVisible(excel.getData("allcourses", 1, 0)),
				excel.getData("allcourses", 1, 0) + " category is not displayed.");

		Reporter.log(testId + " passed ", true);
	}

}
