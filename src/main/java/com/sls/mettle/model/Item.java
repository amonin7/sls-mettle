package com.sls.mettle.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @JsonView(Views.Public.class)
    private UUID id;
    @JsonRawValue
    @JsonView(Views.Public.class)
    private String name;
    @JsonRawValue
    @JsonView(Views.Public.class)
    private String description;
    @JsonRawValue
    @JsonView(Views.Public.class)
    private ItemType type;
    @JsonRawValue
    @JsonView(Views.Public.class)
    private Double cost;
    @JsonAlias("created_at")
    @JsonView(Views.Internal.class)
    private Timestamp createdAt;
    @JsonAlias("updated_at")
    @JsonView(Views.Internal.class)
    private Timestamp updatedAt;
    @JsonAlias("deleted_at")
    @JsonView(Views.Internal.class)
    private Timestamp deletedAt;

    public void setType(String type) {
        this.type = ItemType.valueOfIgnoreCase(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id.equals(item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description) && type == item.type && Objects.equals(cost, item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, cost);
    }
}
