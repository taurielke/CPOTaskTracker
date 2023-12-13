package com.example.courseworktasktracker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.courseworktasktracker.data.Category;
import com.example.courseworktasktracker.data.CategoryData;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class ExampleUnitTest {

    private CategoryData categoryData;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        categoryData = new CategoryData(context);
        categoryData.deleteAll();
    }

    @Test
    public void A(){
        assertEquals(4, 2+2);
    }


    @Test
    public void testGetCategory() {
        Category category = new Category();
        category.setId(1);
        categoryData.addCategory("TestCategory");
        Category result = categoryData.getCategory(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("TestCategory", result.getName());
    }

    @Test
    public void testFindAllCategory() {
        categoryData.addCategory("TestCategory1");
        categoryData.addCategory("TestCategory2");
        List<Category> result = categoryData.findAllCategory();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TestCategory1", result.get(0).getName());
        assertEquals("TestCategory2", result.get(1).getName());
    }

    @Test
    public void testAddCategory() {
        categoryData.addCategory("TestCategory");
        List<Category> result = categoryData.findAllCategory();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestCategory", result.get(0).getName());
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        category.setId(1);
        categoryData.addCategory("TestCategory");
        categoryData.updateCategory(1, "UpdatedCategory");
        Category updatedCategory = categoryData.getCategory(1);
        assertNotNull(updatedCategory);
        assertEquals(1, updatedCategory.getId());
        assertEquals("UpdatedCategory", updatedCategory.getName());
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category();
        category.setId(1);
        categoryData.addCategory("TestCategory");
        categoryData.deleteCategory(1);
        List<Category> result = categoryData.findAllCategory();
        assertTrue(result.isEmpty());
    }
}