/*
 * SonarQube ISO/IEC 25000 Mapping
 * Copyright (C) 2014 Kamilla H. Crozara
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.isomapping;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.PropertyDefinitions;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Directory;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.test.IsMeasure;
import org.sonar.plugins.isomapping.batch.ISOMappingDecorator;
import org.sonar.plugins.isomapping.ISOMappingMetrics;
import org.sonar.plugins.isomapping.ISOMappingPlugin;

public class ISOMappingDecoratorTest {

	private final Project myProject = new Project("myProject");
	  private final DecoratorContext dc = mock(DecoratorContext.class);
	  private ISOMappingDecorator isomappingDecorator;

	  @Before
	  public void initISOMappingThresholds() {
	    Settings settings = new Settings(new PropertyDefinitions(ISOMappingDecorator.class));
	    isomappingDecorator = new ISOMappingDecorator(settings);
	    isomappingDecorator.shouldExecuteOnProject(myProject);
	  }

	  @Test
	  public void testISOMappingOnDecorateFile() {

	    when(dc.getMeasure(CoreMetrics.ABSTRACTNESS)).thenReturn(new Measure(CoreMetrics.COMPLEXITY, 5.0));
	    isomappingDecorator.decorate(new File("simpleFile"), dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, 1.0)));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 50.0));
	    isomappingDecorator.decorate(new File("simpleFile"), dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.MODIFICATION, "Medium")));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 50.5));
	    isomappingDecorator.decorate(new File("simpleFile"), dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.CHANGEABILITY, "Complex")));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 1000.0));
	    isomappingDecorator.decorate(new File("simpleFile"), dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.ANALYZABILITY, "Very complex")));

	  }

	  @Test
	  public void testAbacusComplexityOnDecorateProject() {

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 5.0));
	    isomappingDecorator.decorate(myProject, dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Simple")));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 50.0));
	    isomappingDecorator.decorate(myProject, dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Medium")));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 50.5));
	    isomappingDecorator.decorate(myProject, dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Complex")));

	    when(dc.getMeasure(CoreMetrics.FILE_COMPLEXITY)).thenReturn(new Measure(CoreMetrics.FILE_COMPLEXITY, 1000.0));
	    isomappingDecorator.decorate(myProject, dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Very complex")));

	  }

	  @Test
	  public void testAbacusComplexityDistributionOnDecorateDirectory() {

	    List<Measure> childrenMeasures = Arrays.asList(
	        new Measure(ISOMappingMetrics.TESTABILITY, "Simple"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Simple"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Complex"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Complex"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Complex"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Complex"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Very complex"));

	    when(dc.getChildrenMeasures(ISOMappingMetrics.TESTABILITY)).thenReturn(childrenMeasures);
	    isomappingDecorator.decorate(new Directory("myDirectory"), dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Simple=2;Medium=0;Complex=4;Very complex=1")));

	  }

	  @Test
	  public void testAbacusComplexityDistributionOnDecorateProject() {

	    List<Measure> childrenMeasures = Arrays.asList(
	        new Measure(ISOMappingMetrics.TESTABILITY, "Simple=2;Medium=0;Complex=4;Very complex=1"),
	        new Measure(ISOMappingMetrics.TESTABILITY, "Simple=1;Medium=1;Complex=2;Very complex=6"));

	    when(dc.getChildrenMeasures(ISOMappingMetrics.TESTABILITY)).thenReturn(childrenMeasures);
	    isomappingDecorator.decorate(myProject, dc);
	    verify(dc).saveMeasure(argThat(new IsMeasure(ISOMappingMetrics.TESTABILITY, "Simple=3;Medium=1;Complex=6;Very complex=7")));

	  }

}
