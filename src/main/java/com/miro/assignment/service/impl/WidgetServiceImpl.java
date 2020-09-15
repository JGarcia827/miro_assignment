package com.miro.assignment.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.exception.MissingWidgetFieldException;
import com.miro.assignment.exception.WidgetNotFoundException;
import com.miro.assignment.repository.WidgetRepository;
import com.miro.assignment.service.api.WidgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the implementation of the WidgetService
 */
@Service
public class WidgetServiceImpl implements WidgetService {

    @Autowired
    private WidgetRepository repository;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Widget create(final Widget widget) throws MissingWidgetFieldException {

        // Check required fields
        checkRequiredField(widget.getXCoordinate(), "xCoordiante");
        checkRequiredField(widget.getYCoordinate(), "yCoordinate");
        checkRequiredField(widget.getWidth(), "width");
        checkRequiredField(widget.getHeight(), "height");

        final Integer zIndex = widget.getZIndex() == null ? findNewZIndex() : widget.getZIndex();

        final Widget newWidget = new Widget.Builder(widget.getXCoordinate(), widget.getYCoordinate(), widget.getWidth(),
                widget.getHeight()).withZIndex(zIndex).build();

        setZIndex(zIndex, newWidget);

        return repository.save(newWidget);
    }

    @Override
    @Transactional
    public Widget find(final Long id) throws WidgetNotFoundException {
        return repository.findById(id).orElseThrow(() -> new WidgetNotFoundException(id));
    }

    @Override
    @Transactional
    public Widget update(final Long id, final Widget widget) throws WidgetNotFoundException {
        final Widget widgetToUpdate = find(id);

        // Only update non-null values
        if (widget.getXCoordinate() != null) {
            widgetToUpdate.setXCoordinate(widget.getXCoordinate());
        }
        if (widget.getYCoordinate() != null) {
            widgetToUpdate.setYCoordinate(widget.getYCoordinate());
        }
        if (widget.getWidth() != null) {
            widgetToUpdate.setWidth(widget.getWidth());
        }
        if (widget.getHeight() != null) {
            widgetToUpdate.setHeight(widget.getHeight());
        }

        if (widget.getZIndex() != null) {
            // Free up z-index if necessary
            setZIndex(widget.getZIndex(), widgetToUpdate);
        }

        return repository.save(widgetToUpdate);
    }

    @Override
    @Transactional
    public void delete(final Long id) throws WidgetNotFoundException {
        final Widget widget = find(id);
        repository.delete(widget);
    }

    @Override
    @Transactional
    public List<Widget> getAll(final int pageNo, final int pageSize, final String sortBy) {
        final Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<Widget> pagedResult = repository.findAll(paging);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Widget>();
    }

    @Override
    @Transactional
    public List<Widget> getAll() {
        return repository.findAll().stream().sorted(Comparator.comparingInt(Widget::getZIndex))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Widget> getWidgetsFilteredByArea(final int xCoordinate, final int yCoordinate, final int width,
            final int height) {
        return repository.findAll().stream()
                .filter(x -> x.getXCoordinate() >= xCoordinate && x.getYCoordinate() >= yCoordinate
                        && x.getXCoordinate() + x.getWidth() <= xCoordinate + width
                        && x.getYCoordinate() + x.getHeight() <= yCoordinate + height)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the lowest available z-index
     * 
     * @return  The lowest available z-index
     */
    private int findNewZIndex() {
        final List<Widget> allWidgets = getAll();
        return allWidgets.isEmpty() ? 0 : allWidgets.get(0).getZIndex() - 1;
    }

    /**
     * Sets the z-index of an entity. If the index is taken by another entity it
     * moves that index by 1 (recursively, as long as necessary)
     * 
     * @param zIndex The z-index to use
     * @param widget The widget to set
     */
    private void setZIndex(final int zIndex, final Widget widget) {
        final List<Widget> allWidgets = getAll();
        final Map<Integer, Widget> widgets = allWidgets.stream().collect(Collectors.toMap(Widget::getZIndex, x -> x));
        // Is it safe to use this index? If not move necessary widgets 1 place in the
        // z-plane
        freeZIndex(zIndex, widget, widgets);
    }

    /**
     * Frees up a z-index if it was already taken
     * 
     * @param zIndex  The z-index to check
     * @param widget  The entity
     * @param widgets The map of widgets (z-index <-> widget)
     */
    private void freeZIndex(final int zIndex, final Widget widget, final Map<Integer, Widget> widgets) {
        if (widgets.containsKey(zIndex)) {
            freeZIndex(zIndex + 1, widgets.get(zIndex), widgets);
        }

        // Set new z-index
        widget.setZIndex(zIndex);
        repository.save(widget);
    }

    /**
     * Checks whether the field is not null
     * 
     * @param field     The field to check
     * @param fieldName The field's name
     * @throws MissingWidgetFieldException If the field is missing (null)s
     */
    private static void checkRequiredField(final Object field, final String fieldName)
            throws MissingWidgetFieldException {
        if (field == null) {
            throw new MissingWidgetFieldException(fieldName);
        }
    }
}
