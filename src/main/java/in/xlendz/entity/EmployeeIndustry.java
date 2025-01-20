package in.xlendz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_industry")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String industryName;
}
