package foo.bar.test;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Startup
public class Test {

  @Inject
  EntityManager em;

  @Inject
  AgroalDataSource ds;

  @PostConstruct
  public void test() {

    Logger.getLogger(Test.class.getName()).log(Level.INFO, "Start");
    Logger.getLogger(Test.class.getName()).log(Level.INFO, "Preparing Database");

    try (Statement stm = ds.getConnection().createStatement()) {
      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Filling tables");
      stm.executeUpdate("drop table if exists foo_bar");
      stm.executeUpdate("create table foo_bar (key1 VARCHAR(50) NOT NULL not null,key2 INT(10) UNSIGNED NOT NULL not null,primary key (key1, key2)) engine=InnoDB;");

      int ENTRIES = 10000000;
      int STEP = 10000;
      for (int i = 0; i < ENTRIES / STEP; i++) {
        if (i % (ENTRIES / STEP / 10) == 0) {
          Logger.getLogger(Test.class.getName()).log(Level.INFO, i * STEP + " / " + ENTRIES);
        }
        String values = "('key" + (i * STEP) + "'," + (i * STEP) + ")";
        for (int j = 1; j < STEP; j++) {
          values += ",('key" + (i * STEP + j) + "'," + (i * STEP + j) + ")";
        }
        stm.executeUpdate("INSERT INTO foo_bar (key1,key2) values " + values);
      }
      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Done filling tables");
      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Database prepared");

      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Executing an EXPLAIN for the expected statement");
      try (ResultSet rs = stm.executeQuery("EXPLAIN SELECT * FROM foo_bar WHERE (key1,key2) in (('key123',123),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null),(null,null))")) {
        while (rs.next()) {
          for (int c = 1; c <= rs.getMetaData().getColumnCount(); c++) {
            System.out.println(rs.getMetaData().getColumnName(c) + ": " + rs.getObject(c));
          }
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
    }

    long start = System.currentTimeMillis();
    var entity = em.find(FooBarEntity.class, new FooBarEntity.FooBar2EntityPK("key123", 123l));
    if (entity != null) {
      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Found " + entity.key1);
    } else {
      Logger.getLogger(Test.class.getName()).log(Level.INFO, "Nothing found");
    }
    Logger.getLogger(Test.class.getName()).log(Level.INFO, "Query for PK took: " + (System.currentTimeMillis() - start) + "ms");

    Logger.getLogger(Test.class.getName()).log(Level.INFO, "End");
  }
}
