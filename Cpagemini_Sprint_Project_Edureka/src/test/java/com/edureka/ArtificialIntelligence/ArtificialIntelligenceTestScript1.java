
package com.edureka.ArtificialIntelligence;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.edureka.base.BaseClass;

public class ArtificialIntelligenceTestScript1 extends BaseClass {

	@Test(groups = { "integration" })
	public void verifyBackNavigationReturnsToAIListing() throws IOException {

		aiPage.openAIListing();
		aiPage.exploreAICourses();
		aiPage.openFirstAICourse();

		Assert.assertTrue(aiPage.navigateBackToAIListing(), "Back navigation did not return to the AI listing.");
		Reporter.log( " passed ", true);
		
	}

	@Test(groups = { "system" })
	public void verifyEndToEndAICourseDiscovery() throws IOException {

		aiPage.navigateToAIFromHome();

		Assert.assertTrue(aiPage.isAIListingDisplayed(), "AI listing did not open from the homepage.");

		aiPage.exploreAICourses();

		String courseTitle = aiPage.getFirstAICourseTitle();

		Assert.assertNotNull(courseTitle, "AI course title is missing.");

		Assert.assertFalse(courseTitle.trim().isEmpty(), "AI course title is empty.");

		aiPage.openFirstAICourse();

		Assert.assertFalse(aiPage.getCurrentUrl().contains("/artificial-intelligence-certification-courses"),"The AI course detail page did not open.");
		
		Reporter.log( " passed ", true);
	}

	@Test(groups = { "integration" })
	public void verifyPrimaryCourseCallToActionNavigation() throws IOException {

		aiPage.navigateToAIFromHome();
		aiPage.exploreAICourses();

		Assert.assertTrue(aiPage.clickPrimaryCourseCallToAction(),"The primary course call-to-action did not navigate to the next page.");

		System.out.println("TC_AI_18 passed: Course call-to-action navigated.");
		
		Reporter.log( " passed ", true);
	}

	@Test(groups = { "system" })
	public void verifyAICourseLinksRemainAccessibleAfterScrolling() throws IOException {

		aiPage.navigateToAIFromHome();
		aiPage.exploreAICourses();

		Assert.assertTrue(aiPage.verifyCourseLinksAfterScrolling(),"AI course links were not accessible after scrolling.");
		
		Reporter.log( " passed ", true);
	}

}
