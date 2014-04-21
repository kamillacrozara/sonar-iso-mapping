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

import org.sonar.plugins.isomapping.batch.ISOMappingDecorator;
import org.sonar.plugins.isomapping.ui.ISOMappingWidget;
import org.sonar.api.SonarPlugin;
import java.util.Arrays;
import java.util.List;

public final class ISOMappingPlugin extends SonarPlugin {

	// This is where you're going to declare all your Sonar extensions
	public List getExtensions() {
		return Arrays.asList(
		// Definitions
				ISOMappingMetrics.class,

				// Batch
				ISOMappingDecorator.class,

				// UI
				ISOMappingWidget.class);
	}
}