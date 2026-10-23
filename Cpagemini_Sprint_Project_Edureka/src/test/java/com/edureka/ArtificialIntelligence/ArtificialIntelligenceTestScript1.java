
package com.edureka.ArtificialIntelligence;

import org.testng.Assert;
import org.testng.annotations.Test;

import objRepo.ArtificialIntelligencePage;
import com.edureka.base.BaseClass;

public class ArtificialIntelligenceTestScript1 extends BaseClass {

    @Test(groups = {"functional"})
    public void verifyAICourseDetailReloadsCorrectly() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.openAIListing();
        aiPage.exploreAICourses();
        aiPage.openFirstAICourse();

        Assert.assertTrue(aiPage.refreshCourseDetailPage(),"AI course detail page did not reload correctly.");
    }

    @Test(groups = {"integration"})
    public void verifyBackNavigationReturnsToAIListing() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.openAIListing();
        aiPage.exploreAICourses();
        aiPage.openFirstAICourse();

        Assert.assertTrue(aiPage.navigateBackToAIListing(), "Back navigation did not return to the AI listing.");
    }

    @Test(groups = {"functional"})
    public void verifyScrollingReachesLowerAISections() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.openAIListing();

        Assert.assertTrue(aiPage.scrollToLowerSections(),"Scrolling did not reach lower page content.");
    }

    @Test(groups = {"system"})
    public void verifyEndToEndAICourseDiscovery() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.navigateToAIFromHome();

        Assert.assertTrue(aiPage.isAIListingDisplayed(),"AI listing did not open from the homepage.");

        aiPage.exploreAICourses();

        String courseTitle = aiPage.getFirstAICourseTitle();

        Assert.assertNotNull(courseTitle,"AI course title is missing.");

        Assert.assertFalse(courseTitle.trim().isEmpty(),"AI course title is empty.");

        aiPage.openFirstAICourse();

        Assert.assertFalse(aiPage.getCurrentUrl().contains("/artificial-intelligence-certification-courses"),
                "The AI course detail page did not open.");
    }
    
    @Test(groups = {"integration"})
    public void verifyPrimaryCourseCallToActionNavigation() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.navigateToAIFromHome();
        aiPage.exploreAICourses();

        Assert.assertTrue( aiPage.clickPrimaryCourseCallToAction(),
                "The primary course call-to-action did not navigate to the next page.");

        System.out.println("TC_AI_18 passed: Course call-to-action navigated.");
    }


    @Test(groups = {"system"})
    public void verifyAICourseLinksRemainAccessibleAfterScrolling() {

        ArtificialIntelligencePage aiPage =new ArtificialIntelligencePage(driver, properties, wait);

        aiPage.navigateToAIFromHome();
        aiPage.exploreAICourses();

        Assert.assertTrue(aiPage.verifyCourseLinksAfterScrolling(),
                "AI course links were not accessible after scrolling.");
    }

 
}
