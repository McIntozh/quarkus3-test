package foo.bar.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "foo_bar")
public class FooBarEntity implements Serializable {

  @Id
  public Long id;

}
