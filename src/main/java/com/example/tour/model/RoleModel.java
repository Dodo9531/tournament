package com.example.tour.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Table(name="roles")
@Schema(description = "Role details")
@Entity
public class RoleModel {
    @Schema(description = "Role Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "Role name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
