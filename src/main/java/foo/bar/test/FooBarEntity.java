package foo.bar.test;

import foo.bar.test.FooBarEntity.FooBar2EntityPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "foo_bar")
@IdClass(FooBar2EntityPK.class)
//@BatchSize(size = 1)
public class FooBarEntity implements Serializable {

  @Id
  @Column(columnDefinition = "VARCHAR(50) NOT NULL")
  public String key1;
  @Id
  @Column(columnDefinition = "INT(10) UNSIGNED NOT NULL")
  public long key2;

  public FooBarEntity() {
  }

  public FooBarEntity(String key1, Long key2) {
    this.key1 = key1;
    this.key2 = key2;
  }

  public static class FooBar2EntityPK implements Serializable {

    public String key1;
    public Long key2;

    public FooBar2EntityPK() {
    }

    public FooBar2EntityPK(String key1, Long key2) {
      this.key1 = key1;
      this.key2 = key2;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 41 * hash + Objects.hashCode(this.key1);
      hash = 41 * hash + Objects.hashCode(this.key2);
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
