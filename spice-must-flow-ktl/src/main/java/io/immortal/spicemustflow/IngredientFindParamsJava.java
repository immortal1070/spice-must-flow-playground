package io.immortal.spicemustflow;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public class IngredientFindParamsJava {
   private List<UUID> ids;
   private List<String> names;

    public IngredientFindParamsJava(final List<UUID> ids, final List<String> names) {
        this.ids = ids;
        this.names = names;
    }

    public List<UUID> ids() {
        return ids;
    }

    public void setIds(final List<UUID> ids) {
        this.ids = ids;
    }

    public List<String> names() {
        return names;
    }

    public void setNames(final List<String> names) {
        this.names = names;
    }
}