package com.glam.bff.dto.garment;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.glam.bff.dto.authentication.enums.StyleEnum;
import com.glam.bff.dto.garment.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicGarmentDTO {

    private String title;

    private String description;

    private CategoryEnum category;

    private SubCategoryEnum subCategory;

    private ColorEnum mainColor;

    private ColorEnum secondColor;

    private PatternEnum pattern;

    private String brand;

    private Boolean isFavorite;

    private FabricEnum fabric;

    private ConditionEnum condition;

    private SleeveLengthEnum sleeveLength;

    private LengthEnum length;

    private SizeEnum sizeEnum;

    private PatternDetailEnum patternDetail;

    private String photoUri;

    private String textureUri;

    // To be updated after asking to OpenAI
    private List<SeasonEnum> season;

    private List<StyleEnum> styles;

}

class ColorSerializer extends StdSerializer<ColorEnum> {

    protected ColorSerializer(Class<ColorEnum> t) {
        super(t);
    }

    public ColorSerializer() {
        this(null);
    }

    @Override
    public void serialize(ColorEnum color, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(color.toString());
    }
}

class ColorDeserializer extends StdDeserializer<ColorEnum> {

    protected ColorDeserializer(Class<ColorEnum> t) {
        super(t);
    }

    public ColorDeserializer() {
        this(null);
    }

    @Override
    public ColorEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String hexCode = p.getValueAsString();
        int r = Integer.valueOf(hexCode.substring(1, 3), 16);
        int g = Integer.valueOf(hexCode.substring(3, 4), 16);
        int b = Integer.valueOf(hexCode.substring(5, 7), 16);
        return ColorEnum.searchMatch(r, g, b);
    }
}