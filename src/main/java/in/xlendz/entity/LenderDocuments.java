package in.xlendz.entity;

import in.xlendz.constants.LenderDocumentTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "lender_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderDocuments {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

     @ManyToOne
     @JoinColumn
     private XlendzLender xlendzLender;

     private  String fileName;

     @Lob
     private byte[] fileData;

     private String fileContentTypes;

     @Enumerated(EnumType.STRING)
     private LenderDocumentTypes lenderDocumentType;

     @CreationTimestamp
     private Date upLoadedOn;


}
