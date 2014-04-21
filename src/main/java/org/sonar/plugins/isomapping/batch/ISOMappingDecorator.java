/*
 * SonarQube ISO/IEC 25000 Mapping
 * Copyright (C) 2014 Kamilla H. Crozara
 * holanda.kamilla@gmail.com
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
package org.sonar.plugins.isomapping.batch;

import org.sonar.plugins.isomapping.ISOMappingMetrics;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.apache.commons.lang.math.RandomUtils;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;
import org.sonar.plugins.isomapping.config.ISOMappingSettings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import java.util.Arrays;
import java.util.List;

public class ISOMappingDecorator implements Decorator {

	private ISOMappingSettings settings;

	public ISOMappingDecorator(ISOMappingSettings _settings) {
		this.settings = _settings;
	}

	@DependsUpon
	public List<Metric> dependsOn() {
		return Arrays.asList(CoreMetrics.FILE_COMPLEXITY);
	}

	public boolean shouldExecuteOnProject(Project project) {
		// Execute only on Java projects
		return StringUtils.equals(project.getLanguageKey(), Java.KEY);
	}

	public void decorate(Resource resource, DecoratorContext context) {
		// This method is executed on the whole tree of resources.
		// Bottom-up navigation : Java methods -> Java classes -> files ->
		// packages -> modules -> project
		if (Scopes.isBlockUnit(resource)) {
			// Sonar API includes many libraries like commons-lang and
			// google-collections
			double value = RandomUtils.nextDouble();
			
			System.out.println(value);
			System.out.println(resource);

			// Add a measure to the current Java method
			//context.saveMeasure(ISOMappingMetrics.ANALYZABILITY, value);

		} else {
			System.out.println("Else");
			// we sum random values on resources different than method
			/*context.saveMeasure(
					ISOMappingMetrics.ANALYZABILITY,
					MeasureUtils.sum(
							true,
							context.getChildrenMeasures(ISOMappingMetrics.ANALYZABILITY)));*/
		}
	}
}
