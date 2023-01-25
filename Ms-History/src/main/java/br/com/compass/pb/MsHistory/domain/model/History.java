package br.com.compass.pb.MsHistory.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "history")
@Builder
public class History {

    @Field(name = "id")
    @Indexed(unique = true)
    @Id
    private String id;
    @NonNull
    @Field(name="id_order")
    @Indexed(unique = true)
    private Long idOrder;
    @NonNull
    @Field(name="total")
    private BigDecimal total;
    @NonNull
    @Field(name="event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate eventDate;
/*
    @NonNull
    @Field
    private String code;
*/
}
