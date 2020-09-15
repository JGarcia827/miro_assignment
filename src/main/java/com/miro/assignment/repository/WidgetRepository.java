package com.miro.assignment.repository;

import java.util.List;

import com.miro.assignment.domain.Widget;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * This class is responsible for the data retrieval
 */
@Repository
public interface WidgetRepository extends PagingAndSortingRepository<Widget, Long> {
    /**
     * Retuns a list with all the widgets in the repository
     */
    List<Widget> findAll();
}
