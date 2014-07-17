/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.displayer;

/**
 * A DataSetDisplayerBuilder allows for the assembly of a DisplayerSettings instance in a friendly manner.
 *
 * <pre>
 *   DisplayerFactory.newBarChart()
 *   .title("By Product")
 *   .titleVisible(false)
 *   .margins(10, 50, 100, 100)
 *   .column("Product")
 *   .column("Total amount")
 *   .horizontal()
 *   .buildDisplayer();
 * </pre>
 *
 * @see DisplayerSettings
 */
public interface DataDisplayerBuilder<T> {

    /**
     * Sets the caption that will be shown for this particular visualization of the data.
     * @param title The caption to be shown
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T title(String title);

    /**
     * Set whether the caption should be visible or not.
     * @param visible True if the caption is to be visible, false if not.
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T titleVisible(boolean visible);

    /**
     * Set the renderer that will be used for visualizing this DisplayerSettings.
     * @param renderer The identifier of the renderer.
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T renderer(String renderer);

    /**
     * Set a data set column header caption. If no column identifier is specified, each successive call to this
     * method will set the specified caption to the next occurring column of the data set.
     * @param displayName The header caption to be set to a column.
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T column(String displayName);

    /**
     * Set a data set column header caption. If no column identifier is specified, each successive call to this
     * method will set the specified caption to the next occurring column of the data set.
     * @param columnId The identifier within the data set of the column this caption should be set to.
     * @param displayName The header caption to be set to the column specified by the identifier.
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T column(String columnId, String displayName);

    /**
     * Enable the ability to select/filter values (or range of values) within the data displayer.
     *
     * <p> Usually, in a dashboard there exists a unique coordinator which takes cares of propagate all the data
     * selection events among the other displayers. If enabled then there exists also the ability to configure how to
     * interact with other displayers in the same dashboard.</p>

     * @param applySelf If true then any filter request issued within the data displayer will be applied to the own displayer.
     * @param notifyOthers If true then any filter request issued within the data displayer will be propagated to other interested displayers.
     * @param receiveFromOthers If true then the data displayer will listen for filter requests coming from other displayers.
     *
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T filterOn(boolean applySelf, boolean notifyOthers, boolean receiveFromOthers);

    /**
     * Disable the ability to select/filter values (or range of values) within the data displayer.
     *
     * @see DataDisplayerBuilder#filterOn DataDisplayerBuilder's filterOn method.
     * @return The DataDisplayerBuilder instance that is being used to configure a DisplayerSettings.
     */
    T filterOff();

    /**
     * @return The DisplayerSettings instance that has been configured.
     * @see DisplayerSettings
     */
    DisplayerSettings buildDisplayer();
}
