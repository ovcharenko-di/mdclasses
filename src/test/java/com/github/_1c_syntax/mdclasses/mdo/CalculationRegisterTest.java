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

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationRegisterTest extends AbstractMDOTest {
  CalculationRegisterTest() {
    super(MDOType.CALCULATION_REGISTER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CalculationRegisters/РегистрРасчета1/РегистрРасчета1.mdo");
    checkBaseField(mdo, CalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 3, "CalculationRegister.РегистрРасчета1",
      AttributeType.DIMENSION, AttributeType.RESOURCE, AttributeType.RECALCULATION);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CalculationRegisters/РегистрРасчета1.xml");
    checkBaseField(mdo, CalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2, "CalculationRegister.РегистрРасчета1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
