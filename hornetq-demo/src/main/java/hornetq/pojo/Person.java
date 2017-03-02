package hornetq.pojo;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -3017450903305153831L;
    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "Person [id=" + id + ", name=" + name + "]";
    }
}