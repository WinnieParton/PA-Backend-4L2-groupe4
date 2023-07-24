package com.esgi.pa.api.dtos.requests.move;

import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.enums.RollbackEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/***
 * @param move id numérique du lobby
 * @param status id numérique du status
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerMoveRequest(@NotNull(message = "Status Id is required") RollbackEnum status,
                                @NotNull(message = "Move Id is required") Long move) {
}