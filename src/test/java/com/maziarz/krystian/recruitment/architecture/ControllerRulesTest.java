package com.maziarz.krystian.recruitment.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(
    packages = "com.maziarz.krystian.recruitment",
    importOptions = {
      ImportOption.DoNotIncludeTests.class,
      ImportOption.DoNotIncludeArchives.class,
      ImportOption.DoNotIncludeJars.class
    })
public class ControllerRulesTest {

  @ArchTest
  public void shouldHaveControllerNameSuffix(JavaClasses classes) {
    classes()
        .that()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveSimpleNameEndingWith("Controller")
        .because("We want consistent names.")
        .check(classes);
  }

  @ArchTest
  void shouldBeAnnotatedWithRequestMapping(JavaClasses classes) {
    classes()
        .that()
        .haveSimpleNameEndingWith("Controller")
        .should()
        .beAnnotatedWith(RestController.class)
        .because("We want all controllers to use same annotations.")
        .check(classes);
  }
}
