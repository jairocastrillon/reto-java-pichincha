package co.com.bancopichincha.retojava.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Client extends Person {

    private String clientId;
    private String password;
    private boolean status;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Client() {
        super();
    }

    public Client(long id, String name, String genre, int age, String address, String phone,
                  String clientId, String password, boolean status) {
        super(id, name, genre, age, address, phone);
        this.clientId = clientId;
        this.password = password;
        this.status = status;
    }

}
