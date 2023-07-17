package br.com.banco;

import javax.persistence.*;

@Entity
@Table(name = "CONTA")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "NOME_RESPONSAVEL")
    private String name;

    public Account(){};

    public Account(long id, String name) {
        this.id = id;
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
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
