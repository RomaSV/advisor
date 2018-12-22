package com.springingdream.adviser.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Data
@NoArgsConstructor
public class UserProduct {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private Map<String, String> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserProduct))
            return false;
        return id.equals(((UserProduct) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}