package com.miro.assignment.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a Widget entity
 */
@Entity
@Data
@EqualsAndHashCode
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer xCoordinate;

    @Column(nullable = false)
    private Integer yCoordinate;

    @Column(unique = true, nullable = false)
    private Integer zIndex;

    @Min(0)
    @Column(nullable = false)
    private Integer width;

    @Min(0)
    @Column(nullable = false)
    private Integer height;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastModified;

    /**
     * Overrides the toString representation
     */
    @Override
    public String toString() {
        return "Widget {" + "id=" + id + ", xCoordinate='" + xCoordinate + '\'' + ", yCoordinate='" + yCoordinate + '\''
                + ", zIndex='" + zIndex + '\'' + ", width='" + width + '\'' + ", height='" + height + '\''
                + ", lastModified=" + lastModified + '}';
    }

    /**
     * Builder for the Widget entity
     */
    public static class Builder {
        private Integer xCoordinate;
        private Integer yCoordinate;
        private Integer zIndex;
        private Integer width;
        private Integer height;

        /**
         * Constructor
         * 
         * @param xCoordinate The Widget's X coordinate
         * @param yCoordinate The Widget's Y coordinate
         * @param width       The Widget's width
         * @param height      The Widget's height
         */
        public Builder(final Integer xCoordinate, final Integer yCoordinate, final Integer width,
                final Integer height) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
            this.width = width;
            this.height = height;
        }

        /**
         * Optionally assing a z-index
         * 
         * @param zIndex The Widget's z-index
         * @return The builder object
         */
        public Builder withZIndex(final int zIndex) {
            this.zIndex = zIndex;
            return this;
        }

        /**
         * Builds an entity
         * 
         * @return Returns a Widget entity
         */
        public Widget build() {
            final Widget widget = new Widget();
            widget.xCoordinate = xCoordinate;
            widget.yCoordinate = yCoordinate;
            widget.zIndex = zIndex;
            widget.width = width;
            widget.height = height;
            widget.lastModified = LocalDateTime.now();
            return widget;
        }
    }

}
