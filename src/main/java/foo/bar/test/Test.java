package foo.bar.test;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Startup
public class Test {

  @Inject
  private EntityManager em;

  @PostConstruct
  public void test() {
    Logger.getLogger(Test.class.getName()).log(Level.INFO, "Start");

    var entity1 = em.find(FooBarEntity.class, 1l);
    var entity2 = em.find(FooBar2Entity.class, new FooBar2Entity.FooBar2EntityPK("Value1", "Value2"));

    Logger.getLogger(Test.class.getName()).log(Level.INFO, "End");
  }
}
