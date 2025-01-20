package in.xlendz.entity;

import in.xlendz.constants.KeyPersonRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "lender_key_persons")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LenderKeyPersons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyPersonId;

    private String keyPersonName;
    @Column(unique = true)
    private String keyPersonPhone;

    @Column(unique = true)
    private String keyPersonEmail;

    @Enumerated(EnumType.STRING)
    private KeyPersonRoles keyPersonRole;

    @ManyToOne
    @JoinColumn
    private XlendzLender xlendzLender;

    @Lob
    private byte[] fileData;
    private String fileContentTypes;
    private String fileName;
}
