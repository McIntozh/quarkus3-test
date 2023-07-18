package foo.bar.test;

import foo.bar.test.FooBar2Entity.FooBar2EntityPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "foo_bar2")
@IdClass(FooBar2EntityPK.class)
public class FooBar2Entity implements Serializable {

  @Id
  public String key1;
  @Id
  public String key2;

  public FooBar2Entity() {
  }

  public FooBar2Entity(String key1, String key2) {
    this.key1 = key1;
    this.key2 = key2;
  }

  public static class FooBar2EntityPK implements Serializable {

    public String key1;
    public String key2;

    public FooBar2EntityPK() {
    }

    public FooBar2EntityPK(String key1, String key2) {
      this.key1 = key1;
      this.key2 = key2;
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 61 * hash + Objects.hashCode(this.key1);
      hash = 61 * hash + Objects.hashCode(this.key2);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final FooBar2EntityPK other = (FooBar2EntityPK) obj;
      if (!Objects.equals(this.key1, other.key1)) {
        return false;
      }
      return Objects.equals(this.key2, other.key2);
    }

  }

}
