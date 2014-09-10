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
package org.dashbuilder.renderer.google.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.corechart.BarChart;
import com.googlecode.gwt.charts.client.corechart.BarChartOptions;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.corechart.CoreChartWidget;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.CoreOptions;
import org.dashbuilder.displayer.DisplayerAttributeDef;
import org.dashbuilder.displayer.DisplayerAttributeGroupDef;
import org.dashbuilder.displayer.DisplayerEditorConfig;
import org.dashbuilder.displayer.impl.DisplayerEditorConfigImpl;

public class GoogleBarChartDisplayer extends GoogleXAxisChartDisplayer {

    protected CoreChartWidget chart;
    protected Panel filterPanel;

    @Override
    public ChartPackage getPackage() {
        return ChartPackage.CORECHART;
    }

    @Override
    public Widget createVisualization() {

        if (displayerSettings.isBarchartHorizontal()) chart = new BarChart();
        else chart = new ColumnChart();

        chart.addSelectHandler(createSelectHandler(chart));
        chart.draw(createTable(), createOptions());


        HTML titleHtml = new HTML();
        if (displayerSettings.isTitleVisible()) {
            titleHtml.setText(displayerSettings.getTitle());
        }

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(titleHtml);
        verticalPanel.add(filterPanel = new SimplePanel());
        verticalPanel.add(chart);
        return verticalPanel;
    }

    @Override
    public DisplayerEditorConfig getDisplayerEditorConfig() {
        return new DisplayerEditorConfigImpl()
                   .supportsAttribute( DisplayerAttributeGroupDef.COMMON_GROUP )
                   .supportsAttribute( DisplayerAttributeDef.CHART_WIDTH )
                   .supportsAttribute( DisplayerAttributeDef.CHART_HEIGHT )
                   .supportsAttribute( DisplayerAttributeGroupDef.CHART_MARGIN_GROUP )
                   .supportsAttribute( DisplayerAttributeGroupDef.CHART_LEGEND_GROUP )
                   .supportsAttribute( DisplayerAttributeGroupDef.AXIS_GROUP )
                   .supportsAttribute( DisplayerAttributeDef.BARCHART_HORIZONTAL );
    }

    protected void updateVisualization() {
        filterPanel.clear();
        Widget filterReset = createCurrentSelectionWidget();
        if (filterReset != null) filterPanel.add(filterReset);

        chart.draw(createTable(), createOptions());
    }

    private CoreOptions createOptions() {
        Animation anim = Animation.create();
        anim.setDuration(700);
        anim.setEasing(AnimationEasing.IN_AND_OUT);

        if (displayerSettings.isBarchartHorizontal()) {
            BarChartOptions options = BarChartOptions.create();
            options.setWidth(displayerSettings.getChartWidth());
            options.setHeight( displayerSettings.getChartHeight() );
            options.setLegend( createChartLegend( displayerSettings ) );
            if ( displayerSettings.isXAxisShowLabels() ) options.setHAxis( createHAxis() );
            if ( displayerSettings.isYAxisShowLabels() ) options.setVAxis( createVAxis() );
            options.setAnimation( anim );
            options.setChartArea(createChartArea());
            return options;
        }
        else {
            ColumnChartOptions options = ColumnChartOptions.create();
            options.setWidth(displayerSettings.getChartWidth());
            options.setHeight(displayerSettings.getChartHeight());
            options.setLegend( createChartLegend( displayerSettings ) );
            if ( displayerSettings.isXAxisShowLabels() ) options.setHAxis( createHAxis() );
            if ( displayerSettings.isYAxisShowLabels() ) options.setVAxis( createVAxis() );
            options.setAnimation(anim);
            // TODO: options.set3D(displayerSettings.is3d());
            options.setChartArea(createChartArea());
            return options;
        }
    }
}
