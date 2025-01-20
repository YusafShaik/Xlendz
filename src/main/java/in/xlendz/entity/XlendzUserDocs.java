package in.xlendz.entity;

import in.xlendz.constants.FileUploadTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name="xlend_user_docs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XlendzUserDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xlend_user_docs_seq")
    @SequenceGenerator(name = "xlend_user_docs_seq", sequenceName = "xlend_user_docs_seq", allocationSize = 1)
    private Long docId;

    @Enumerated(EnumType.STRING)
    private FileUploadTypes  docType;

    private String fileName;

    @CreationTimestamp
    private Date uploadedOn;

    @ManyToOne
    @JoinColumn(name="user_id")
    private XlendzUser xlendzUser;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileData;

    private String fileContentType;


}
