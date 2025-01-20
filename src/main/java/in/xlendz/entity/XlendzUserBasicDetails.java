package in.xlendz.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="xlendz_user_basic_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserBasicDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDetailsId;
    @OneToOne
    @JoinColumn(name="xlendzUserId")
    private XlendzUser xlendzUserId;
    private String firstName;
    private String lastName;
    private char maritalStatus;
    private char sex;
    private String fatherName;
    @OneToMany(mappedBy ="userBasicDetailsId" ,orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<XlendzUserAddress> userAddresses;
}
