package com.esgi.pa.api.dtos.responses.move;

import com.esgi.pa.domain.enums.RollbackEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AnswerRollback(Long id, String gameState, RollbackEnum status){}