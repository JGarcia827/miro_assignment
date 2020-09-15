package com.miro.assignment.service.api;

import java.util.List;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.exception.MissingWidgetFieldException;
import com.miro.assignment.exception.WidgetNotFoundException;

/**
 * This class provides the interface for the Widget service
 */
public interface WidgetService {
    /**
     * Creates a new Widget entity
     * 
     * @param widget An object which contains the data to use
     * @return A new Widget entity
     * @throws MissingWidgetFieldException If input data is missing required fields
     */
    Widget create(Widget widget) throws MissingWidgetFieldException;

    /**
     * Retrieves a Widget entity by its id
     * 
     * @param id The id to look for
     * @return The entity found
     * @throws WidgetNotFoundException If no entity could be found with the given id
     */
    Widget find(Long id) throws WidgetNotFoundException;

    /**
     * Update a Widget's data
     * 
     * @param id     The entity's id
     * @param widget The object data to update (empty fields will be ignored)
     * @return The updated entity
     * @throws WidgetNotFoundException If no entity could be found with the given id
     */
    Widget update(Long id, Widget widget) throws WidgetNotFoundException;

    /**
     * Delete a Widget entity from the repository
     * 
     * @param id The entity's id
     * @throws WidgetNotFoundException If no entity could be found with the given id
     */
    void delete(Long id) throws WidgetNotFoundException;

    /**
     * Retrieve all Widget entities from the repository according to certain
     * criteria
     * 
     * @param pageNo   The page number to display
     * @param pageSize The page size to use
     * @param sortBy   The sorting criteria
     * @return A list of Widgets according to the criteria
     */
    List<Widget> getAll(int pageNo, int pageSize, String sortBy);

    /**
     * Retrieve all Widget entities from the repository in ascending z-index
     * 
     * @return A list of all Widget entities
     */
    List<Widget> getAll();

    /**
     * Retrieve all Widget entities from the repository within an area
     * 
     * @param xCoordinate Search area X coordinate
     * @param yCoordinate Search area Y coordinate
     * @param width       Search area width
     * @param height      earch area height
     * @return A list of Widgets within the area
     */
    List<Widget> getWidgetsFilteredByArea(int xCoordinate, int yCoordinate, int width, int height);
}
