package com.maziarz.krystian.recruitment.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(
    packages = "com.maziarz.krystian.recruitment",
    importOptions = {
      ImportOption.DoNotIncludeTests.class,
      ImportOption.DoNotIncludeArchives.class,
      ImportOption.DoNotIncludeJars.class
    })
public class GeneralCodingRulesTest {
  @ArchTest
  void shouldNotThrowGenericExceptions(JavaClasses classes) {
    noClasses().should(THROW_GENERIC_EXCEPTIONS).check(classes);
  }

  @ArchTest
  void shouldNotUseJavaUtilLogging(JavaClasses classes) {
    noClasses().should(USE_JAVA_UTIL_LOGGING).check(classes);
  }

  @ArchTest
  void shouldNotUseJodaTime(JavaClasses classes) {
    noClasses().should(USE_JODATIME).check(classes);
  }
}
