package com.miro.assignment.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.Optional;

import com.miro.assignment.utils.Utils;
import com.miro.assignment.domain.Widget;
import com.miro.assignment.repository.WidgetRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class provided integration tests for the Widget REST API
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:application-test.properties")
public class WidgetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WidgetRepository repository;

    private static final String apiBasePath = "/api/v1/widgets";

    /**
     * Test listing all Widgets
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void getAllWidgets_OK() throws Exception {
        // Count widgets in repo
        final int count = repository.findAll().size();

        mockMvc.perform(get(apiBasePath + "/")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));
    }

    /**
     * Test displaying a Widget
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void showWidget_OK() throws Exception {
        // Check that entity is present (from demo data)
        final Optional<Widget> widget = repository.findById((long) 1);
        assertThat(widget.isPresent(), is(equalTo(true)));

        mockMvc.perform(get(apiBasePath + "/" + widget.get().getId())
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    /**
     * Test displaying a Widget (with missing ID)
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void showWidget_NOK_NotFound() throws Exception {
        // Generate a non existing id by finding the max stored
        final Optional<Widget> widgets = repository.findAll().stream().max(Comparator.comparingLong(Widget::getId));
        assertThat(widgets.isPresent(), is(equalTo(true)));        
        final long notExistingID = widgets.get().getId() + 1;
        
        mockMvc.perform(get(apiBasePath + "/" + notExistingID)
            .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

    /**
     * Test creating a Widget (without z-index)
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void createWidget_OK() throws Exception {
        // Count widgets in repo
        final int count = repository.findAll().size();

        // Find lowest z-index widget
        final Optional<Widget> minZIndexWidget = repository.findAll().stream().min(Comparator.comparingLong(Widget::getZIndex));
        assertThat(minZIndexWidget.isPresent(), is(equalTo(true)));
        final int minZIndex = minZIndexWidget.get().getZIndex();

        // Create a new Widget
        final Widget widget =  new Widget.Builder(10, 20, 25, 30).build();    
        widget.setLastModified(null);            

        mockMvc.perform(post(apiBasePath + "/")
                .contentType("application/json")
                .content(Utils.asJsonString(widget)))
                .andExpect(status().isCreated());

        assertThat(repository.findAll().size(), is(count + 1));
        final Optional<Widget> minZIndexWidget2 = repository.findAll().stream().min(Comparator.comparingLong(Widget::getZIndex));
        assertThat(minZIndexWidget2.isPresent(), is(equalTo(true)));
        assertThat(minZIndexWidget2.get().getZIndex(), is(minZIndex - 1));
    }

    /**
     * Test creating a Widget (with z-index)
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void createWidget_OK_withZIndex() throws Exception {
        // Count widgets in repo
        final int count = repository.findAll().size();

        // Find lowest z-index widget
        final Optional<Widget> minZIndexWidget = repository.findAll().stream().min(Comparator.comparingLong(Widget::getZIndex));
        assertThat(minZIndexWidget.isPresent(), is(equalTo(true)));
        final int minZIndex = minZIndexWidget.get().getZIndex();

        // Create a new Widget
        final Widget widget =  new Widget.Builder(10, 20, 25, 30).withZIndex(minZIndex).build();    
        widget.setLastModified(null);            

        mockMvc.perform(post(apiBasePath + "/")
                .contentType("application/json")
                .content(Utils.asJsonString(widget)))
                .andExpect(status().isCreated());

        assertThat(repository.findAll().size(), is(count + 1));
        final Optional<Widget> minZIndexWidget2 = repository.findAll().stream().min(Comparator.comparingLong(Widget::getZIndex));
        assertThat(minZIndexWidget2.isPresent(), is(equalTo(true)));
        assertThat(minZIndexWidget2.get().getZIndex(), is(minZIndex));
        assertThat(repository.findById(minZIndexWidget.get().getId()).get().getZIndex(), is(minZIndex + 1));
    }

    /**
     * Test creating a Widget (with missing required fields)
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void createWidget_NOK_missingFields() throws Exception {
        // Create a new Widget
        final Widget widget =  new Widget.Builder(10, 20, 25, 30).build();    
        widget.setLastModified(null);  
        widget.setXCoordinate(null);           

        mockMvc.perform(post(apiBasePath + "/")
                .contentType("application/json")
                .content(Utils.asJsonString(widget)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test updating a Widget's data
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void updateWidget_OK() throws Exception {
        final long widgetId = 1;
        // Check that entity is present (from demo data)
        final Optional<Widget> widget = repository.findById((long) widgetId);
        assertThat(widget.isPresent(), is(equalTo(true)));

        // Create a new Widget
        final int xCoordinate = 2;
        final int yCoordinate = 2;
        final int width = 2;
        final int height = 2;
        final Widget widgetUpdate =  new Widget.Builder(xCoordinate, yCoordinate, width, height).build();                
        widgetUpdate.setLastModified(null);      

        mockMvc.perform(put(apiBasePath + "/" + widgetId)
                .contentType("application/json")
                .content(Utils.asJsonString(widgetUpdate)))
                .andExpect(status().isOk());

        final Optional<Widget> updatedWidget = repository.findById(widgetId);
        assertThat(updatedWidget.isPresent(), is(equalTo(true)));
        assertThat(updatedWidget.get().getXCoordinate(), is(xCoordinate));
        assertThat(updatedWidget.get().getYCoordinate(), is(yCoordinate));
        assertThat(updatedWidget.get().getWidth(), is(width));
        assertThat(updatedWidget.get().getHeight(), is(height));
    }

    /**
     * Test deleting a Widget 
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void deleteWidget_OK() throws Exception {
        // Find a free z-index
        final Optional<Widget> maxZIndexWidget = repository.findAll().stream().max(Comparator.comparingLong(Widget::getZIndex));
        assertThat(maxZIndexWidget.isPresent(), is(equalTo(true)));
        
        // Create and save an entity
        final Widget widget = new Widget.Builder(10, 10, 10, 10).withZIndex(maxZIndexWidget.get().getZIndex() + 1).build();
        final long id = repository.save(widget).getId();        

        mockMvc.perform(delete(apiBasePath + "/" + id)
            .contentType("application/json"))
            .andExpect(status().isOk());

        assertThat(repository.findById(id).isPresent(), is(false));  
    }

    /**
     * Test deleting a Widget (using a missing ID)
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void deleteWidget_NOK() throws Exception {
        // Generate a non-existing ID
        final Optional<Widget> maxIdWidget = repository.findAll().stream().max(Comparator.comparingLong(Widget::getId));
        assertThat(maxIdWidget.isPresent(), is(equalTo(true)));  
        final long noExistingId = maxIdWidget.get().getId() + 1;

        mockMvc.perform(delete(apiBasePath + "/" + noExistingId)
            .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

    /**
     * Test retrieving all Widgets in an area
     * 
     * @throws Exception If something unexpectedly goes wrong
     */
    @Test
    void getAllWidgetFilterdByArea_OK() throws Exception {

        final String xCoordinate = "1000";
        final String yCoordinate = "1000";
        final String width = "100";
        final String height = "150";

        mockMvc.perform(get(apiBasePath + "/area")
            .contentType("application/json")
            .param("x_coordinate", xCoordinate)
            .param("y_coordinate", yCoordinate)
            .param("width", width)
            .param("height", height))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[1].id", is(2)));
    }

}
