package in.xlendz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "xlend_user_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlendzUserAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xlend_user_address_seq")
    @SequenceGenerator(name = "xlend_user_address_seq", sequenceName = "xlend_user_address_seq", allocationSize = 1)
    private Long addressId;
    @ManyToOne
    @JoinColumn(name = "basicDetailsId", nullable = false)
    @JsonBackReference
    private XlendzUserBasicDetails userBasicDetailsId;
    private String houseNumber;
    private String streetName;
    private String city;
    private String state;
    private int pinCode;
    private String addressType;
    private char latestAddress;


}
