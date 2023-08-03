package com.maziarz.krystian.recruitment.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.springframework.data.repository.Repository;

@AnalyzeClasses(
    packages = "com.maziarz.krystian.recruitment",
    importOptions = {
      ImportOption.DoNotIncludeTests.class,
      ImportOption.DoNotIncludeArchives.class,
      ImportOption.DoNotIncludeJars.class
    })
public class RepositoriesRulesTest {
  @ArchTest
  void shouldHaveRepositoryNameSuffix(JavaClasses classes) {
    classes()
        .that()
        .areAssignableTo(Repository.class)
        .should()
        .haveSimpleNameEndingWith("Repository")
        .because("We want consistent names.")
        .check(classes);
  }

  @ArchTest
  void shouldNotBePublic(JavaClasses classes) {
    classes()
        .that()
        .haveSimpleNameEndingWith("Repository")
        .should()
        .notBePublic()
        .because("We don't want repositories to be accessed from out of their domain package.")
        .check(classes);
  }

  @ArchTest
  void shouldNotBeAnnotatedWithRepository(JavaClasses classes) {
    classes()
        .that()
        .haveSimpleNameEndingWith("Repository")
        .and()
        .areInterfaces()
        .should()
        .notBeAnnotatedWith(org.springframework.stereotype.Repository.class)
        .because("We extend spring Jpa Repositories which are anyway auto detected.")
        .check(classes);
  }
}
