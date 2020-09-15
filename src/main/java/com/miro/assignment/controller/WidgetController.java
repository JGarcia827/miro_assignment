package com.miro.assignment.controller;

import java.util.List;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.exception.PagingSizeExceededException;
import com.miro.assignment.service.api.WidgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class is the REST API controller for Widget entities
 */
@RequestMapping("/api/v1/widgets")
@RestController
@Api(tags = { "widgets" })
public class WidgetController {

    @Autowired
    protected WidgetService widgetService;

    private static final int PAGE_MAX_SIZE = 500;
    private static final String PAGE_DEFAULT_PAGE = "0";
    private static final String PAGING_DEFAULT_SIZE = "10";
    private static final String PAGING_DEFAULT_SORT = "zIndex";

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all widgets.", notes = "Returns a list of all widgets sorted by incrementing z-index.")
    public List<Widget> getAllWidgets() {
        return widgetService.getAll();
    }

    @GetMapping("/paged_list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all widgets (paged).", notes = "Returns a list of all widgets using paging, filtering and sorting.")
    public List<Widget> getAllWidgetsPaged(final @RequestParam(name = "page", defaultValue = PAGE_DEFAULT_PAGE) int pageNo,
            final @RequestParam(name = "size", defaultValue = PAGING_DEFAULT_SIZE) int pageSize,
            final @RequestParam(name = "sort", defaultValue = PAGING_DEFAULT_SORT) String sortBy) {
        if (pageSize > PAGE_MAX_SIZE) {
            throw new PagingSizeExceededException(PAGE_MAX_SIZE, pageSize);
        }
        return widgetService.getAll(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Display Widget data.", notes = "Displays Widget data given an ID.")
    public Widget showWidget(
            @ApiParam(value = "The id of the Widget to display.", required = true) final @PathVariable(name = "id") Long id) {
        return widgetService.find(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new Widget.", notes = "Creates a new Widget entity with the specified data.")
    public Widget createWidget(@RequestBody final Widget widget) {
        return widgetService.create(widget);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a Widget.", notes = "Updates a Widget entity according to the specified data.")
    public Widget updateWidget(
            @ApiParam(value = "The id of the Widget to update.", required = true) @PathVariable("id") final Long id,
            @RequestBody final Widget widget) {
        return widgetService.update(id, widget);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a Widget.", notes = "Deletes a Widget entity given an ID.")
    public void deleteWidget(
            @ApiParam(value = "The id of the Widget to delete.", required = true) final @PathVariable(name = "id") Long id) {
        widgetService.delete(id);
    }

    @GetMapping("/area")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all widgets with fit an area.", notes = "Returns a list of all widgets which fit a specified area.")
    public List<Widget> getAllWidgetFilterdByArea(
            final @RequestParam(name = "x_coordinate", required = true) int xCoordinate,
            final @RequestParam(name = "y_coordinate", required = true) int yCoordinate,
            final @RequestParam(name = "width", required = true) int width,
            final @RequestParam(name = "height", required = true) int height) {
        return widgetService.getWidgetsFilteredByArea(xCoordinate, yCoordinate, width, height);
    }

}
