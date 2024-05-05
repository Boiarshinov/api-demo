package dev.boiarshinov.standardmethods.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequestMapping("/partial")
@RestController
public record PartialEndpoint(
        ObjectMapper jsonMapper
) {

    @GetMapping("/{id}")
    public Object findPartialById(
            @PathVariable String id,
            @RequestParam(required = false) List<String> fieldMask
    ) {
        Entity entity = findById(id);

        if (fieldMask == null || fieldMask.isEmpty()) {
            return entity;
        }

        var responseMap = new HashMap<String, Object>();
        for (String fieldName : fieldMask) {
            Field field = ReflectionUtils.findField(Entity.class, fieldName);
            if (field == null) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = ReflectionUtils.getField(field, entity);
            responseMap.put(fieldName, fieldValue);
        }

        return responseMap;
    }

    @PatchMapping("/{id}")
    public Entity partialUpdate(
            @PathVariable String id,
            @RequestBody PartialUpdateRequest updateRequest
    ) throws JsonMappingException {
        Entity entity = findById(id);

        jsonMapper.updateValue(entity, updateRequest);

        return entity;
    }

    private Entity findById(String id) {
        return new Entity(
                id,
                "name",
                "inn",
                "address",
                "title",
                "status"
        );
    }

    public static class Entity {
        String id;
        String name;
        String inn;
        String address;
        String title;
        String status;

        public Entity(String id, String name, String inn, String address, String title, String status) {
            this.id = id;
            this.name = name;
            this.inn = inn;
            this.address = address;
            this.title = title;
            this.status = status;
        }


        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setInn(String inn) {
            this.inn = inn;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PartialUpdateRequest(
            Optional<String> name,
            Optional<String> inn,
            Optional<String> address,
            Optional<String> title,
            Optional<String> status
    ) {}
}
