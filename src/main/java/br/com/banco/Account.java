package br.com.banco;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CONTA")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CONTA")
    private long id;

    @Column(name = "NOME_RESPONSAVEL")
    private String name;

    public Account(){};

    public Account(String name) {
        this.name = name;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return """
            Account {
                id = %d,
                name = '%s'
            }
            """.formatted(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
