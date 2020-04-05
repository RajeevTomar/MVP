package com.positivemind.newsapp;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.positivemind.newsapp.dashboard.DashboardActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by Rajeev Tomar on 22,December,2019
 */


public class HeadlineRecyclerViewTest {

    @Rule
    public ActivityTestRule<DashboardActivity> activityTestRule = new ActivityTestRule<>(
            DashboardActivity.class,
            true,
            true
    );

    @Test
    public void testHeadlineListVisiblity()
    {
        Espresso.onView(ViewMatchers.withId(R.id.rv_headline))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testHeadlineItemClick()
    {
        // checking at position 2
        Espresso.onView(ViewMatchers.withId(R.id.rv_headline))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

    }

    @Test
    public void testHeadlineListScroll()
    {
        // Get total item of RecyclerView
        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.rv_headline);
        int itemCount = recyclerView.getAdapter().getItemCount();

        // Scroll to end of page with position
        Espresso.onView(ViewMatchers.withId(R.id.rv_headline))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }

}
