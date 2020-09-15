package com.miro.assignment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class WidgetTest {

    /**
     * Test bean creation
     */
    @Test
    public void testBean() {
        final Widget widget = new Widget.Builder(1, 2, 3, 4).withZIndex(5).build();
        assertThat(widget.getId(), nullValue()); // ID not set manually
        assertThat(widget.getXCoordinate(), is(equalTo(1)));
        assertThat(widget.getYCoordinate(), is(equalTo(2)));
        assertThat(widget.getWidth(), is(equalTo(3)));
        assertThat(widget.getHeight(), is(equalTo(4)));
        assertThat(widget.getZIndex(), is(equalTo(5)));
        assertThat(widget.getLastModified(), notNullValue()); // Updated automatically
    }
}
