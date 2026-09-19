package clinica.api.model.converter;

import clinica.api.model.enums.MotivoCancelamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = false)
public class MotivoCancelamentoConverter implements AttributeConverter<MotivoCancelamento, String> {

    private static final Map<String, MotivoCancelamento> LEGACY_MAP = new HashMap<>();

    static {
        LEGACY_MAP.put("Paciente não compareceu", MotivoCancelamento.PACIENTE_DESISTIU);
        LEGACY_MAP.put("Paciente nao compareceu", MotivoCancelamento.PACIENTE_DESISTIU);
        LEGACY_MAP.put("Paciente desistiu", MotivoCancelamento.PACIENTE_DESISTIU);
        LEGACY_MAP.put("Médico cancelou", MotivoCancelamento.MEDICO_CANCELOU);
        LEGACY_MAP.put("Medico cancelou", MotivoCancelamento.MEDICO_CANCELOU);
        // add other legacy labels here if needed
    }

    @Override
    public String convertToDatabaseColumn(MotivoCancelamento attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public MotivoCancelamento convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        // try direct enum name
        try {
            return MotivoCancelamento.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            // try exact legacy match
            MotivoCancelamento mapped = LEGACY_MAP.get(dbData);
            if (mapped != null) return mapped;

            // try case-insensitive match of legacy keys
            String trimmed = dbData.trim();
            for (Map.Entry<String, MotivoCancelamento> e : LEGACY_MAP.entrySet()) {
                if (e.getKey().equalsIgnoreCase(trimmed)) return e.getValue();
            }

            // try case-insensitive match against enum names
            for (MotivoCancelamento m : MotivoCancelamento.values()) {
                if (m.name().equalsIgnoreCase(trimmed)) return m;
            }

            // fallback to OUTROS when unknown
            return MotivoCancelamento.OUTROS;
        }
    }
}
