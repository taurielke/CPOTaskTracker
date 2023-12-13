package com.example.courseworktasktracker;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import com.example.courseworktasktracker.data.Category;
import com.example.courseworktasktracker.data.CategoryData;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {
    private CategoryData categoryData;

    @Test
    public void testGetCategory() {
        Category category = new Category();
        category.setName("Test Category");
        categoryData.addCategory(category.getName());
        Category result = categoryData.getCategory(category.getId());
        assertNotNull(result);
        assertEquals(category.getName(), result.getName());
        categoryData.deleteCategory(category.getId());
    }

    @Test
    public void testFindAllCategory() {
        List<Category> categories = categoryData.findAllCategory();
        assertNotNull(categories);
    }

}