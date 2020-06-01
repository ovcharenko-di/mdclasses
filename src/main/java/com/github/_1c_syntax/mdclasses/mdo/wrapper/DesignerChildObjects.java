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
package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Враппер для дочерних элементов объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerChildObjects {
  @NonNull
  @XStreamImplicit(itemFieldName = "Form")
  List<String> forms = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Template")
  List<String> templates = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Command")
  List<DesignerMDO> commands = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Dimension")
  List<DesignerMDO> dimensions = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Resource")
  List<DesignerMDO> resources = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Recalculation")
  List<DesignerMDO> recalculations = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Attribute")
  List<DesignerMDO> attributes = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "TabularSection")
  List<DesignerMDO> tabularSections = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "AccountingFlag")
  List<DesignerMDO> accountingFlags = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "ExtDimensionAccountingFlag")
  List<DesignerMDO> extDimensionAccountingFlags = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Column")
  List<DesignerMDO> columns = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "AddressingAttribute")
  List<DesignerMDO> addressingAttributes = Collections.emptyList();

  @NonNull
  @XStreamImplicit
  List<Either<String, MDObjectBase>> children = Collections.emptyList();

}
