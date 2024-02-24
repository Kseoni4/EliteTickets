package ru.mirea.docker.elitetickets.dto.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentModel {

    @JsonProperty("id")
    private String paymentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("paid")
    private boolean isPaid;

    @JsonProperty("confirmation")
    private ConfirmationProperties confirmationProperties;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConfirmationProperties{

        public String type;

        @JsonProperty("confirmation_url")
        public String confirmationUrl;

    }
}
