package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.AccumulationRegister;
import com.github._1c_syntax.mdclasses.mdo.BusinessProcess;
import com.github._1c_syntax.mdclasses.mdo.CalculationRegister;
import com.github._1c_syntax.mdclasses.mdo.Catalog;
import com.github._1c_syntax.mdclasses.mdo.ChartOfAccounts;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.mdclasses.mdo.CommandGroup;
import com.github._1c_syntax.mdclasses.mdo.CommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.CommonCommand;
import com.github._1c_syntax.mdclasses.mdo.CommonForm;
import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.mdo.CommonPicture;
import com.github._1c_syntax.mdclasses.mdo.CommonTemplate;
import com.github._1c_syntax.mdclasses.mdo.Constant;
import com.github._1c_syntax.mdclasses.mdo.DataProcessor;
import com.github._1c_syntax.mdclasses.mdo.DefinedType;
import com.github._1c_syntax.mdclasses.mdo.Document;
import com.github._1c_syntax.mdclasses.mdo.DocumentJournal;
import com.github._1c_syntax.mdclasses.mdo.DocumentNumerator;
import com.github._1c_syntax.mdclasses.mdo.EventSubscription;
import com.github._1c_syntax.mdclasses.mdo.ExchangePlan;
import com.github._1c_syntax.mdclasses.mdo.FilterCriterion;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOption;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.mdclasses.mdo.HTTPService;
import com.github._1c_syntax.mdclasses.mdo.InformationRegister;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDOEnum;
import com.github._1c_syntax.mdclasses.mdo.MDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.Report;
import com.github._1c_syntax.mdclasses.mdo.Role;
import com.github._1c_syntax.mdclasses.mdo.ScheduledJob;
import com.github._1c_syntax.mdclasses.mdo.Sequence;
import com.github._1c_syntax.mdclasses.mdo.SessionParameter;
import com.github._1c_syntax.mdclasses.mdo.SettingsStorage;
import com.github._1c_syntax.mdclasses.mdo.Style;
import com.github._1c_syntax.mdclasses.mdo.StyleItem;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.Task;
import com.github._1c_syntax.mdclasses.mdo.WSReference;
import com.github._1c_syntax.mdclasses.mdo.WebService;
import com.github._1c_syntax.mdclasses.mdo.XDTOPackage;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * Класс для чтения XML и MDO файлов конфигурации
 */
@UtilityClass
public class XStreamFactory {

  private final String ATTRIBUTE_FIELD_NAME = "attributes";
  private final String CHILDREN_FIELD_NAME = "children";

  @Getter(lazy = true)
  private final XStream xstream = createXMLMapper();

  /**
   * Выполняет чтение объекта из XML файла
   */
  public Object fromXML(File file) {
    return getXstream().fromXML(file);
  }

  private XStream createXMLMapper() {
    // данный провайдер неробходим для корректной обработки значений по умолчанию, чтобы не было null
    var xStream = new XStream(new PureJavaReflectionProvider());
    xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
    xStream.autodetectAnnotations(true);
    xStream.ignoreUnknownElements();

    // необходимо зарегистрировать все классы, имена которых в XML отличаются от имен самих классов
    addClassAliases(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    addConverters(xStream);
    // все типы реквизитов добавляются в одно поле
    addFieldAliases(xStream);

    return xStream;
  }

  private void addFieldAliases(XStream xStream) {

    // дочерние элементы
    MDOType.values(true).forEach(type -> {
      xStream.aliasField(type.getName(), DesignerChildObjects.class, CHILDREN_FIELD_NAME);

      if(type.getGroupName().isEmpty()) {
        return;
      }
      String fieldName;
      switch (type) {
        case WS_REFERENCE:
          fieldName = "wsReferences";
          break;
        case XDTO_PACKAGE:
          fieldName = "xDTOPackages";
          break;
        case HTTP_SERVICE:
          fieldName = "httpServices";
          break;
        default:
          var groupName = type.getGroupName();
          fieldName = groupName.substring(0, 1).toLowerCase() + groupName.substring(1);
      }
      xStream.aliasField(fieldName, MDOConfiguration.class, CHILDREN_FIELD_NAME);
    });

    // реквизиты объекта
    xStream.aliasField("dimensions", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("resources", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("recalculations", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("attributes", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("tabularSections", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("accountingFlags", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("extDimensionAccountingFlags", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("columns", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("addressingAttributes", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);

    // у табличной части тоже есть свои атрибуты
    xStream.aliasField("attributes", TabularSection.class, ATTRIBUTE_FIELD_NAME);

    // поля подсистем
    xStream.aliasField("subsystems", Subsystem.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("content", Subsystem.class, CHILDREN_FIELD_NAME);
  }

  private void addClassAliases(XStream xStream) {
    xStream.alias("mdclass:AccountingRegister", AccountingRegister.class);
    xStream.alias("mdclass:AccumulationRegister", AccumulationRegister.class);
    xStream.alias("mdclass:BusinessProcess", BusinessProcess.class);
    xStream.alias("mdclass:CalculationRegister", CalculationRegister.class);
    xStream.alias("mdclass:Catalog", Catalog.class);
    xStream.alias("mdclass:ChartOfAccounts", ChartOfAccounts.class);
    xStream.alias("mdclass:ChartOfCalculationTypes", ChartOfCalculationTypes.class);
    xStream.alias("mdclass:ChartOfCharacteristicTypes", ChartOfCharacteristicTypes.class);
    xStream.alias("mdclass:CommandGroup", CommandGroup.class);
    xStream.alias("mdclass:CommonAttribute", CommonAttribute.class);
    xStream.alias("mdclass:CommonCommand", CommonCommand.class);
    xStream.alias("mdclass:CommonForm", CommonForm.class);
    xStream.alias("mdclass:CommonModule", CommonModule.class);
    xStream.alias("mdclass:CommonPicture", CommonPicture.class);
    xStream.alias("mdclass:CommonTemplate", CommonTemplate.class);
    xStream.alias("mdclass:Constant", Constant.class);
    xStream.alias("mdclass:DataProcessor", DataProcessor.class);
    xStream.alias("mdclass:DefinedType", DefinedType.class);
    xStream.alias("mdclass:Document", Document.class);
    xStream.alias("mdclass:DocumentJournal", DocumentJournal.class);
    xStream.alias("mdclass:DocumentNumerator", DocumentNumerator.class);
    xStream.alias("mdclass:EventSubscription", EventSubscription.class);
    xStream.alias("mdclass:ExchangePlan", ExchangePlan.class);
    xStream.alias("mdclass:FilterCriterion", FilterCriterion.class);
    xStream.alias("mdclass:FunctionalOption", FunctionalOption.class);
    xStream.alias("mdclass:FunctionalOptionsParameter", FunctionalOptionsParameter.class);
    xStream.alias("mdclass:HTTPService", HTTPService.class);
    xStream.alias("mdclass:InformationRegister", InformationRegister.class);
    xStream.alias("mdclass:Enum", MDOEnum.class);
    xStream.alias("mdclass:Report", Report.class);
    xStream.alias("mdclass:Role", Role.class);
    xStream.alias("mdclass:ScheduledJob", ScheduledJob.class);
    xStream.alias("mdclass:Sequence", Sequence.class);
    xStream.alias("mdclass:SessionParameter", SessionParameter.class);
    xStream.alias("mdclass:SettingsStorage", SettingsStorage.class);
    xStream.alias("mdclass:Style", Style.class);
    xStream.alias("mdclass:StyleItem", StyleItem.class);
    xStream.alias("mdclass:Subsystem", Subsystem.class);
    xStream.alias("mdclass:Task", Task.class);
    xStream.alias("mdclass:WebService", WebService.class);
    xStream.alias("mdclass:WSReference", WSReference.class);
    xStream.alias("mdclass:XDTOPackage", XDTOPackage.class);

    xStream.alias("mdclass:Configuration", MDOConfiguration.class);

    xStream.alias("MetaDataObject", DesignerWrapper.class);
  }

  private void addConverters(XStream xStream) {
    xStream.registerConverter(new EnumConverter(ReturnValueReuse.class));
    xStream.registerConverter(new EnumConverter(UseMode.class));
    xStream.registerConverter(new AttributeConverter());
    xStream.registerConverter(new CompatibilityModeConverter());

    xStream.registerConverter(new PairConverter());
  }
}
