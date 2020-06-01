/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class DefinedTypeTest extends AbstractMDOTest {
  DefinedTypeTest() {
    super(MDOType.DEFINED_TYPE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("DefinedTypes/ОпределяемыйТип1/ОпределяемыйТип1.mdo");
    checkBaseField(mdo, DefinedType.class, "ОпределяемыйТип1",
      "e8c616d9-4957-48ab-a917-afb6847f6840");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("DefinedTypes/ОпределяемыйТип1.xml");
    checkBaseField(mdo, DefinedType.class, "ОпределяемыйТип1",
      "e8c616d9-4957-48ab-a917-afb6847f6840");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
