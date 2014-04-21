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
package org.sonar.plugins.isomapping;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public final class ISOMappingMetrics implements Metrics {

	public static final String MODULARITY_DOMAIN = "Modularity";
	public static final String REUSABILITY_DOMAIN = "Modularity";
	public static final String ANALYZABILITY_DOMAIN = "Modularity";
	public static final String CHANGEABILITY_DOMAIN = "Modularity";
	public static final String MODIFICATION_DOMAIN = "Modularity";
	public static final String TESTABILITY_DOMAIN = "Modularity";
	public static final String COMPLIANCE_DOMAIN = "Modularity";

	// This metric is used by Decorator
	public static final Metric MODULARITY = new Metric.Builder(
			"ISOMappingPlugin.Modularity", "Modularity", Metric.ValueType.FLOAT)
			.setDescription("Modularity value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric REUSABILITY = new Metric.Builder(
			"ISOMappingPlugin.Reusability", "Reusability",
			Metric.ValueType.FLOAT).setDescription("Reusability value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric ANALYZABILITY = new Metric.Builder(
			"ISOMappingPlugin.Analizability", "Analizability",
			Metric.ValueType.FLOAT).setDescription("Analyzability value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric CHANGEABILITY = new Metric.Builder(
			"ISOMappingPlugin.Changeability", "Changeability",
			Metric.ValueType.FLOAT).setDescription("Changeability value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric MODIFICATION = new Metric.Builder(
			"ISOMappingPlugin.Modification", "Modification stability",
			Metric.ValueType.FLOAT).setDescription("Modification value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric TESTABILITY = new Metric.Builder(
			"ISOMappingPlugin.Testability", "Testability",
			Metric.ValueType.FLOAT).setDescription("Testability value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// This metric is used by Decorator
	public static final Metric COMPLIANCE = new Metric.Builder(
			"ISOMappingPlugin.Compliance", "Compliance", Metric.ValueType.FLOAT)
			.setDescription("Compliance value")
			.setDirection(Metric.DIRECTION_BETTER).setQualitative(true)
			.setDomain(MODULARITY_DOMAIN).create();

	// getMetrics() method is defined in the Metrics interface and is used by
	// Sonar to retrieve the list of new metrics
	public List<Metric> getMetrics() {
		return Arrays.asList(MODULARITY, REUSABILITY, ANALYZABILITY,
				CHANGEABILITY, MODIFICATION, TESTABILITY, COMPLIANCE);
	}
}
